package com.example.bailyhome.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bailyhome.R;
import com.example.bailyhome.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class StaSumFirstBaseAdapter extends BaseAdapter {
    public Context context;
    public List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    public LayoutInflater inflater;
    public String tag;

    public StaSumFirstBaseAdapter(Context context, List<Map<String, String>> list,String tag) {
        this.context = context;
        this.list = list;
        this.tag=tag;
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
        if (view == null)
        {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_listview_stasum_first_item, null);
            viewHolder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            viewHolder.tv_score_left = (TextView) view.findViewById(R.id.tv_score_left);
            viewHolder.tv_score = (TextView) view.findViewById(R.id.tv_score);
            view.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        Map<String, String> map = list.get(i);
        if ("1".equals(tag)){
            viewHolder.tv_score_left.setText("考试成绩：");
        }else {
            viewHolder.tv_score_left.setText("阶段测试成绩：");
        }
        viewHolder.tv_date.setText(map.get("testDate"));
        viewHolder.tv_score.setText(map.get("testScore")+"分");
        return view;
    }
    class ViewHolder {
        public TextView tv_date, tv_score,tv_score_left;
    }
}
