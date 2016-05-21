package com.example.fhm.mymusic.entity;

import java.util.List;

/**
 * Created by fhm on 2016/5/7.
 */
public class Pagebean {
    private int currentPage;


    private int ret_code;


    private List<Songlist> songlist;


    private int total_song_num;


    public void setCurrentPage(int currentPage) {

        this.currentPage = currentPage;

    }

    public int getCurrentPage() {

        return this.currentPage;

    }

    public void setRet_code(int ret_code) {

        this.ret_code = ret_code;

    }

    public int getRet_code() {

        return this.ret_code;

    }

    public void setSonglist(List<Songlist> songlist) {

        this.songlist = songlist;

    }

    public List<Songlist> getSonglist() {

        return this.songlist;

    }

    public void setTotal_song_num(int total_song_num) {

        this.total_song_num = total_song_num;

    }

    public int getTotal_song_num() {

        return this.total_song_num;

    }
}
