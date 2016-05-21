package com.example.fhm.mymusic.entity;

/**
 * Created by fhm on 2016/5/7.
 */
public class Showapi_res_body {
    private Pagebean pagebean;


    private int ret_code;


    public void setPagebean(Pagebean pagebean){

        this.pagebean = pagebean;

    }

    public Pagebean getPagebean(){

        return this.pagebean;

    }

    public void setRet_code(int ret_code){

        this.ret_code = ret_code;

    }

    public int getRet_code(){

        return this.ret_code;

    }
}
