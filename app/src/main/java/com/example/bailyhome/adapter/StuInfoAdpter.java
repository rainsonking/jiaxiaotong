package com.example.bailyhome.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bailyhome.ClassRecordActivity;
import com.example.bailyhome.CourseInfoActivity;
import com.example.bailyhome.CoursePingjiaActivity;
import com.example.bailyhome.FujianActivity;
import com.example.bailyhome.R;
import com.example.bailyhome.StagePlanActivity;
import com.example.bailyhome.StageSumReportActivity;
import com.example.bailyhome.bean.MyGridBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/20 0020.
 */
public class StuInfoAdpter extends BaseAdapter {
    private Context context;
    private boolean isShow[] = new boolean[50];
    List<Map<String, Object>> mDatas;
    private String USERNAME, STU_SEX;

    public StuInfoAdpter(Context context, List<Map<String, Object>> mDatas, String stuName, String STU_SEX) {
        this.context = context;
        this.mDatas = mDatas;
        this.USERNAME = stuName;
        this.STU_SEX = STU_SEX;
        for (int i = 0; i < isShow.length; i++) {
            isShow[i] = false;
        }

    }

    @Override
    public int getCount() {
        return mDatas.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        switch (type) {
            case 0:
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_stu_info_top, null);
                ViewHolder viewHolder = new ViewHolder(convertView);
                viewHolder.stuName.setText(USERNAME+"同学");
                if ("168".equals(STU_SEX)) {
                    viewHolder.iv_image.setImageResource(R.mipmap.female_pic);
                } else {
                    viewHolder.iv_image.setImageResource(R.mipmap.male_pic);
                }
                String start_time = mDatas.get(position).get("AFM_17") + "";
                String abroad_time = mDatas.get(position).get("DIC_AFM_18") + "";
                viewHolder.start_time.setText(!start_time.equals("null") ? start_time.substring(0, 10) : "");
                viewHolder.abroad_time.setText(!abroad_time.equals("null") ? abroad_time : "");
                break;
            case 1:
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_stu_info_buttom, null);
                final ViewHolder1 viewHolder1 = new ViewHolder1(convertView);
                String course_name = mDatas.get(position - 1).get("AFM_19") + "";
                String course_hour = mDatas.get(position - 1).get("AFM_21") + "";
                String buy_hour_value = mDatas.get(position - 1).get("AFM_22") + "";
                String learn_hour_value = mDatas.get(position - 1).get("AFM_8") + "";
                String bushi_value = mDatas.get(position - 1).get("add_hour") + "";
                String shengyu_value = mDatas.get(position - 1).get("AFM_24") + "";
                viewHolder1.tv_course_name.setText(!course_name.equals("null") ? course_name : "");
                viewHolder1.tv_course_hour.setText(!course_hour.equals("null") ? (course_hour + "课时") : "0");
                viewHolder1.tv_buy_hour_value.setText(!buy_hour_value.equals("null") ? buy_hour_value : "0");
                viewHolder1.tv_learn_hour_value.setText(!learn_hour_value.equals("null") ? learn_hour_value : "0");
                viewHolder1.tv_bushi_value.setText(!bushi_value.equals("null") ? bushi_value : "0");
                viewHolder1.tv_shengyu_value.setText(!shengyu_value.equals("null") ? shengyu_value : "0");
                if (isShow[position]) {
                    viewHolder1.layout_expansion_detail.setVisibility(View.VISIBLE);
                    viewHolder1.iv_on_off.setImageResource(R.mipmap.arrow_bottom);
                    String start_score = mDatas.get(position - 1).get("AFM_26") + "";
                    String mokao_score = mDatas.get(position - 1).get("AFM_25") + "";
                    String end_score = mDatas.get(position - 1).get("AFM_28") + "";
                    String test_time = mDatas.get(position - 1).get("AFM_27") + "";
                    viewHolder1.tv_start_score.setText(!start_score.equals("null") ? (start_score + "分") : "0");
                    viewHolder1.tv_mokao_score.setText(!mokao_score.equals("null") ? (mokao_score + "分") : "0");
                    viewHolder1.tv_end_score.setText(!end_score.equals("null") ? (end_score + "分") : "0");
                    viewHolder1.tv_test_time.setText(!test_time.equals("null") ? test_time.substring(0, 10) : "暂无数据");
                } else {
                    viewHolder1.layout_expansion_detail.setVisibility(View.GONE);
                    viewHolder1.iv_on_off.setImageResource(R.mipmap.arrow_right);
                }
                viewHolder1.layout_expansion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow[position] = !isShow[position];
                        if (viewHolder1.layout_expansion_detail.getVisibility() == View.VISIBLE) {
                            viewHolder1.layout_expansion_detail.setVisibility(View.GONE);
                            viewHolder1.iv_on_off.setImageResource(R.mipmap.arrow_right);
                        } else {
                            viewHolder1.layout_expansion_detail.setVisibility(View.VISIBLE);
                            viewHolder1.iv_on_off.setImageResource(R.mipmap.arrow_bottom);
                            String start_score = mDatas.get(position - 1).get("AFM_26") + "";
                            String mokao_score = mDatas.get(position - 1).get("AFM_25") + "";
                            String end_score = mDatas.get(position - 1).get("AFM_28") + "";
                            String test_time = mDatas.get(position - 1).get("AFM_27") + "";
                            viewHolder1.tv_start_score.setText(!start_score.equals("null") ? (start_score + "分") : "0");
                            viewHolder1.tv_mokao_score.setText(!mokao_score.equals("null") ? (mokao_score + "分") : "0");
                            viewHolder1.tv_end_score.setText(!end_score.equals("null") ? (end_score + "分") : "0");
                            viewHolder1.tv_test_time.setText(!test_time.equals("null") ? test_time.substring(0, 10) : "暂无数据");
                        }
                    }
                });
                break;
            default:
                break;
        }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {

            return 1;
        }
    }

    public class ViewHolder {
        public final View mView;
        public final TextView start_time;
        public final TextView abroad_time;
        public Map<String, Object> mItem;
        public TextView stuName;
        public ImageView iv_image;


        public ViewHolder(View view) {
            mView = view;
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            stuName = (TextView) view.findViewById(R.id.tv_stu_name);
            start_time = (TextView) view.findViewById(R.id.start_time);
            abroad_time = (TextView) view.findViewById(R.id.abroad_time);
        }

    }

    public class ViewHolder1 {
        public final View mView;
        public final RelativeLayout layout_expansion;
        public final LinearLayout layout_expansion_detail;
        public final TextView tv_buy_hour_value;
        public final TextView tv_learn_hour_value;
        public final TextView tv_bushi_value;
        public final TextView tv_shengyu_value;
        public final TextView tv_course_hour;
        public final TextView tv_course_name;
        public final TextView tv_start_score;
        public final TextView tv_mokao_score;
        public final TextView tv_end_score;
        public final TextView tv_test_time;
        public final ImageView iv_on_off;
        public Map<String, Object> mItem;

        public ViewHolder1(View view) {
            mView = view;
            layout_expansion = (RelativeLayout) view.findViewById(R.id.layout_expansion);
            layout_expansion_detail = (LinearLayout) view.findViewById(R.id.layout_expansion_detail);
            tv_buy_hour_value = (TextView) view.findViewById(R.id.tv_buy_hour_value);
            tv_learn_hour_value = (TextView) view.findViewById(R.id.tv_learn_hour_value);
            tv_bushi_value = (TextView) view.findViewById(R.id.tv_bushi_value);
            tv_shengyu_value = (TextView) view.findViewById(R.id.tv_shengyu_value);
            tv_course_hour = (TextView) view.findViewById(R.id.tv_course_hour);
            tv_course_name = (TextView) view.findViewById(R.id.tv_course_name);
            tv_start_score = (TextView) view.findViewById(R.id.tv_start_score);
            tv_mokao_score = (TextView) view.findViewById(R.id.tv_mokao_score);
            tv_end_score = (TextView) view.findViewById(R.id.tv_end_score);
            tv_test_time = (TextView) view.findViewById(R.id.tv_test_time);
            iv_on_off = (ImageView) view.findViewById(R.id.iv_on_off);
        }
    }
}
