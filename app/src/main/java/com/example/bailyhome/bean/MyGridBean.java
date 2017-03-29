package com.example.bailyhome.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/14 0014.
 */
public class MyGridBean implements Serializable {
    private int img;
    private  String tvName;

    public MyGridBean(int img, String tvName) {
        this.img = img;
        this.tvName = tvName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }
}
