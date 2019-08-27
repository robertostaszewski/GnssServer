package mgr.robert.test.gnssserver.android.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import mgr.robert.test.gnssserver.android.services.GnssServerService;

public class StopReceiver extends BroadcastReceiver {

    private final GnssServerRunner gnssServerRunner;
    public static final String ACTION_STOP = "stop";

    public StopReceiver(GnssServerRunner gnssServerRunner) {
        this.gnssServerRunner = gnssServerRunner;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        gnssServerRunner.stopServer(context, new Intent(context, GnssServerService.class));
    }
}
