package com.example.fhm.mymusic.entity;

import java.io.Serializable;

/**
 * Created by fhm on 2016/5/7.
 */
public class Songlist implements Serializable{
    private int albumid;


    private String albummid;


    private String albumpic_big;


    private String albumpic_small;


    private String downUrl;


    private int seconds;


    private int singerid;


    private String singername;


    private int songid;


    private String songname;


    private String url; // 流媒体


    public void setAlbumid(int albumid) {

        this.albumid = albumid;

    }

    public int getAlbumid() {

        return this.albumid;

    }

    public void setAlbummid(String albummid) {

        this.albummid = albummid;

    }

    public String getAlbummid() {

        return this.albummid;

    }

    public void setAlbumpic_big(String albumpic_big) {

        this.albumpic_big = albumpic_big;

    }

    public String getAlbumpic_big() {

        return this.albumpic_big;

    }

    public void setAlbumpic_small(String albumpic_small) {

        this.albumpic_small = albumpic_small;

    }

    public String getAlbumpic_small() {

        return this.albumpic_small;

    }

    public void setDownUrl(String downUrl) {

        this.downUrl = downUrl;

    }

    public String getDownUrl() {

        return this.downUrl;

    }

    public void setSeconds(int seconds) {

        this.seconds = seconds;

    }

    public int getSeconds() {

        return this.seconds;

    }

    public void setSingerid(int singerid) {

        this.singerid = singerid;

    }

    public int getSingerid() {

        return this.singerid;

    }

    public void setSingername(String singername) {

        this.singername = singername;

    }

    public String getSingername() {

        return this.singername;

    }

    public void setSongid(int songid) {

        this.songid = songid;

    }

    public int getSongid() {

        return this.songid;

    }

    public void setSongname(String songname) {

        this.songname = songname;

    }

    public String getSongname() {

        return this.songname;

    }

    public void setUrl(String url) {

        this.url = url;

    }

    public String getUrl() {

        return this.url;

    }

    @Override
    public String toString() {
        return "Songlist{" +
                "albumid=" + albumid +
                ", albummid='" + albummid + '\'' +
                ", albumpic_big='" + albumpic_big + '\'' +
                ", albumpic_small='" + albumpic_small + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", seconds=" + seconds +
                ", singerid=" + singerid +
                ", singername='" + singername + '\'' +
                ", songid=" + songid +
                ", songname='" + songname + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
