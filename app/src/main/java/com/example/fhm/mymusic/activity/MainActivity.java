package com.example.fhm.mymusic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.fhm.mymusic.R;
import com.example.fhm.mymusic.adapter.PlayListAdapter;
import com.example.fhm.mymusic.entity.Root;
import com.example.fhm.mymusic.entity.Songlist;
import com.example.fhm.mymusic.utils.T;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    boolean isLoc = true;
    public static List<Songlist> list = new ArrayList<>();
    public List<Songlist> listLoc = new ArrayList<>();
    public List<Songlist> listNet = new ArrayList<>();

    public static int index;

    @ViewInject(R.id.lv)
    ListView lv;

    @ViewInject(R.id.btnLoc)
    ImageView btnLoc;

    @ViewInject(R.id.btnNet)
    ImageView btnNet;
    @ViewInject(R.id.rl)
    RelativeLayout rl;
    PlayListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewUtils.inject(this);
        adapter = new PlayListAdapter(getApplicationContext());
        lv.setAdapter(adapter);
        getLocData();
    }

    private void getLocData() {
        isLoc = true;
        if (listLoc.size() > 0) {
            adapter.setData(listLoc);
            return;

        }
        // show
        rl.setVisibility(View.VISIBLE);
        new Thread(){
            public void run() {
                listLoc.clear();
                getFile(Environment.getExternalStorageDirectory().getAbsolutePath().toString());
                myHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    // hide
                    rl.setVisibility(View.INVISIBLE);
                    adapter.setData(listLoc);
                    break;
            }
        }
    };

    @OnItemClick(R.id.lv)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        index = position;
//        Log.i(TAG, "OnItemClick: " + "position" + position + ", " + id);
        Intent i = new Intent(MainActivity.this, PlayerActivity.class);
        if (isLoc) {
            list = listLoc;
        } else {
            list = listNet;
        }
        startActivity(i);
        overridePendingTransition(R.anim.in, R.anim.out);

    }

    @OnClick(R.id.btnLoc)
    public void btnLoc(View v) {
        getLocData();
        v.animate().rotationXBy(360).setDuration(1000).start();
    }

    /**
     * 获取音乐文件
     * @param path 音乐文件路径
     */
    public void getFile(String path){
        File f = new File(path);
        File[] fs = f.listFiles();
        if (fs != null) {
            for (int i = 0; i < fs.length; i++) {
                File ff = fs[i];
                if (ff.isDirectory()) {
                    getFile(ff.getAbsolutePath());
                } else if (ff.isFile()) {
                    if (ff.getName().endsWith(".mp3")) {
                        Songlist songlist = new Songlist();
                        songlist.setSongname(ff.getName());
                        songlist.setUrl(ff.getAbsolutePath());
                        listLoc.add(songlist);
                    }
                }
            }
        }
    }

    /**
     * 获取网络歌曲
     * @param v
     */
    @OnClick(R.id.btnNet)
    public void btnNet(View v) {
        isLoc = false;
        rl.setVisibility(View.INVISIBLE);
        v.animate().rotationYBy(360).setDuration(1000).start();
        getDataTask("https://route.showapi.com/213-4?showapi_appid=18616&topid=5&showapi_sign=BD3D1296015795C7C6E6084D59B45385");
    }

    public void getDataTask(String url) {
        RequestParams params = new RequestParams("UTF-8");
//        params.addBodyParameter("key", value);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i(T.TAG, "onSuccess: " + responseInfo.result);
                Gson gson = new Gson();
                Root root = gson.fromJson(responseInfo.result, Root.class);
                if(root==null){
                    return;
                }
                listNet.clear();
                listNet = root.getShowapi_res_body().getPagebean().getSonglist();
//                for (int i = 0; i < list.size(); i++) {
//                   Log.i(TAG, list.get(i).toString());
//                }
                if (!isLoc) {
                    adapter.setData(listNet);
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i(T.TAG, "onFailure: " + msg);
            }
        });
    }

}

