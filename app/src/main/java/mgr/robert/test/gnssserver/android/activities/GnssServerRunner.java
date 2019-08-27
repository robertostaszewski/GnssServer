package mgr.robert.test.gnssserver.android.activities;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

public class GnssServerRunner {

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    public void startServer(Context context, Intent intent) {
        if (!isRunning.get()) {
            context.startService(intent);
            isRunning.set(true);
        } else {
            Toast.makeText(context, "Server is currently running", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopServer(Context context, Intent intent) {
        context.stopService(intent);
        isRunning.set(false);
    }
}
