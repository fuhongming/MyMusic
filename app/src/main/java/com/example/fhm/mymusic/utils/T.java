package com.example.fhm.mymusic.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.fhm.mymusic.entity.Songlist;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by peter on 2015/12/10.
 */
public class T {

    public static final String TAG = "Peter";
    static Toast toast;

    public static String convertTimeFormat(int miliseconds) {
        int time = miliseconds / 1000;
        return  time / 60 + ":" + time % 60 + "";
    }

    public static void replaceActivity(Context context, Class<?> activity)
    {
        Intent intent = new Intent();
        intent.setClass(context, activity);
        context.startActivity(intent);
    }

    public static boolean isConnected(Context context) {
        if (context == null)
        {
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        addToast(context, "网络无连接");
        return false;
    }

    public static void addToast(Context context, String text) {
        if (null != toast) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
