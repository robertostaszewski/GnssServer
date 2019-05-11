package mgr.robert.test.gnssserver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.v("PERM","Permission is not granted");
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
                intent.putExtra("sourcePort", Integer.parseInt(sourcePort.getText().toString()));
                intent.putExtra("serverPort", Integer.parseInt(serverPort.getText().toString()));
                intent.putExtra("bufferSize", Integer.parseInt(bufferSize.getText().toString()));
                startService(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GnssServerService.class);
                stopService(intent);
            }
        });

        selectDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectDataActivity.class);
                startActivity(intent);
            }
        });
    }


}
