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
import com.example.bailyhome.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class StuInfoBaseAdapter extends BaseAdapter {
    public Context context;
    public List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    public LayoutInflater inflater;

    public StuInfoBaseAdapter(Context context, List<Map<String, String>> list) {
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
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_stu_info_item, null);
            viewHolder.className = (TextView) view.findViewById(R.id.className);
            viewHolder.classSize = (TextView) view.findViewById(R.id.classSize);
            viewHolder.purchaseClass = (TextView) view.findViewById(R.id.purchase_class);
            viewHolder.addClass = (TextView) view.findViewById(R.id.add_class);
            viewHolder.finishClass = (TextView) view.findViewById(R.id.finish_class);
            viewHolder.remainClass = (TextView) view.findViewById(R.id.remain_class);
            viewHolder.tvStartScore = (TextView) view.findViewById(R.id.tv_start_score);
            viewHolder.tvMockExamScore = (TextView) view.findViewById(R.id.tv_mock_exam_score);
            viewHolder.tvTargetScore = (TextView) view.findViewById(R.id.tv_target_score);
            viewHolder.tvExamTime = (TextView) view.findViewById(R.id.tv_exam_time);
            viewHolder.ivImage = (ImageView) view.findViewById(R.id.iv_image);
            viewHolder.llGone = (LinearLayout) view.findViewById(R.id.ll_gone);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Map<String, String> map = list.get(i);
        viewHolder.className.setText(map.get("className"));
        viewHolder.classSize.setText(map.get("classSize"));
        viewHolder.purchaseClass.setText(map.get("purchaseClass"));
        viewHolder.addClass.setText(map.get("addClass"));
        viewHolder.finishClass.setText(map.get("finishClass"));
        viewHolder.remainClass.setText(map.get("remainClass"));
        viewHolder.tvStartScore.setText(map.get("tvStartScore"));
        viewHolder.tvMockExamScore.setText(map.get("tvMockExamScore"));
        viewHolder.tvTargetScore.setText(map.get("tvTargetScore"));
        viewHolder.tvExamTime.setText(map.get("tvExamTime"));
        viewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.llGone.getVisibility() == View.GONE) {
                    viewHolder.llGone.setVisibility(View.VISIBLE);
                    viewHolder.ivImage.setImageResource(R.mipmap.choosebar_press_up);
                } else {
                    viewHolder.llGone.setVisibility(View.GONE);
                    viewHolder.ivImage.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });

        return view;
    }

    class ViewHolder {
        public TextView className, classSize, purchaseClass, addClass, finishClass, remainClass, tvStartScore, tvMockExamScore, tvTargetScore, tvExamTime;
        public ImageView ivImage;
        public LinearLayout llGone;
    }
}
