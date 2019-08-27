package mgr.robert.test.gnssserver.android.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mgr.robert.test.gnssserver.android.activities.StopReceiver;
import mgr.robert.test.gnssserver.network.ConcurrentSubscriberService;
import mgr.robert.test.gnssserver.network.NetworkManager;
import mgr.robert.test.gnssserver.network.NetworkService;
import mgr.robert.test.gnssserver.R;
import mgr.robert.test.gnssserver.android.activities.MainActivity;

public class GnssServerService extends IntentService {
    private final ExecutorService pool = Executors.newFixedThreadPool(2);
    private final NetworkManager networkManager = new NetworkManager(new ConcurrentSubscriberService());

    private int producerPort;
    private int consumerPort;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public GnssServerService() {
        super("GnssServer");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        producerPort = intent.getIntExtra("producerPort", 8083);
        consumerPort = intent.getIntExtra("consumerPort", 8084);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        startForeground(1, getNotification());
        try {
            NetworkService producerNetwork = networkManager.getProducerNetwork(producerPort);
            NetworkService consumerNetwork = networkManager.getConsumerNetwork(consumerPort);

            pool.execute(consumerNetwork);
            pool.submit(producerNetwork).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        pool.shutdown();
        networkManager.closeAll();
        pool.shutdownNow();
        super.onDestroy();
    }

    private Notification getNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Intent stopIntent = new Intent(StopReceiver.ACTION_STOP);
        PendingIntent stop = PendingIntent.getBroadcast(this, 0, stopIntent, 0);

        String channelId = "channelId";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "channelName";
            NotificationChannel chan = new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_DEFAULT);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(chan);
        }

        return new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.icon_file_pdf)
                        .setContentTitle("title")
                        .setContentText("text")
                        .setContentIntent(pendingIntent)
                        .setTicker("ticker")
                        .addAction(R.drawable.gallery_album_overlay, "Stop", stop)
                        .build();
    }


}
