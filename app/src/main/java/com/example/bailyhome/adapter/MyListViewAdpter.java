package com.example.bailyhome.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bailyhome.ClassRecordActivity;
import com.example.bailyhome.CourseInfoActivity;
import com.example.bailyhome.CoursePingjiaActivity;
import com.example.bailyhome.CoursePingjiaActivity2;
import com.example.bailyhome.CouseSumReportActivity;
import com.example.bailyhome.FujianActivity;
import com.example.bailyhome.R;
import com.example.bailyhome.StagePlanActivity;
import com.example.bailyhome.StageSumReportActivity;
import com.example.bailyhome.bean.MyGridBean;
import com.example.bailyhome.bean.SerializableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/20 0020.
 */
public class MyListViewAdpter extends BaseAdapter{
    private Context context;
    private boolean isShow[]=new boolean[50];
    private List<MyGridBean> lists = new ArrayList<MyGridBean>();
    public int[] imgs = {R.mipmap.a_stage_information, R.mipmap.b_stage_record,
            R.mipmap.c_stage_evaluate, R.mipmap.d_stage_task};
    public String[] values = {"课程信息", "课堂记录", "课程评价", "附件"};
    MyGridAdapter myGridAdapter =null;
    List<Map<String,Object>> mDatas;
    Map<String,Object> map;
    String stageId;
    String satageName;

    public MyListViewAdpter(Context context,List<Map<String,Object>> mDatas,Map<String,Object> map) {
        this.context = context;
        this.mDatas=mDatas;
        this.map=map;
        for (int i=0;i<isShow.length;i++){
            isShow[i]=false;
        }

        MyGridBean myGridBean = null;
        for (int i = 0; i < 4; i++) {
            myGridBean = new MyGridBean(imgs[i], values[i]);
            lists.add(myGridBean);
        }
        myGridAdapter = new MyGridAdapter(context, lists);
    }

