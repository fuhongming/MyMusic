package com.example.fhm.mymusic.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.fhm.mymusic.R;
import com.example.fhm.mymusic.view.MoveView;
import com.example.fhm.mymusic.view.PaintView;


/**
 * Created by fhm on 2016/5/19.
 */
public class DrawingBoardActivity extends Activity {

    MoveView ballView;
    Animation anim;
    Button btnStart;
    Button btnClear;
    PaintView paintView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        paintView= (PaintView) findViewById(R.id.paintView);
        ballView= (MoveView) findViewById(R.id.ballView);
    }
    public void onClick(View v){
        anim= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        ballView.startAnimation(anim);
    }
    public void onStart(View v){
        Intent i=new Intent(DrawingBoardActivity.this,CirclularSpreadActivity.class);
        startActivity(i);
    }
    public void onClear(View v){
        paintView.clear();
    }

}
