package com.example.fhm.mymusic.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fhm.mymusic.R;
import com.example.fhm.mymusic.entity.Songlist;
import com.example.fhm.mymusic.utils.BitmapHelp;
import com.example.fhm.mymusic.utils.T;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by fhm on 2016/5/7.
 */
public class PlayerActivity extends BaseActivity {

    @ViewInject(R.id.imgPrevious)
    ImageView imgPrevious;

    @ViewInject(R.id.imgPlay)
    ImageView imgPlay;

    @ViewInject(R.id.imgNext)
    ImageView imgNext;

    @ViewInject(R.id.img)
    ImageView img;

    @ViewInject(R.id.tvSong)
    TextView tvSong;

    @ViewInject(R.id.tvSinger)
    TextView tvSinger;

    @ViewInject(R.id.tvTime)
    TextView tvTime;

    @ViewInject(R.id.tvDuration)
    TextView tvDuration;

    @ViewInject(R.id.seekBar)
    SeekBar seekBar;
    @ViewInject(R.id.imgChange)
    ImageView imgChange;

    MediaPlayer mediaPlayer;
    Songlist songlist;
    BitmapUtils bitmapUtils;
    Timer timer;
    boolean random = false;

    /* @Override
     protected void onStart() {
         super.onStart();
         Log.i("Test","onStart");
     }

     @Override
     protected void onResume() {
         super.onResume();
         Log.i("Test", "onResume");

     }

     @Override
     protected void onPause() {
         super.onPause();
         Log.i("Test", "onPause");

     }

     @Override
     protected void onStop() {
         Log.i("Test", "onStop");

         super.onStop();
     }
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Test", "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        bitmapUtils = BitmapHelp.getBitmapUtils(this);
        ViewUtils.inject(this);
        if (readIntData("random") == 1) {
            random = true;
        }
        if(MainActivity.list.size()==0){
            return;
        }
        songlist = MainActivity.list.get(MainActivity.index);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                updateData();
            }
        });
        // 播放完成监听
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (random) {
                    randomPlay();
                } else {
                    playNext();
                }

            }
        });
        //设置定时器
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 100);
        //播放进度
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress() * mediaPlayer.getDuration() / 100);
            }
        });
        playMusic(MainActivity.index);
    }

    //    更新数据
    private void updateData() {
        tvSong.setText(songlist.getSongname());
        tvSinger.setText(songlist.getSingername());
//        tvDownUrl.setText(songlist.getDownUrl());
        bitmapUtils.display(img, songlist.getAlbumpic_big());
//        tvDuration.setText(songlist.getSeconds() / 60 + ":" + songlist.getSeconds() % 60 + "");
        tvDuration.setText(T.convertTimeFormat(mediaPlayer.getDuration()));
    }

    //     播放音乐
    private void playMusic(int pos) {
        songlist = MainActivity.list.get(pos);
        updateData();
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, Uri.parse(songlist.getUrl()));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @OnClick(R.id.img)
    private void img(View v) {
        Intent i = new Intent(PlayerActivity.this,DrawingBoardActivity.class);
        startActivity(i);
    }
//    切换随机、顺序播放
    @OnClick(R.id.imgChange)
    public void imgChange(View v) {
        if (mediaPlayer == null) {
            return;
        }
        if (random) {
            random=false;
            writeIntData("random" ,0);
            imgChange.setImageResource(R.drawable.order);
        } else {
            random=true;
            writeIntData("random" ,1);
            imgChange.setImageResource(R.drawable.random);
        }
    }

//      歌曲播放、暂停
    @OnClick(R.id.imgPlay)
    public void imgPlay(View v) {
        if (mediaPlayer == null) {
            return;
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            imgPlay.setImageResource(R.drawable.btn_play_nor);
        } else {
            mediaPlayer.start();
            imgPlay.setImageResource(R.drawable.btn_pause_nor);
        }
    }

    @OnClick(R.id.imgPrevious)
    public void imgPrevious(View v) {
        playPrevious();
    }

    /**
     * 播放上一首
     */
    private void playPrevious() {
        Log.i(T.TAG, "MainActivity.index " + MainActivity.index);
        --MainActivity.index;
        if (MainActivity.index < 0) {
            MainActivity.index = MainActivity.list.size() - 1;
        }
        playMusic(MainActivity.index);
    }

    @OnClick(R.id.imgNext)
    public void imgNext(View v) {
        playNext();
    }

    /**
     * 播放下一首
     */
    private void playNext() {
        ++MainActivity.index;
        if (MainActivity.index == MainActivity.list.size()) {
            MainActivity.index = 0;
        }
        playMusic(MainActivity.index);
    }

    /**
     * 随机播放
     */
    private void randomPlay() {
        int random = new Random().nextInt(MainActivity.list.size());
        playMusic(random);
    }

    @Override
    protected void onDestroy() {
        Log.i("Test","onDestroy");
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setView();
                    break;
            }
        }
    };

    private void setView() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPos = mediaPlayer.getCurrentPosition();
//        Log.i(T.TAG, "currentPos " + currentPos + ", mediaPlayer.getDuration()　" + (mediaPlayer.getDuration()) + ", currentPos / mediaPlayer.getDuration() " + (currentPos * 100 / mediaPlayer.getDuration()));
            int progress = currentPos * 100 / mediaPlayer.getDuration();
            seekBar.setProgress(progress);
            tvTime.setText(T.convertTimeFormat(currentPos - 500));
        }
    }
}
