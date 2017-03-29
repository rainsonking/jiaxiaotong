package com.example.bailyhome.model;

/**
 * Created by Administrator on 2016/5/12 0012.
 *
 *
 */
public interface OnDataListener {
    void onGetDataSuccess(String jsonData);
    void onGetDataError();
    void onLoading(long total, long current);
}
