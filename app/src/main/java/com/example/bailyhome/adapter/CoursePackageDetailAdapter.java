//package com.example.bailyhome.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.example.bailyhome.CourseInfoActivity;
//import com.example.bailyhome.R;
//import com.example.bailyhome.StagePlanActivity;
//import com.example.bailyhome.StageSumReportActivity;
//
//import java.util.List;
//import java.util.Map;
//
//
//public class CoursePackageDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private List<Map<String,Object>> mValues;
//    private View.OnClickListener onClickListener;
//    private Context context;
//    private boolean isShow[]=new boolean[50];
//
//    public CoursePackageDetailAdapter(List<Map<String,Object>> items, View.OnClickListener onClickListener) {
//        mValues = items;
//        this.onClickListener=onClickListener;
//    }
//
//    public CoursePackageDetailAdapter(Context context,View.OnClickListener onClickListener) {
//        this.context=context;
//        this.onClickListener=onClickListener;
//        for (int i=0;i<isShow.length;i++){
//            isShow[i]=false;
//        }
//    }
//
//    public void setData(List<Map<String,Object>> datas) {
//        mValues.clear();
//        mValues.addAll(datas);
//    }
//
//    public void addDatas(List<Map<String,Object>> datas) {
//        mValues.addAll(datas);
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view;
//        if (viewType==0) {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.course_package_detail_item1, parent, false);
//            return new ViewHolder(view);
//        }else {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.course_package_detail_item2, parent, false);
//            return new ViewHolder1(view);
//        }
//
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        int viewType=getItemViewType(position);
//        Log.i("123","onBindView===>"+position);
//        if (viewType==0) {
//            ViewHolder mHolder = (ViewHolder) holder;
////            mHolder.mItem = mValues.get(position);
//            mHolder.layout_stage_plan.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(context, StagePlanActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//
//            mHolder.layout_stage_final_report.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(context, StageSumReportActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//            View view=mHolder.mView;
//            view.setTag(position);
////            view.setOnClickListener(onClickListener);
//        }else {
//            ViewHolder1 mHolder = (ViewHolder1) holder;
////            mHolder.mItem = mValues.get(position);
//            final LinearLayout layout_expansion_info=mHolder.layout_expansion_info;
//            if (isShow[position]){
//                layout_expansion_info.setVisibility(View.VISIBLE);
//            }else {
//                layout_expansion_info.setVisibility(View.GONE);
//            }
//            mHolder.layout_expansion.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    isShow[position]=!isShow[position];
//                    if (layout_expansion_info.getVisibility()==View.VISIBLE) {
//                        layout_expansion_info.setVisibility(View.GONE);
//                    }else {
//                        layout_expansion_info.setVisibility(View.VISIBLE);
//                    }
//                }
//            });
//            if (layout_expansion_info.getVisibility()==View.VISIBLE) {
//                mHolder.layout_course_info.setFocusable(true);
//                mHolder.layout_course_record.setFocusable(true);
//                mHolder.layout_course_pingjia.setFocusable(true);
//                mHolder.layout_fujian.setFocusable(true);
//                mHolder.layout_course_info.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.i("123","OnClick===>");
//                        Intent intent=new Intent(context, CourseInfoActivity.class);
//                        context.startActivity(intent);
//                    }
//                });
//
//                mHolder.layout_course_record.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//
//                mHolder.layout_course_pingjia.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//
//                mHolder.layout_fujian.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//            }
//            View view=mHolder.mView;
//            view.setTag(position);
////            view.setOnClickListener(onClickListener);
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position==0) {
//            return 0;
//        }else {
//
//            return 1;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 50;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final ImageView iv_head;
//        public final TextView tv_course_name;
//        public final TextView tv_teacher;
//        public final TextView tv_xiaoqu;
//        public final RelativeLayout layout_stage_plan;
//        public final RelativeLayout layout_stage_final_report;
//        public Map<String,Object> mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            iv_head = (ImageView) view.findViewById(R.id.iv_head);
//            tv_course_name = (TextView) view.findViewById(R.id.tv_course_name);
//            tv_teacher = (TextView) view.findViewById(R.id.tv_teacher);
//            tv_xiaoqu = (TextView) view.findViewById(R.id.tv_xiaoqu);
//            layout_stage_plan = (RelativeLayout) view.findViewById(R.id.layout_stage_plan);
//            layout_stage_final_report = (RelativeLayout) view.findViewById(R.id.layout_stage_final_report);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + tv_course_name.getText() + "'";
//        }
//    }
//
//    public class ViewHolder1 extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final RelativeLayout layout_expansion;
//        public final LinearLayout layout_expansion_info;
//        public final LinearLayout layout_course_info;
//        public final LinearLayout layout_course_record;
//        public final LinearLayout layout_course_pingjia;
//        public final LinearLayout layout_fujian;
//        public Map<String,Object> mItem;
//
//        public ViewHolder1(View view) {
//            super(view);
//            mView = view;
//            layout_expansion = (RelativeLayout) view.findViewById(R.id.layout_expansion);
//            layout_expansion_info = (LinearLayout) view.findViewById(R.id.layout_expansion_info);
//            layout_course_info = (LinearLayout) view.findViewById(R.id.layout_course_info);
//            layout_course_record = (LinearLayout) view.findViewById(R.id.layout_course_record);
//            layout_course_pingjia = (LinearLayout) view.findViewById(R.id.layout_course_pingjia);
//            layout_fujian = (LinearLayout) view.findViewById(R.id.layout_fujian);
//        }
//
//    }
//}
