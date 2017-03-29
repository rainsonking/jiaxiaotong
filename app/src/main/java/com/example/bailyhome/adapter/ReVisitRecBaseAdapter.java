package com.example.bailyhome.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bailyhome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class ReVisitRecBaseAdapter extends BaseAdapter {
    public Context context;
    public List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    public LayoutInflater inflater;
    public String name;

    public ReVisitRecBaseAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
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
            view = inflater.inflate(R.layout.activity_revisit_record_item, null);
            viewHolder.iv_title_left = (ImageView) view.findViewById(R.id.iv_title_left);
            viewHolder.className = (TextView) view.findViewById(R.id.class_name);
            viewHolder.visitDate = (TextView) view.findViewById(R.id.visit_date);
            viewHolder.visitTeacher = (TextView) view.findViewById(R.id.visit_teacher);
            viewHolder.visitResult = (TextView) view.findViewById(R.id.visit_result);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Map<String, String> map = list.get(i);
        name = map.get("reVisitStage");
        if ("久未上课回访".equals(name)) {
            viewHolder.iv_title_left.setImageResource(R.mipmap.visit_record_curriculum);
        } else if ("每日回访".equals(name)) {
            viewHolder.iv_title_left.setImageResource(R.mipmap.visit_record_daily);
        }else if ("初次回访".equals(name)) {
            viewHolder.iv_title_left.setImageResource(R.mipmap.visit_record_first);
        }else if ("阶段回访".equals(name)) {
            viewHolder.iv_title_left.setImageResource(R.mipmap.visit_record_stage);
        }else if ("头脑风暴".equals(name)) {
            viewHolder.iv_title_left.setImageResource(R.mipmap.visit_record_idea);
        }else{
            viewHolder.iv_title_left.setImageResource(R.mipmap.visit_record_teacher);
        }
        viewHolder.className.setText(name);
        viewHolder.visitDate.setText(map.get("reVisitDate"));
        viewHolder.visitTeacher.setText(map.get("reVisitTech"));
        viewHolder.visitResult.setText(map.get("reVisitRes"));
        return view;
    }

    class ViewHolder {
        public TextView className, visitDate, visitTeacher, visitResult;
        public ImageView iv_title_left;
    }

}
