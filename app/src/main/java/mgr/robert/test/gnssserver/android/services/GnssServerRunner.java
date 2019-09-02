package mgr.robert.test.gnssserver.android.services;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

public class GnssServerRunner {

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    public void startServer(Context context, int producerPort, int ConsumerPort) {
        if (!isRunning.get()) {
            Intent intent = new Intent(context, GnssServerService.class);
            intent.putExtra("producerPort", producerPort);
            intent.putExtra("consumerPort", ConsumerPort);
            context.startService(intent);
            isRunning.set(true);
        } else {
            Toast.makeText(context, "Server is currently running", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopServer(Context context) {
        context.stopService(new Intent(context, GnssServerService.class));
        isRunning.set(false);
    }
}
