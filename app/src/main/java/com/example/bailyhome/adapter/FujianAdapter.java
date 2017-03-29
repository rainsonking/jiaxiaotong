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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bailyhome.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FujianAdapter extends BaseAdapter {
	private List<Map<String, Object>> mList;
	private Context mContext;


	public FujianAdapter(Context mContext, List<Map<String, Object>> mList) {
		this.mContext = mContext;
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.fujian_item, null);

		TextView textView=(TextView)convertView.findViewById(R.id.tv_fujian_name);
		TextView textView1=(TextView)convertView.findViewById(R.id.tv_fujian_date);
		ImageView iv_img=(ImageView) convertView.findViewById(R.id.iv_img);
		View line=convertView.findViewById(R.id.line);
		if (mList.size()>0&&position==mList.size()-1) {
			line.setVisibility(View.GONE);
		}
		String fujianName=(String) mList.get(position).get("name");
		if (!TextUtils.isEmpty(fujianName)) {
			if (fujianName.contains(".jpg")) {
				iv_img.setImageResource(R.mipmap.img_fujian_jpg);
			} else if (fujianName.contains(".doc")) {
				iv_img.setImageResource(R.mipmap.img_fujian_w);
			} else {
				iv_img.setImageResource(R.mipmap.img_fujian_x);
			}
		}
		textView.setText(!TextUtils.isEmpty(fujianName)?fujianName:"");
		String fujianDate=(String) mList.get(position).get("date");
		textView1.setText(!TextUtils.isEmpty(fujianDate)?fujianDate.substring(0,10):"");
		return convertView;
	}


}