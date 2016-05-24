package com.example.fhm.mymusic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by fhm on 2016/5/20.
 */
public class MyView extends View implements Runnable {
    boolean flag, isHidding;//循环标志位
    Paint paint;
    float x, y;//记录点击点的坐标
    float radius;//记录圆的半径
    Path path;
    Random random = new Random();
    List<Part> list =new ArrayList<Part>();
    final int[] COLOR = {Color.BLUE, Color.RED, Color.YELLOW, Color.BLACK, Color.WHITE, Color.TRANSPARENT, Color.CYAN, Color.GRAY, Color.DKGRAY};

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    class Part {
        float x, y;
        float radius;//半径
        int color;//圆的颜色

        public Part(float x, float y, float radius, int color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    public MyView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);//颜色透明
        paint.setAntiAlias(true);//抗锯齿
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);//画笔粗细
//        paint.setStrokeCap(Paint.Cap.ROUND);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                invalidate();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag = true;
                isHidding = true;
                list.clear();
                break;
            case MotionEvent.ACTION_MOVE:
                int color = COLOR[random.nextInt(COLOR.length)];
                list.add(new Part(event.getX(), event.getY(), 0, color));
                break;

        }
        return true;
    }

    public void run() {
        while (flag) {
            //通知画布重绘
            postInvalidate();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isHidding) {

            for (Part part:list){
                part.radius += 5;
                paint.setColor(part.color);
                canvas.drawCircle(part.x,part.y,part.radius,paint);
            }
        }
        super.onDraw(canvas);
//        path.moveTo(30,30);
//        path.lineTo(30,50);
       /* canvas.drawCircle(100, 100, 50, paint);
        canvas.drawLine(100, 100, 0, 100, paint);
        canvas.drawLine(100, 150, 100, 400, paint);
        canvas.drawLine(100, 200, 20, 200, paint);
        canvas.drawLine(100, 400, 70, 500, paint);
        canvas.drawLine(100, 400, 130, 500, paint);
        canvas.drawLine(100, 300, 500, 150, paint);
        canvas.drawLine(500, 200, 500, 100, paint);
        canvas.drawRect(400, 120, 120, 300, paint);*/
    }
}
