package com.example.fhm.mymusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fhm.mymusic.R;
import com.example.fhm.mymusic.entity.Songlist;
import com.example.fhm.mymusic.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fhm on 2016/5/8.
 */
public class PlayListAdapter extends BaseAdapter {
    List<Songlist> list = new ArrayList<>();
    Context context;
    BitmapUtils bitmapUtils;

    public PlayListAdapter(Context context) {
        this.context = context;
        bitmapUtils = BitmapHelp.getBitmapUtils(context);
    }

    public void setData(List<Songlist> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            holder = new ViewHolder();
                /* 得到各个控件的对象 */
            holder.tvSinger = (TextView) convertView.findViewById(R.id.tvSinger);
            holder.tvSong = (TextView) convertView.findViewById(R.id.tvSong);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            convertView.setTag(holder);// 绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
        }
//        if(list.size() < position){
//            return convertView;
//        }
        String singer = list.get(position).getSingername();
        String song = list.get(position).getSongname();
        holder.tvSinger.setText(singer);
        holder.tvSong.setText(song);

        bitmapUtils.display(holder.iv, list.get(position).getAlbumpic_small());

        return convertView;
    }

    class ViewHolder {
        TextView tvSong;
        TextView tvSinger;
        ImageView iv;
    }
}

