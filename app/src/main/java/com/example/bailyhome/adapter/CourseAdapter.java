package com.example.bailyhome.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bailyhome.R;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CourseAdapter extends BaseAdapter {
	private List<Map<String, Object>> mList;
	private Context mContext;
	private int[] colors={R.drawable.couse_bg,R.drawable.course_background_mokao};
	private Random random=new Random();
	//当前周的第一天和最后一天
	long start,end;
	int mListSize=0;

	public CourseAdapter(Context mContext, List<Map<String, Object>> mList,int mListSize) {
		this.mContext = mContext;
		this.mListSize=mListSize;
		this.mList = mList;
	}

	//设置当前周的第一天和最后一天
	public void startAndEndDate(String start,String end){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.start=sdf.parse(start).getTime();
			this.end=sdf.parse(end).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 70;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.course_item, null);
		int itemHeight=dip2px(mContext, 80);
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,itemHeight);
	    convertView.setLayoutParams(param);
		TextView tv_course_name = (TextView) convertView
				.findViewById(R.id.tv_course_name);
		TextView tv_time = (TextView) convertView
				.findViewById(R.id.tv_time);
		//如果当前positon有数据，返回list集合的对应数据下标不为-1
		int dataIndex=isHashData(position);
		Log.i("123","dataIndex==>"+dataIndex+"==position==>"+position);
		if (dataIndex!=-1) {
			if (dataIndex < mListSize) {
				tv_time.setText((String) mList.get(dataIndex).get("DIC_AFM_11"));
				tv_course_name.setText((String) mList.get(dataIndex).get("AFM_46"));
			} else {
				tv_time.setText((String)mList.get(dataIndex).get("AFM_18"));
				tv_course_name.setText((String)mList.get(dataIndex).get("AFM_14"));
			}
			int k=random.nextInt();
			int j= Math.abs(k%2);
			convertView.setBackgroundResource(colors[j]);
		} else {
			tv_course_name.setText("  ");
		}
		
		convertView.setTag(dataIndex);
		return convertView;
	}

	// 判断是否是第一列
	private boolean isFistColumn(int position) {
		if (position % 8 == 0) {
			return true;
		} else {
			return false;
		}
	}

	// 判断是否有数据
	private int isHashData(int position) {
		for (int i = 0; i < mList.size(); i++) {
			String date="";
			String time="";
			if (i<mListSize){
				date=(String)mList.get(i).get("AFM_3");
				time=(String)mList.get(i).get("AFM_48");
			}else {
				date=(String)mList.get(i).get("AFM_5");
				time=(String)mList.get(i).get("AFM_18");
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			long dateLong=0;
			try {
				dateLong = sdf.parse(date).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int week=week(date);
			//gridview下标是从0开始的，对应周减1
			week--;
			if (TextUtils.isEmpty(time)) {
				return -1;
			}
			String str[]=time.split("-");
			long eight=3600*8L;
			String[] splitLeft = str[0].split(":");
			String stringHour = splitLeft[0];
			String stringMin = splitLeft[1];
			long left=Integer.parseInt(stringHour)*3600;
			left+=Integer.parseInt(stringMin)*60;

//			String[] splitRight = str[1].split(":");
//			String stringHour1 = splitRight[0];
//			String stringMin1 = splitRight[1];
//			long right=Integer.parseInt(stringHour1)*3600;
//			right+=Integer.parseInt(stringMin1)*60;
			long temp=3600L;
			if (date.equals("2016-05-5 00:00:00")){
				int a=week+7*2;
				Log.i("123","myData====eight==>"+eight+"==left==>"+left+
						"==dateLong==>"+dateLong+"==start==>"+start+"==end==>"+end+"==week==>"+a+
				"==position==>"+position);
			}
			try {
				if(left>=eight&&left<eight+temp&&dateLong>=start&&dateLong<=end&& (week+7*0)==position){
					return i;
				}else if(left>=(eight+temp)&&left<(eight+temp*2)&&
						dateLong>=start&&dateLong<=end&& (week+7*1)==position){
					return i;
				}else if(left>=(eight+temp*2)&&left<(eight+temp*3)&&
						dateLong>=start&&dateLong<=end&& (week+7*2)==position){
					return i;
				}else if(left>=(eight+temp*3)&&left<(eight+temp*4)&&
						dateLong>=start&&dateLong<=end&& (week+7*3)==position){
					return i;
				}else if(left>=(eight+temp*4)&&left<(eight+temp*5)&&
						dateLong>=start&&dateLong<=end&& (week+7*4)==position){
					return i;
				}else if(left>=(eight+temp*5)&&left<(eight+temp*6)&&
						dateLong>=start&&dateLong<=end&& (week+7*5)==position){
					return i;
				}else if(left>=(eight+temp*6)&&left<(eight+temp*7)&&
						dateLong>=start&&dateLong<=end&& (week+7*6)==position){
					return i;
				}else if(left>=(eight+temp*7)&&left<(eight+temp*8)&&
						dateLong>=start&&dateLong<=end&& (week+7*7)==position){
					return i;
				}else if(left>=(eight+temp*8)&&left<(eight+temp*9)&&
						dateLong>=start&&dateLong<=end&& (week+7*8)==position){
					return i;
				}else if(left>=(eight+temp*9)&&left<(eight+temp*10)&&
						dateLong>=start&&dateLong<=end&& (week+7*9)==position){
					return i;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		return -1;
	}
	
	//获得星期几
	private int week(String date){
		if (date==null) {
			return 8;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int week=calendar.get(Calendar.DAY_OF_WEEK);
		if (week==1){
			return 7;
		}else if (week==2){
			return 1;
		}else if (week==3){
			return 2;
		}else if (week==4){
			return 3;
		}else if (week==5){
			return 4;
		}else if (week==6){
			return 5;
		}else if (week==7){
			return 6;
		}else {
			return 8;
		}
	}
	
	/** 
	* 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
	*/  
	public int dip2px(Context context, float dpValue) {
	  final float scale = context.getResources().getDisplayMetrics().density;  
	  return (int) (dpValue * scale + 0.5f);  
	} 
	
	public class ViewHolder {
		public TextView mTextName;
	}
}