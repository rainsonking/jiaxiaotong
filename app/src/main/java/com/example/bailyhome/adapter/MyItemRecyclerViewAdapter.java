package com.example.bailyhome.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bailyhome.R;

import java.util.List;
import java.util.Map;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Map<String,Object>> mValues;
    private View.OnClickListener onClickListener;

    public MyItemRecyclerViewAdapter(List<Map<String,Object>> items, View.OnClickListener onClickListener) {
        mValues = items;
        this.onClickListener=onClickListener;
    }

    public void setData(List<Map<String,Object>> datas) {
        mValues.clear();
        mValues.addAll(datas);
    }

    public void addDatas(List<Map<String,Object>> datas) {
        mValues.addAll(datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_un_get_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder mHolder = (ViewHolder) holder;
        mHolder.mItem = mValues.get(position);
        String courseName=mValues.get(position).get("AFM_26")+"";
        String teacher=mValues.get(position).get("AFM_40")+"";
        String xiaoqu=mValues.get(position).get("AFM_36")+"";
        mHolder.tv_course_name.setText(!courseName.equals("null")?courseName:"");
        mHolder.tv_teacher.setText(!teacher.equals("null")?teacher:"");
        mHolder.tv_xiaoqu.setText(!xiaoqu.equals("null")?xiaoqu:"");


        String buyHourStr=String.valueOf(mValues.get(position).get("AFM_29"));
        String studyHourStr=String.valueOf(mValues.get(position).get("AFM_8"));
        String bushiHourStr=String.valueOf(mValues.get(position).get("add_hour"));
        String shengyuStr=String.valueOf(mValues.get(position).get("AFM_31"));
        String allHourStr=String.valueOf(mValues.get(position).get("AFM_28"));
        int buyHour=0;
        float studyHour=0;
        int bushiHour=0;
        int shengyuHour=0;
        float allHour=0;
        if (!TextUtils.isEmpty(buyHourStr)&&!buyHourStr.equals("null")) {
            buyHour =Integer.parseInt(buyHourStr);
        }
        if (!TextUtils.isEmpty(studyHourStr)&&!studyHourStr.equals("null")) {
            studyHour =Integer.parseInt(studyHourStr);
        }
        if (!TextUtils.isEmpty(bushiHourStr)&&!bushiHourStr.equals("null")) {
            bushiHour =Integer.parseInt(bushiHourStr);
        }
        if (!TextUtils.isEmpty(shengyuStr)&&!shengyuStr.equals("null")) {
            shengyuHour =Integer.parseInt(shengyuStr);
        }
        if (!TextUtils.isEmpty(allHourStr)&&!allHourStr.equals("null")) {
            allHour =Integer.parseInt(allHourStr);
        }

        float progress=(studyHour/allHour)*100;
        String peixunState=mValues.get(position).get("DIC_AFM_41")+"";
        if (!TextUtils.isEmpty(peixunState)) {
            if (peixunState.contains("开始培训")) {
                mHolder.tv_learn_rate2.setText((int)progress+"%");
                mHolder.iv_peixun_state.setImageResource(R.mipmap.img_peixun_state2);
            } else if (peixunState.contains("未开始")) {
                mHolder.tv_learn_rate2.setText("未开始");
                mHolder.iv_peixun_state.setImageResource(R.mipmap.img_peixun_state);
            } else if (peixunState.contains("已结课")) {
                mHolder.tv_learn_rate2.setText("100%");
                mHolder.iv_peixun_state.setImageResource(R.mipmap.img_peixun_state3);
            }else {
                mHolder.tv_learn_rate2.setText("该课程已退款停用");
                mHolder.tv_peixun_state.setVisibility(View.GONE);
                mHolder.iv_peixun_state.setVisibility(View.GONE);
            }
        }

        mHolder.progressBar.setProgress((int)progress);
        mHolder.tv_buy_hour_value.setText(buyHour+"");
        mHolder.tv_study_hour_value.setText((int)studyHour+"");
        mHolder.tv_bushi_hour_value.setText(bushiHour+"");
        mHolder.tv_shengyu_hour_value.setText(shengyuHour+"");
        View view=mHolder.mView;
        view.setTag(position);
        view.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tv_course_name;
        public final TextView tv_teacher;
        public final TextView tv_xiaoqu;
        public final TextView tv_peixun_state;
        public final TextView tv_learn_rate2;
        public final ProgressBar progressBar;
        public final TextView tv_buy_hour_value;
        public final TextView tv_study_hour_value;
        public final TextView tv_bushi_hour_value;
        public final TextView tv_shengyu_hour_value;
        public final ImageView iv_peixun_state;
        public Map<String,Object> mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tv_course_name = (TextView) view.findViewById(R.id.tv_course_name);
            tv_teacher = (TextView) view.findViewById(R.id.tv_teacher);
            tv_xiaoqu = (TextView) view.findViewById(R.id.tv_xiaoqu);
            tv_peixun_state = (TextView) view.findViewById(R.id.tv_peixun_state);
            tv_learn_rate2 = (TextView) view.findViewById(R.id.tv_learn_rate2);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            tv_buy_hour_value = (TextView) view.findViewById(R.id.tv_buy_hour_value);
            tv_study_hour_value = (TextView) view.findViewById(R.id.tv_study_hour_value);
            tv_bushi_hour_value = (TextView) view.findViewById(R.id.tv_bushi_hour_value);
            tv_shengyu_hour_value = (TextView) view.findViewById(R.id.tv_shengyu_hour_value);
            iv_peixun_state = (ImageView) view.findViewById(R.id.iv_peixun_state);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_course_name.getText() + "'";
        }
    }
}
