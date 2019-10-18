package com.open.auto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.open.auto.crashui.CrashUI;
import com.open.auto.tbuilder.ListBuilder;
import com.open.auto.tbuilder.MapBuilder;

import java.util.List;
import java.util.Map;

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
        CrashUI.getInstance().work(this);
        //test
        CrashUI.getInstance().dotest("123");


    }
}
