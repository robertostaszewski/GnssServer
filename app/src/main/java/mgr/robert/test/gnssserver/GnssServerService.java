package mgr.robert.test.gnssserver;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GnssServerService extends IntentService {
    private int sourcePortNumber;
    private int dataPortNumber;
    private int bufferSize;
    boolean work = true;

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
        bufferSize = intent.getIntExtra("bufferSize", 1024);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String channelId = "channelId";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "channelName";
            NotificationChannel chan = new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_DEFAULT);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            service.createNotificationChannel(chan);
        }

        Notification notification =
                new NotificationCompat.Builder(this, channelId)
                        .setContentTitle("title")
                        .setContentText("text")
                        .setContentIntent(pendingIntent)
                        .setTicker("ticker")
                        .build();

        startForeground(1, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (work) {
            try {
                server();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        work = false;
        super.onDestroy();
    }

    public void server() throws Exception {
        try (ServerConn serverConn = new ServerConn(sourcePortNumber);
             ServerConn clientConn = new ServerConn(dataPortNumber)) {

            boolean isInitialized = false;
            while (!isInitialized) {
                String data = new Scanner(serverConn).useDelimiter("\\r\\n\\r\\n").next();
                Matcher get = Pattern.compile("^SOURCE").matcher(data);
                System.out.println(data);
                byte[] response = ("ICY 200 OK\r\n\r\n").getBytes("UTF-8");
                serverConn.write(response);
                isInitialized = true;
            }

            int len;
            byte[] b;
            while (work) {
                b = new byte[bufferSize];
                if ((len = serverConn.read(b)) != -1) {
                    clientConn.write(b, 0, len);
                } else {
                    throw new IOException("cannot read more");
                }
            }
        }
    }
}
