package com.example.bailyhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bailyhome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/16 0016.
 *
 * 课首测试、考试成绩
 */
public class FirstTextTimeBaseAdapter extends BaseAdapter {
    public Context context;
    public List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    public LayoutInflater inflater;
    public String tag;

    public FirstTextTimeBaseAdapter(List<Map<String, String>> list, Context context, String tag) {
        this.list = list;
        this.context = context;
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
            view = inflater.inflate(R.layout.fragment_first_text_time_item, null);
            if (tag.equals("0")) {
                viewHolder.classDate = (TextView) view.findViewById(R.id.class_date_text);
                viewHolder.classHour = (TextView) view.findViewById(R.id.class_hour);
                viewHolder.testResult = (TextView) view.findViewById(R.id.test_result);

            } else if ("4".equals(tag)) {
                viewHolder.testResultText = (TextView) view.findViewById(R.id.test_result_text);
                viewHolder.classHourLl = (LinearLayout) view.findViewById(R.id.class_hour_ll);
                viewHolder.classDate = (TextView) view.findViewById(R.id.class_date_text);
                viewHolder.testResult = (TextView) view.findViewById(R.id.test_result);
                viewHolder.testResultText.setText("考试成绩");
                viewHolder.classHourLl.setVisibility(View.GONE);
            }
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Map<String, String> map = list.get(i);
        if ("0".equals(tag)) {
            viewHolder.classDate.setText(map.get("classDate"));
            viewHolder.classHour.setText(map.get("classHour"));
            viewHolder.testResult.setText(map.get("testResult"));

        } else if ("4".equals(tag)) {
            viewHolder.classDate.setText(map.get("classDate"));
            viewHolder.testResult.setText(map.get("testResult"));
        }
        return view;
    }

    class ViewHolder {
        public TextView classDate, classHour, testResult, classDateText, classHourText, testResultText;
        public LinearLayout classHourLl;

    }
}
