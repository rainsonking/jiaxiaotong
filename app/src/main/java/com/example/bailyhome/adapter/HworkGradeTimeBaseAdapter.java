package com.example.bailyhome.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bailyhome.ClassWorkActivity;
import com.example.bailyhome.MoKaoDetailActivity;
import com.example.bailyhome.R;
import com.example.bailyhome.StateTestActivity;
import com.example.bailyhome.TeachLogActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/16 0016.
 * <p/>
 * 作业成绩、阶段测试、模考成绩
 */
public class HworkGradeTimeBaseAdapter extends BaseAdapter {
    public Context context;
    public List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    public LayoutInflater inflater;
    public String tag;

    public HworkGradeTimeBaseAdapter(Context context, List<Map<String, String>> list, String tag) {
        this.context = context;
        this.list = list;
        this.tag = tag;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.fragment_hwork_grade_time_item, null);
            viewHolder.detail = (TextView) view.findViewById(R.id.detail);
            if (tag.equals("1")) {
                viewHolder.classDate = (TextView) view.findViewById(R.id.class_date);
                viewHolder.classHour = (TextView) view.findViewById(R.id.class_hour);
                viewHolder.testResult = (TextView) view.findViewById(R.id.test_result);
            } else if ("2".equals(tag)) {
                viewHolder.testResultText = (TextView) view.findViewById(R.id.test_result_text);
                viewHolder.classHourLl = (LinearLayout) view.findViewById(R.id.class_hour_ll);
                viewHolder.classDate = (TextView) view.findViewById(R.id.class_date);
                viewHolder.testResult = (TextView) view.findViewById(R.id.test_result);
                viewHolder.testResultText.setText("阶段测试成绩");
                viewHolder.classHourLl.setVisibility(View.GONE);
            } else if ("3".equals(tag)) {
                viewHolder.testResultText = (TextView) view.findViewById(R.id.test_result_text);
                viewHolder.classHourLl = (LinearLayout) view.findViewById(R.id.class_hour_ll);
                viewHolder.classDate = (TextView) view.findViewById(R.id.class_date);
                viewHolder.testResult = (TextView) view.findViewById(R.id.test_result);
                viewHolder.testResultText.setText("模考成绩");
                viewHolder.classHourLl.setVisibility(View.GONE);
            }
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Map<String, String> map = list.get(i);
        if ("1".equals(tag)) {
            viewHolder.classDate.setText(map.get("classDate"));
            viewHolder.classHour.setText(map.get("classHour"));
            viewHolder.testResult.setText(map.get("testResult"));
            viewHolder.detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClassWorkActivity.class);
                    intent.putExtra("finishRate", map.get("finishRate"));
                    intent.putExtra("classDate", map.get("classDate"));
                    intent.putExtra("testResult", map.get("testResult"));
                    context.startActivity(intent);
                }
            });
        } else if ("2".equals(tag)) {
            viewHolder.classDate.setText(map.get("correctDate"));
            viewHolder.testResult.setText(map.get("testResult"));
            viewHolder.detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, StateTestActivity.class);
                    intent.putExtra("className",map.get("className"));
                    intent.putExtra("teacher",map.get("teacher"));
                    intent.putExtra("publishDate",map.get("publishDate"));
                    intent.putExtra("correctDate",map.get("correctDate"));
                    intent.putExtra("classDate",map.get("classDate"));
                    intent.putExtra("testResult",map.get("testResult"));
                    context.startActivity(intent);
                }
            });
        } else if ("3".equals(tag)) {
            viewHolder.classDate.setText(map.get("classDate"));
            viewHolder.testResult.setText(map.get("testResult"));
            viewHolder.detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MoKaoDetailActivity.class);
                    intent.putExtra("mockFeedback",map.get("mockFeedback"));
                    intent.putExtra("testResult",map.get("testResult"));
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }

    class ViewHolder {
        public TextView classDate, classHour, testResult, classHourText, testResultText;
        public LinearLayout classHourLl;
        public TextView detail;
    }
}
