package com.example.bailyhome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.bailyhome.MoreItemFirstActivity;
import com.example.bailyhome.R;
import com.example.bailyhome.StageSumReportActivity;
import com.example.bailyhome.model.OnDataListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/13 0013.
 *
 * 更多模块
 */
public class MoreFragment extends android.support.v4.app.Fragment implements OnDataListener {
    private LinearLayout llFirst, llSecond;
    private String USERID,USERNAME;
    private ArrayList<String> liststr = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, null);
        llFirst = (LinearLayout) view.findViewById(R.id.ll_first);
        llSecond = (LinearLayout) view.findViewById(R.id.ll_second);
       Bundle data = getArguments();
        USERID = data.getString("USERID");
        USERNAME = data.getString("USERNAME");
        liststr = data.getStringArrayList("liststr");
        llFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MoreItemFirstActivity.class);
                startActivity(intent);
            }
        });
        llSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StageSumReportActivity.class);
                intent.putExtra("USERID",USERID);
                intent.putExtra("USERNAME",USERNAME);
                intent.putExtra("title", "阶段总结报告");
                intent.putStringArrayListExtra("liststr",liststr);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onGetDataSuccess(String jsonData) {

    }

    @Override
    public void onGetDataError() {

    }

    @Override
    public void onLoading(long total, long current) {

    }
}
