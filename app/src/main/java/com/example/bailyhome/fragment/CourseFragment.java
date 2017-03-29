package com.example.bailyhome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bailyhome.R;
import com.example.bailyhome.adapter.FragmentTabAdapter;
import com.example.bailyhome.model.OnDataListener;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.List;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.WeekDateChaListener;
import noman.weekcalendar.listener.OnDateClickListener;

/**
 * Created by Administrator on 2016/5/12 0012.
 *
 * 课程包、课程表界面
 */
public class CourseFragment extends Fragment implements OnDataListener{
    private  View view;
    private Fragment courseTableFm;
    private Fragment coursePackageFm;
    private List<Fragment> mFragments;// 每个部分的fragment实体
    FragmentTabAdapter tabAdapter;
    public RadioGroup radioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course,null);
        init(view);
        return view;

    }

    private void init(View view){
        radioGroup=(RadioGroup) getActivity().findViewById(R.id.book_radioGroup);
        ((RadioButton) radioGroup.findViewById(R.id.rb_un_get)).setChecked(true);// 设置radiogroup的机制
        courseTableFm=new CourseTableFm();
        coursePackageFm=new CoursePackageFm();
        mFragments=new ArrayList<>();
        mFragments.add(courseTableFm);
        mFragments.add(coursePackageFm);
        tabAdapter = new FragmentTabAdapter(getActivity(), mFragments, R.id.book_content, radioGroup);
    }

    @Override
    public void onGetDataSuccess(String jsonData) {

    }

    @Override
    public void onGetDataError() {

    }

    @Override
    public void onLoading(long total, long current) {

    }

}
