package mgr.robert.test.gnssserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class SelectDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_data);
    }

    public void onButtonClickChartButton(View view) {
        FilePickerBuilder.getInstance()
                .setMaxCount(1)
                .setActivityTheme(R.style.LibAppTheme)
                .addFileSupport("LOG", new String[]{".log"}, R.drawable.icon_file_unknown)
                .pickFile(this);
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
