package com.example.bailyhome.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.bailyhome.R;
import com.example.bailyhome.ReVisitRecActivity;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class SelPopWindow extends PopupWindow {
    private View conentView;
    public String reVstate = "1133", reVres = "1355";//回访阶段、回访结果
    private Activity context;
    public String str1 = "", str2 = "";
    public CheckBox rb1, rb2, rb3, rb4, rb5, rb6;
    public CheckBox satisfy, unsatisfy;

    public SelPopWindow(final Activity context, String param1, String param2, final String val1, String val2) {
        this.context = context;
        this.str2 = param2;
        this.str1 = param1;
        this.reVstate = val1;
        this.reVres = val2;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.activity_revisit_record_select, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(w / 2 + 50);
        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        rb1 = (CheckBox) conentView.findViewById(R.id.rb1);
        rb2 = (CheckBox) conentView.findViewById(R.id.rb2);
        rb3 = (CheckBox) conentView.findViewById(R.id.rb3);
        rb4 = (CheckBox) conentView.findViewById(R.id.rb4);
        rb5 = (CheckBox) conentView.findViewById(R.id.rb5);
        rb6 = (CheckBox) conentView.findViewById(R.id.rb6);
        satisfy = (CheckBox) conentView.findViewById(R.id.satisfy);
        unsatisfy = (CheckBox) conentView.findViewById(R.id.unsatisfy);
        setRadioBtn(param1, param2);
        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    str1 = 0 + "";
                    reVstate = "1133";
                    rb2.setChecked(!b);
                    rb3.setChecked(!b);
                    rb4.setChecked(!b);
                    rb5.setChecked(!b);
                    rb6.setChecked(!b);
                }
            }
        });

        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    str1 = 1 + "";
                    reVstate = "1134";
                    rb1.setChecked(!b);
                    rb3.setChecked(!b);
                    rb4.setChecked(!b);
                    rb5.setChecked(!b);
                    rb6.setChecked(!b);
                }
            }
        });
        rb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    str1 = 2 + "";
                    reVstate = "1135";
                    rb1.setChecked(!b);
                    rb2.setChecked(!b);
                    rb4.setChecked(!b);
                    rb5.setChecked(!b);
                    rb6.setChecked(!b);
                }
            }
        });
        rb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    str1 = 3 + "";
                    reVstate = "1136";
                    rb1.setChecked(!b);
                    rb2.setChecked(!b);
                    rb3.setChecked(!b);
                    rb5.setChecked(!b);
                    rb6.setChecked(!b);
                }
            }
        });
        rb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    str1 = 4 + "";
                    reVstate = "1549";
                    rb1.setChecked(!b);
                    rb2.setChecked(!b);
                    rb3.setChecked(!b);
                    rb4.setChecked(!b);
                    rb6.setChecked(!b);
                }
            }
        });
        rb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    str1 = 5 + "";
                    reVstate = "1137";
                    rb1.setChecked(!b);
                    rb2.setChecked(!b);
                    rb3.setChecked(!b);
                    rb4.setChecked(!b);
                    rb5.setChecked(!b);
                }
            }
        });
        LinearLayout rgSecond = (LinearLayout) conentView.findViewById(R.id.rg2);
        final LinearLayout btnSubmit = (LinearLayout) conentView.findViewById(R.id.btn_submit);

        satisfy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    str2 = 6 + "";
                    reVres = "1355";
                    unsatisfy.setChecked(!b);
                }
            }
        });

        unsatisfy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    str2 = 7 + "";
                    reVres = "1356";
                    satisfy.setChecked(!b);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!rb1.isChecked()) && (!rb2.isChecked()) && (!rb3.isChecked()) && (!rb4.isChecked()) && (!rb5.isChecked()) && (!rb6.isChecked())) {
                    reVstate="";
                    str1="";
                }
                if ((!satisfy.isChecked())&&(!unsatisfy.isChecked())){
                    str2 = "";
                    reVres = "";
                }
                ((ReVisitRecActivity) context).getUrlData(reVstate, reVres, "2");
                ((ReVisitRecActivity) context).setTvStr12(str1, str2, reVstate, reVres);
                SelPopWindow.this.dismiss();
            }
        });
        //添加pop窗口关闭事件
       setOnDismissListener(new poponDismissListener());
    }

    public void setRadioBtn(String str1, String str2) {
        switch (str1) {
            case "0":
                rb1.setChecked(true);
                break;
            case "1":
                rb2.setChecked(true);
                break;
            case "2":
                rb3.setChecked(true);
                break;
            case "3":
                rb4.setChecked(true);
                break;
            case "4":
                rb5.setChecked(true);
                break;
            case "5":
                rb6.setChecked(true);
                break;
            default:
//                rb1.setChecked(false);
//                rb2.setChecked(false);
//                rb3.setChecked(false);
//                rb4.setChecked(false);
//                rb5.setChecked(false);
//                rb6.setChecked(false);
                break;
        }
        switch (str2) {
            case "6":
                satisfy.setChecked(true);
                break;
            case "7":
                unsatisfy.setChecked(true);
                break;
            default:
//                satisfy.setChecked(false);
//                unsatisfy.setChecked(false);
                break;
        }
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            backgroundAlpha(context,0.5f);
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            //backgroundAlpha(context,1f);
            this.dismiss();
        }
    }

    public interface OnSendStrListener {
        public String sendSelData();
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity activity,float bgAlpha)
    {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     *
     */
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(context,1f);
        }

    }
}
