package mgr.robert.test.gnssserver.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import mgr.robert.test.gnssserver.R;
import mgr.robert.test.gnssserver.android.services.GnssServerRunner;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    private final GnssServerRunner gnssServerRunner = new GnssServerRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            Log.v("PERM", "Permission is not granted");
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 2);
        }
        Button start = findViewById(R.id.Start);
        Button stop = findViewById(R.id.Stop);
        Button selectDataButton = findViewById(R.id.selectDataButton);

        final EditText serverPort = findViewById(R.id.serverPort);
        final EditText sourcePort = findViewById(R.id.sourcePort);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gnssServerRunner.startServer(MainActivity.this,
                        Integer.parseInt(sourcePort.getText().toString()),
                        Integer.parseInt(serverPort.getText().toString()));
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gnssServerRunner.stopServer(MainActivity.this);
            }
        });

        selectDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePickerBuilder.getInstance()
                        .setMaxCount(1)
                        .setActivityTheme(R.style.LibAppTheme)
                        .enableDocSupport(false)
                        .addFileSupport("LOG", new String[]{".log"}, R.drawable.icon_file_unknown)
                        .pickFile(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == FilePickerConst.REQUEST_CODE_DOC
                && resultCode == Activity.RESULT_OK
                && data != null) {

            String dataPath = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS).get(0);

            Intent intent = new Intent(this, ChartActivity.class);
            intent.putExtra("dataPath", dataPaths.get(0));
            startActivity(intent);
        }
    }
}
