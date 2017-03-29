package com.example.bailyhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bailyhome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class LeaveRecBaseAdapter extends BaseAdapter {
    public Context context;
    public List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    public LayoutInflater inflater;

    public LeaveRecBaseAdapter(Context context, List<Map<String, String>> list) {
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
        if (view == null)
        {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_leave_record_item, null);
            viewHolder.tvDate = (TextView) view.findViewById(R.id.tv_date);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tvApprove = (TextView) view.findViewById(R.id.tv_approve);
            view.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        Map<String, String> map = list.get(i);
        viewHolder.tvDate.setText(map.get("orderDate"));
        viewHolder.tvName.setText(map.get("className"));
        viewHolder.tvApprove.setText(map.get("leaveState"));

        return view;
    }
    class ViewHolder {
        public TextView tvDate, tvName, tvApprove;
    }
}
