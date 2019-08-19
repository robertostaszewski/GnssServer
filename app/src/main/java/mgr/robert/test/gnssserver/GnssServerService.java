package mgr.robert.test.gnssserver;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GnssServerService extends IntentService {
    private final ExecutorService pool = Executors.newFixedThreadPool(2);
    private final StopReceiver receiver = new StopReceiver();

    private NetworkManager networkManager;
    private int sourcePortNumber;
    private int dataPortNumber;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public GnssServerService() {
        super("GnssServer");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        sourcePortNumber = intent.getIntExtra("sourcePort", 8083);
        dataPortNumber = intent.getIntExtra("serverPort", 8084);
        int bufferSize = intent.getIntExtra("bufferSize", 1024);
        networkManager = new NetworkManager(new ConcurrentSubscriberService(), bufferSize);

        startNotification();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            NetworkService producerNetwork = networkManager.getProducerNetwork(sourcePortNumber);
            NetworkService consumerNetwork = networkManager.getConsumerNetwork(dataPortNumber);

            pool.execute(consumerNetwork);
            pool.submit(producerNetwork).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        pool.shutdown();
        networkManager.close();
        unregisterReceiver(receiver);
        pool.shutdownNow();
        super.onDestroy();
    }

    private void startNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String channelId = "channelId";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "channelName";
            NotificationChannel chan = new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_DEFAULT);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(chan);
        }

        Intent stopIntent = new Intent(StopReceiver.ACTION_STOP);
        PendingIntent stop = PendingIntent.getBroadcast(this, 0, stopIntent, 0);

        Notification notification =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.icon_file_pdf)
                        .setContentTitle("title")
                        .setContentText("text")
                        .setContentIntent(pendingIntent)
                        .setTicker("ticker")
                        .addAction(R.drawable.gallery_album_overlay, "Stop", stop)
                        .build();

        startForeground(1, notification);
    }

    private static class StopReceiver extends BroadcastReceiver {

        public static final String ACTION_STOP = "stop";

        @Override
        public void onReceive(Context context, Intent intent) {
            context.stopService(new Intent(context, GnssServerService.class));
        }
    }
}
