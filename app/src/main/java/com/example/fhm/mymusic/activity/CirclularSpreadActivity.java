package com.example.fhm.mymusic.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.fhm.mymusic.view.MyView;

public class CirclularSpreadActivity extends Activity {
    MyView myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView=new MyView(this);
        myView.setFlag(true);
        setContentView(myView);
        new Thread(myView).start();

    }

    @Override
    protected void onDestroy() {
        myView.setFlag(false);
        super.onDestroy();
    }
}
