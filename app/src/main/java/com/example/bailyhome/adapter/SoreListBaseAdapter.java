package com.example.bailyhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bailyhome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/14 0014.
 */
public class SoreListBaseAdapter extends BaseAdapter {

    public Context context;
    public List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    public LayoutInflater inflater;

    public SoreListBaseAdapter(List<Map<String, String>> list, Context context) {
        this.list = list;
        this.context = context;
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
            view = inflater.inflate(R.layout.score_list_item, null);
            viewHolder.textItemLeft = (TextView) view.findViewById(R.id.text_item_left);
            viewHolder.textItemRight = (TextView) view.findViewById(R.id.text_item_right);
            view.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        Map<String, String> map = list.get(i);
        viewHolder.textItemLeft.setText(map.get("textItemLeft"));
        viewHolder.textItemRight.setText(map.get("textItemRight"));

        return view;
    }

    class ViewHolder
    {
        public TextView textItemLeft, textItemRight;
    }
}
