package com.example.bailyhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bailyhome.R;
import com.example.bailyhome.bean.MyGridBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/14 0014.
 */
public class MyGridAdapter extends BaseAdapter {
    private Context context;
    public LayoutInflater inflater;
    private List<MyGridBean> lists = new ArrayList<MyGridBean>();
    public MyGridAdapter(Context mContext,List<MyGridBean> lists) {
        super();
        this.context = mContext;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
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
            view = inflater.inflate(R.layout.mygrid_item, null);
            viewHolder.iv_item = (ImageView) view.findViewById(R.id.iv_item);
            viewHolder.tv_item = (TextView) view.findViewById(R.id.tv_item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        MyGridBean myGridBean = lists.get(i);
        viewHolder.iv_item.setImageResource(myGridBean.getImg());
        viewHolder.tv_item.setText(myGridBean.getTvName());

        return view;
    }

    class ViewHolder {
        public ImageView iv_item;
        public TextView tv_item;
    }
}
