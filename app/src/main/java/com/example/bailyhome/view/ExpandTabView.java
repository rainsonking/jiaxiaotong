package com.example.bailyhome.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.bailyhome.R;
import com.example.bailyhome.base.ViewBaseAction;

import java.util.ArrayList;

/**
 * 菜单控件头部，封装了下拉动画，动态生成头部按钮个数
 *
 * @author yueyueniao
 */

public class ExpandTabView extends LinearLayout implements OnDismissListener {

    private ToggleButton selectedButton;
    private ArrayList<String> mTextArray = new ArrayList<String>();
    private ArrayList<RelativeLayout> mViewArray = new ArrayList<RelativeLayout>();
    private ArrayList<ToggleButton> mToggleButton = new ArrayList<ToggleButton>();
    private Context mContext;
    private final int SMALL = 0;
    private int displayWidth;
    private int displayHeight;
    private PopupWindow popupWindow;
    private int selectPosition;
    ImageView iv_image;

    public ExpandTabView(Context context) {
        super(context);
        init(context);
    }

    public ExpandTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 根据选择的位置设置tabitem显示的值
     */
    public void setTitle(String valueText, int position) {
        if (position < mToggleButton.size()) {
            mToggleButton.get(position).setText(valueText);
        }
    }

    public void setTitle(String title) {

    }

    /**
     * 根据选择的位置获取tabitem显示的值
     */
    public String getTitle(int position) {
        if (position < mToggleButton.size() && mToggleButton.get(position).getText() != null) {
            return mToggleButton.get(position).getText().toString();
        }
        return "";
    }

    /**
     * 设置tabitem的个数和初始值
     */
    public void setValue(ArrayList<String> textArray, ArrayList<View> viewArray) {
        if (mContext == null) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mTextArray = textArray;
        for (int i = 0; i < viewArray.size(); i++) {
            final RelativeLayout r = new RelativeLayout(mContext);
//			int maxHeight = (int) (displayHeight * 0.7);
            int maxHeight = ActionBar.LayoutParams.WRAP_CONTENT;
            RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, maxHeight);
            WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            int margin = (display.getWidth()) / 5;
            rl.leftMargin = margin;
            rl.rightMargin = margin;

            r.addView(viewArray.get(i), rl);
            mViewArray.add(r);
            r.setTag(SMALL);

            View view = inflater.inflate(R.layout.sch_toggle_button, null);
            // LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            ToggleButton tButton = (ToggleButton) view.findViewById(R.id.toggleBtn);

            addView(view);
            View line = new TextView(mContext);
            line.setBackgroundColor(Color.WHITE);
            //  line.setBackgroundResource(R.mipmap.choosebar_line);
            if (i < viewArray.size() - 1) {
                LayoutParams lp = new LayoutParams(2, LayoutParams.FILL_PARENT);
                addView(line, lp);
            }
            mToggleButton.add(tButton);
            tButton.setTag(i);
            tButton.setText(mTextArray.get(i));

            r.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPressBack();
                }
            });


            tButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    // initPopupWindow();
                    ToggleButton tButton = (ToggleButton) view;

                    if (selectedButton != null && selectedButton != tButton) {
                        selectedButton.setChecked(false);
                    }
                    selectedButton = tButton;
                    selectPosition = (Integer) selectedButton.getTag();
                    startAnimation();
                    if (mOnButtonClickListener != null && tButton.isChecked()) {
                        mOnButtonClickListener.onClick(selectPosition);
                    }
                }
            });
            //加入下部阴影
           // r.setBackgroundColor(mContext.getResources().getColor(R.color.popup_main_background));
        }
    }

    private void startAnimation() {

        if (popupWindow == null) {
            popupWindow = new PopupWindow(mViewArray.get(selectPosition), displayWidth, displayHeight);
            popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(true);
        }

        if (selectedButton.isChecked()) {
            if (!popupWindow.isShowing()) {
                showPopup(selectPosition);
                iv_image.setBackgroundResource(R.mipmap.choosebar_press_up);

            } else {
                iv_image.setBackgroundResource(R.mipmap.choosebar_press_down);
                popupWindow.setOnDismissListener(this);
                popupWindow.dismiss();
                hideView();
            }
        } else {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                hideView();
            }
        }
    }

    private void showPopup(int position) {
        View tView = mViewArray.get(selectPosition).getChildAt(0);
        if (tView instanceof ViewBaseAction) {
            ViewBaseAction f = (ViewBaseAction) tView;
            f.show();
        }
        if (popupWindow.getContentView() != mViewArray.get(position)) {
            popupWindow.setContentView(mViewArray.get(position));
        }
        popupWindow.showAsDropDown(this, 0, 0);
    }

    /**
     * 如果菜单成展开状态，则让菜单收回去
     */
    public boolean onPressBack() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            hideView();
            if (selectedButton != null) {
                selectedButton.setChecked(false);
            }
            return true;
        } else {
            return false;
        }

    }

    private void hideView() {
        View tView = mViewArray.get(selectPosition).getChildAt(0);
        iv_image.setBackgroundResource(R.mipmap.choosebar_press_down);

        if (tView instanceof ViewBaseAction) {
            ViewBaseAction f = (ViewBaseAction) tView;
            f.hide();
        }
    }

    private void init(Context context) {
        mContext = context;
        WindowManager a = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = a.getDefaultDisplay(); // 获取屏幕宽、高用
        Point size = new Point();
        d.getSize(size);
        displayWidth = size.x;
        displayHeight = size.y;
//		displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
//		displayHeight = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public void onDismiss() {
        showPopup(selectPosition);
        popupWindow.setOnDismissListener(null);
    }

    private OnButtonClickListener mOnButtonClickListener;

    /**
     * 设置tabitem的点击监听事件
     */
    public void setOnButtonClickListener(OnButtonClickListener l) {
        mOnButtonClickListener = l;
    }

    /**
     * 自定义tabitem点击回调接口
     */
    public interface OnButtonClickListener {
        public void onClick(int selectPosition);
    }

}