package com.open.auto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.open.auto.crashui.CrashUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //preview demo
//        CrashUI.getInstance().listen(this).maketest();

        //usage two
//        CrashUI.getInstance().listen(this);
//        CrashUI.getInstance().report();
        //usage one
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        CrashUI.getInstance().listen((Activity) this);
        CrashUI.getInstance().report();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //test
                CrashUI.getInstance().dotest("123");

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (!(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
            )) {
                finish();
            }

        }
    }
}