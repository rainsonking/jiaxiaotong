package com.example.bailyhome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.bailyhome.base.BaseActivity;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/5/18 0018.
 * <p/>
 * 回访记录详情
 */
public class ReVisitRecInfoActivity extends BaseActivity {
    private TextView textHeader;
    private ImageView menuImg, menuImgRight,iv_ifsatisfy;
    private TextView tvClassName, tvRevisitPerson, tvRevisitDate, tvRevisitTeacher, tvRevisitResultSatify,tvRevisitResultUnsatify, tvRevisitRecord, tvSolution, tvFollowUp;
    private TextView tvSolutionView, tvFollowUpView;
//    private ScrollView scrollView3,scrollView2;
    private LinearLayout layout_gone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisit_record_info);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("className");
        textHeader.setText("回访详情");
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvClassName.setText(bundle.getString("className"));
        tvRevisitPerson.setText(bundle.getString("reVisitPsn"));
        tvRevisitDate.setText(bundle.getString("reVisitDate"));
        tvRevisitTeacher.setText(bundle.getString("reVisitTech"));
      //  tvRevisitResultSatify.setText(bundle.getString("reVisitRes"));
        String reVisitRec=bundle.getString("reVisitRec");
        tvRevisitRecord.setText(!TextUtils.isEmpty(reVisitRec)?reVisitRec:"无内容");
        if ("不满意".equals(bundle.getString("reVisitRes"))) {
            tvRevisitResultUnsatify.setVisibility(View.VISIBLE);
            tvRevisitResultSatify.setVisibility(View.GONE);
            tvRevisitResultUnsatify.setText(bundle.getString("reVisitRes"));
            tvSolution.setText(bundle.getString("resolvMethod"));
            tvFollowUp.setText(bundle.getString("followReVisit"));
            iv_ifsatisfy.setImageResource(R.mipmap.parent_state);
        } else {
           // tvRevisitResult.setTextColor(getResources().getColor(R.color.score_25));
            tvRevisitResultUnsatify.setVisibility(View.GONE);
            tvRevisitResultSatify.setVisibility(View.VISIBLE);
            tvRevisitResultSatify.setText(bundle.getString("reVisitRes"));
            iv_ifsatisfy.setImageResource(R.mipmap.parent_dissatisfied);
            layout_gone.setVisibility(View.GONE);
//            tvSolution.setVisibility(View.GONE);
//            tvFollowUp.setVisibility(View.GONE);
//            tvSolutionView.setVisibility(View.GONE);
//            tvFollowUpView.setVisibility(View.GONE);
//            scrollView2.setVisibility(View.GONE);
//            scrollView3.setVisibility(View.GONE);
        }

    }

    @Override
    public void initView() {
        textHeader = (TextView) findViewById(R.id.text_header);
        menuImg = (ImageView) findViewById(R.id.menu_img);
        menuImgRight = (ImageView) findViewById(R.id.menu_img_right);
        tvClassName = (TextView) findViewById(R.id.tv_className);
        tvRevisitPerson = (TextView) findViewById(R.id.tv_revisit_person);
        tvRevisitDate = (TextView) findViewById(R.id.tv_revisit_date);
        tvRevisitTeacher = (TextView) findViewById(R.id.tv_revisit_teacher);
        tvRevisitResultSatify = (TextView) findViewById(R.id.tv_revisit_result_satify);
        tvRevisitResultUnsatify = (TextView) findViewById(R.id.tv_revisit_result_unsatify);
        tvRevisitRecord = (TextView) findViewById(R.id.tv_revisit_record);
        tvSolution = (TextView) findViewById(R.id.tv_solution);
        tvFollowUp = (TextView) findViewById(R.id.tv_follow_up);
        tvSolutionView = (TextView) findViewById(R.id.tv_solution_view);
        tvFollowUpView = (TextView) findViewById(R.id.tv_follow_up_view);
        layout_gone = (LinearLayout) findViewById(R.id.layout_gone);
        iv_ifsatisfy = (ImageView) findViewById(R.id.iv_ifsatisfy);
//        scrollView2= (ScrollView) findViewById(R.id.scrollView01);
//        scrollView3= (ScrollView) findViewById(R.id.scrollView02);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
