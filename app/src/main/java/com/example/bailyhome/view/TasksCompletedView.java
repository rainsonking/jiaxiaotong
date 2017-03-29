package com.example.bailyhome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.bailyhome.R;

/**
 * Created by Administrator on 2016/6/6 0006.
 */
public class TasksCompletedView extends View {

    private final Context context;

    private AttributeSet attrs;
    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    public int mProgress = 50;

    //外层圆环
    Paint roundPaint, paint1, paint2, paint3, paint4, paint5;
    public String leftScore = "80", rigScore = "20", bottomScore = "52";


    public TasksCompletedView(Context context, String leftScore, String rigScore, String bottomScore) {
        super(context);
        this.context = context;
        this.leftScore = leftScore;
        this.rigScore = rigScore;
        this.bottomScore = bottomScore;
    }


//    public TasksCompletedView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.context = context;
//        this.attrs = attrs;
//        // 获取自定义的属性
//        //initAttrs(context, attrs);
//
//    }

//    private void initAttrs(Context context, AttributeSet attrs) {
//        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
//                R.styleable.TasksCompletedView, 0, 0);
//        mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
//        mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
//        mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
//        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);
//        mStrokeWidth = mStrokeWidth / 2;
//        mRingRadius = mRadius + mStrokeWidth / 2;
//    }

    private void initVariable() {
        mStrokeWidth = 22;

        //画百分之百的透明色圆环
        roundPaint = new Paint();
        roundPaint.setAntiAlias(true);
        // roundPaint.setColor(Color.WHITE); //设置圆环的颜色
        roundPaint.setAlpha(80);
        roundPaint.setStyle(Paint.Style.STROKE); //设置空心
        roundPaint.setStrokeWidth(mStrokeWidth); //设置圆环的宽度

        //画出内部实心圆
        // canvas.drawCircle(centre, centre, radius, paint); //画出圆环
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.WHITE);
        mCirclePaint.setStyle(Paint.Style.FILL);

        //画出外环，所占百分比的环
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(getResources().getColor(R.color.score_eight));
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        //画出内部文字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        //mTextPaint.setARGB(255, 205, 205, 255);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(mRadius / 2);
        //画“阶段“"目标分数""已上""课时数"
        paint1 = new Paint();
        paint1.setTextSize(mRadius / 6);
        //paint1.setTextSize(10);
        paint1.setColor(getResources().getColor(R.color.score_night));
        paint1.setAntiAlias(true);
        //画中心线
        paint2 = new Paint();
        paint2.setColor(getResources().getColor(R.color.gainsboro));
        paint2.setAntiAlias(true);
        //画左侧分数
        paint3 = new Paint();
        paint3.setTextSize(mRadius / 3);
        //paint3.setTextSize(24);
        paint3.setColor(getResources().getColor(R.color.score_ten));
        paint3.setAntiAlias(true);
        //画右侧分数
        paint4 = new Paint();
        paint4.setColor(getResources().getColor(R.color.score_11));
        paint4.setTextSize(mRadius / 3);
        paint4.setAntiAlias(true);

        paint5 = new Paint();
        paint5.setColor(getResources().getColor(R.color.score_12));
        paint5.setTextSize(mRadius / 6);
        paint5.setAntiAlias(true);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

    }


    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = (getHeight() / 2);//五分之二太小
        mRadius = getWidth() / 4 - 22;

        initVariable();

        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
        canvas.drawText("阶段", mXCenter - ((mRadius / 3) * 2) + 3, mYCenter - (mRadius / 2), paint1);
        canvas.drawText("目标分数", mXCenter - ((mRadius / 3) * 2) + 3 - (mRadius / 6), mYCenter - (mRadius / 2) + (mRadius / 6), paint1);
        canvas.drawText("已上", mXCenter + (mRadius / 4), mYCenter - (mRadius / 2), paint1);
        canvas.drawText("课时数", mXCenter + (mRadius / 4) - (mRadius / 12), mYCenter - (mRadius / 2) + (mRadius / 6), paint1);
        canvas.drawText("课时", mXCenter + ((mRadius / 5)) * 3 - (mRadius / 15), mYCenter + (mRadius / 6), paint1);
        canvas.drawLine(mXCenter, mYCenter - (mRadius / 4), mXCenter, mYCenter + (mRadius / 4), paint2);
        mRingRadius = mRadius + mStrokeWidth / 2;

        RectF oval = new RectF();
        oval.left = (mXCenter - mRingRadius);
        oval.top = (mYCenter - mRingRadius);
        oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
        oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
        canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth / 2, roundPaint);
        if (mProgress > 0) {
            canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint);
        }
        //canvas.drawCircle(mXCenter, mYCenter, 250, roundPaint); //

        //canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth / 2, roundPaint);
        //画百分之比
        // String txt = mProgress + "%";
//            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
//            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);
        //canvas.drawText(txt, mXCenter, mYCenter, mTextPaint);
        //画阶段

        if (leftScore.equals("0")) {
            canvas.drawText("暂无数据", mXCenter - ((mRadius / 3) * 2) - (mRadius / 6), mYCenter + (mRadius / 6), paint1);
        } else {
            canvas.drawText(leftScore, mXCenter - ((mRadius / 3) * 2) - (mRadius / 6), mYCenter + (mRadius / 6), paint3);
            canvas.drawText("分", mXCenter - (mRadius / 3) + 3, mYCenter + (mRadius / 6), paint1);
        }
        canvas.drawText(rigScore, mXCenter + (mRadius / 10), mYCenter + (mRadius / 6), paint4);
        String texStr = "剩余课时: " + bottomScore + "课时";
        canvas.drawText(texStr, mXCenter - (mRadius / 2) - (mRadius / 12), mYCenter + ((mRadius / 3) * 2), paint5);

    }

    public void setProgress(int progress, String mleftScore, String mrigScore, String mbottomScore) {
        mProgress = progress;
        leftScore = mleftScore;
        rigScore = mrigScore;
        bottomScore = mbottomScore;
        //invalidate();
        postInvalidate();
    }


    public void setLeftScore(String mleftScore) {
        leftScore = mleftScore;
        postInvalidate();
    }

    public void setRigScore(String mrigScore) {
        rigScore = mrigScore;
        postInvalidate();
    }

    public void setBottomScore(String mbottomScore) {
        bottomScore = mbottomScore;
        postInvalidate();
    }

}
