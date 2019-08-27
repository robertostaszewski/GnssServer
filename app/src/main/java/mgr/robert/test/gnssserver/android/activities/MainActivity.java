package mgr.robert.test.gnssserver.android.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import mgr.robert.test.gnssserver.android.services.GnssServerService;
import mgr.robert.test.gnssserver.R;

public class MainActivity extends AppCompatActivity {

    private GnssServerRunner gnssServerRunner = new GnssServerRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.v("PERM", "Permission is not granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
        Button start = findViewById(R.id.Start);
        Button stop = findViewById(R.id.Stop);
        Button selectDataButton = findViewById(R.id.selectDataButton);

        final EditText serverPort = findViewById(R.id.serverPort);
        final EditText sourcePort = findViewById(R.id.sourcePort);
        final EditText bufferSize = findViewById(R.id.bufferSize);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GnssServerService.class);
                intent.putExtra("producerPort", Integer.parseInt(sourcePort.getText().toString()));
                intent.putExtra("consumerPort", Integer.parseInt(serverPort.getText().toString()));
                intent.putExtra("bufferSize", Integer.parseInt(bufferSize.getText().toString()));
                gnssServerRunner.startServer(MainActivity.this, intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GnssServerService.class);
                gnssServerRunner.stopServer(MainActivity.this, intent);
            }
        });

        selectDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePickerBuilder.getInstance()
                        .setMaxCount(1)
                        .setActivityTheme(R.style.LibAppTheme)
                        .addFileSupport("LOG", new String[]{".log"}, R.drawable.icon_file_unknown)
                        .pickFile(MainActivity.this);
            }
        });

        registerReceiver(new StopReceiver(gnssServerRunner), new IntentFilter(StopReceiver.ACTION_STOP));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == FilePickerConst.REQUEST_CODE_DOC
                && resultCode == Activity.RESULT_OK
                && data != null) {

            ArrayList<String> dataPaths = new ArrayList<>(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));

            Intent intent = new Intent(this, ChartActivity.class);
            intent.putStringArrayListExtra("dataPaths", dataPaths);
            startActivity(intent);
        }
    }

}