    public MyListViewAdpter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if (mDatas == null || mDatas.size() == 0) {
            return 1;
        } else {
            return mDatas.size()+1;
        }
    }

    public void setMyData(String stageId,String stageName){
        this.stageId=stageId;
        this.satageName=stageName;
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
        int type=getItemViewType(position);
        switch (type) {
            case 0:
                convertView = LayoutInflater.from(context).inflate(R.layout.course_package_detail_item1,null);
                ViewHolder viewHolder=new ViewHolder(convertView);
                if (map != null) {
                    String courseName=map.get("AFM_26") + "";
                    String teacher=map.get("AFM_40") + "";
                    String xiaoqu=map.get("AFM_36") + "";
                    viewHolder.tv_course_name.setText(!courseName.equals("null")?courseName:"");
                    viewHolder.tv_teacher.setText(!teacher.equals("null")?teacher:"");
                    viewHolder.tv_xiaoqu.setText(!xiaoqu.equals("null")?xiaoqu:"");
                    viewHolder.layout_stage_plan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(stageId)&&!stageId.equals("null")) {
                                Intent intent = new Intent(context, StagePlanActivity.class);
                                intent.putExtra("stageId", !TextUtils.isEmpty(stageId) ? stageId : "");
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context,"无阶段计划",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    viewHolder.tv_course_name.setText("无数据");
                    viewHolder.tv_teacher.setText("无数据");
                    viewHolder.tv_xiaoqu.setText("无数据");
                }

                viewHolder.layout_stage_final_report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, CouseSumReportActivity.class);
                        if (map!=null&&!TextUtils.isEmpty(satageName)&&!satageName.equals("null")) {
                            intent.putExtra("courseName",map.get("AFM_26")+"");
                            intent.putExtra("stuName",map.get("AFM_23")+"");
                            intent.putExtra("stageName",satageName);
                        }
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                convertView = LayoutInflater.from(context).inflate(R.layout.course_package_detail_item2,null);
                final ViewHolder1 viewHolder1=new ViewHolder1(convertView);
                String hour=(String) mDatas.get(position-1).get("AFM_58");
//                String date=(String)mDatas.get(position-1).get("AFM_8");
                viewHolder1.tv_hour.setText(!TextUtils.isEmpty(hour)?hour:"");
                String order_date=(String)mDatas.get(position-1).get("AFM_3");
                String class_date=(String)mDatas.get(position-1).get("AFM_55");
                String startTime="";
                if (!TextUtils.isEmpty(class_date)) {
                    startTime=class_date.substring(0,5);
                }
                viewHolder1.tv_date.setText(!TextUtils.isEmpty(order_date)?order_date.substring(0,11)+startTime:"");

                if (isShow[position]){
                    viewHolder1.gv.setVisibility(View.VISIBLE);
                    viewHolder1.gv.setAdapter(myGridAdapter);
                    viewHolder1.tv_no.setBackgroundResource(R.mipmap.arrow_bottom);
                    final Map<String,Object> objectMap=mDatas.get(position-1);
                    viewHolder1.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            switch (position) {
                                case 0:
                                    Intent intent=new Intent(context, CourseInfoActivity.class);
                                    SerializableMap serializableMap=new SerializableMap();
                                    serializableMap.setMap(objectMap);
                                    intent.putExtra("SerializableMap",serializableMap);
                                    context.startActivity(intent);
                                    break;
                                case 1:
                                    Intent intent1=new Intent(context, ClassRecordActivity.class);
                                    SerializableMap serializableMap1=new SerializableMap();
                                    serializableMap1.setMap(objectMap);
                                    intent1.putExtra("SerializableMap",serializableMap1);
                                    context.startActivity(intent1);
                                    break;
                                case 2:
                                    Intent intent2=new Intent(context, CoursePingjiaActivity2.class);
                                    SerializableMap serializableMap2=new SerializableMap();
                                    serializableMap2.setMap(objectMap);
                                    intent2.putExtra("SerializableMap",serializableMap2);
                                    context.startActivity(intent2);
                                    break;
                                case 3:
                                    Intent intent3=new Intent(context, FujianActivity.class);
                                    SerializableMap serializableMap3=new SerializableMap();
                                    serializableMap3.setMap(objectMap);
                                    intent3.putExtra("SerializableMap",serializableMap3);
                                    context.startActivity(intent3);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                }else {
                    viewHolder1.gv.setVisibility(View.GONE);
                    viewHolder1.tv_no.setBackgroundResource(R.mipmap.arrow_right);
                }
                viewHolder1.layout_expansion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow[position]=!isShow[position];
                        if (viewHolder1.gv.getVisibility()==View.VISIBLE) {
                            viewHolder1.gv.setVisibility(View.GONE);
                            viewHolder1.tv_no.setBackgroundResource(R.mipmap.arrow_right);
                        }else {
                            viewHolder1.gv.setVisibility(View.VISIBLE);
                            viewHolder1.gv.setAdapter(myGridAdapter);
                            viewHolder1.tv_no.setBackgroundResource(R.mipmap.arrow_bottom);
                            final Map<String,Object> objectMap=mDatas.get(position-1);
                            viewHolder1.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent=null;
                                    switch (position) {
                                        case 0:
                                            intent=new Intent(context, CourseInfoActivity.class);
                                            SerializableMap serializableMap=new SerializableMap();
                                            serializableMap.setMap(objectMap);
                                            intent.putExtra("SerializableMap",serializableMap);
                                            context.startActivity(intent);
                                            break;
                                        case 1:
                                            intent=new Intent(context, ClassRecordActivity.class);
                                            SerializableMap serializableMap1=new SerializableMap();
                                            serializableMap1.setMap(objectMap);
                                            intent.putExtra("SerializableMap",serializableMap1);
                                            context.startActivity(intent);
                                            break;
                                        case 2:
                                            intent=new Intent(context, CoursePingjiaActivity2.class);
                                            SerializableMap serializableMap2=new SerializableMap();
                                            serializableMap2.setMap(objectMap);
                                            intent.putExtra("SerializableMap",serializableMap2);
                                            context.startActivity(intent);
                                            break;
                                        case 3:
                                            intent=new Intent(context, FujianActivity.class);
                                            SerializableMap serializableMap3=new SerializableMap();
                                            serializableMap3.setMap(objectMap);
                                            intent.putExtra("SerializableMap",serializableMap3);
                                            context.startActivity(intent);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            });
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
        if (position==0) {
            return 0;
        }else {

            return 1;
        }
    }

    public class ViewHolder {
        public final View mView;
        public final ImageView iv_head;
        public final TextView tv_course_name;
        public final TextView tv_teacher;
        public final TextView tv_xiaoqu;
        public final RelativeLayout layout_stage_plan;
        public final RelativeLayout layout_stage_final_report;
        public Map<String,Object> mItem;

        public ViewHolder(View view) {
            mView = view;
            iv_head = (ImageView) view.findViewById(R.id.iv_head);
            tv_course_name = (TextView) view.findViewById(R.id.tv_course_name);
            tv_teacher = (TextView) view.findViewById(R.id.tv_teacher);
            tv_xiaoqu = (TextView) view.findViewById(R.id.tv_xiaoqu);
            layout_stage_plan = (RelativeLayout) view.findViewById(R.id.layout_stage_plan);
            layout_stage_final_report = (RelativeLayout) view.findViewById(R.id.layout_stage_final_report);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_course_name.getText() + "'";
        }
    }

    public class ViewHolder1 {
        public final View mView;
        public final RelativeLayout layout_expansion;
        public final GridView gv;
        public final ImageView tv_no;
        public final TextView tv_hour;
        public final TextView tv_date;
        public Map<String,Object> mItem;

        public ViewHolder1(View view) {
            mView = view;
            layout_expansion = (RelativeLayout) view.findViewById(R.id.layout_expansion);
            gv = (GridView) view.findViewById(R.id.gv);
            tv_no = (ImageView) view.findViewById(R.id.tv_no);
            tv_hour = (TextView) view.findViewById(R.id.tv_hour);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
        }
    }
}
