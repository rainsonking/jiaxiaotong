package com.example.bailyhome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import com.example.bailyhome.R;

/**
 * Created by Administrator on 2016/5/17 0017.
 * <p/>
 * 自定义折线图
 */
public class LineChartView extends View {
    // 默认边距
//    private int Margin = 40;
    private int Margin = 80;
    // 原点坐标
    private int Xpoint, xpoint2;
    private int Ypoint;
    // X,Y轴的单位长度
    private int Xscale = 20;
    private int Yscale = 20;
    // X,Y轴上面的显示文字
//    private String[] Xlabel = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
//    private String[] Ylabel = {"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85",
//            "90", "95", "100"};
//    private String agvScore = "80";
    private String[] Xlabel = null;
    private String[] Ylabel = null;
    private String agvScore = "";
    // 标题文本
    private String Title;
    // 曲线数据
//    private int[] Data = {13, 20, 40, 63, 40, 83, 10, 70, 90, 85};
    private int[] Data = null;

    public LineChartView(Context context, String[] xlabel, String[] ylabel,
                         String title, int[] data, String agvScore) {
        super(context);
        this.Xlabel = xlabel;
        this.Ylabel = ylabel;
        this.Title = title;
        this.Data = data;
        this.agvScore = agvScore;

    }

    public LineChartView(Context context) {
        super(context);
    }

    // 初始化数据值
    public void init() {
        Xpoint = this.Margin;
        Ypoint = this.getHeight() - 200 - this.Margin;
//        Xscale = (this.getWidth() - 2 * this.Margin) / (this.Xlabel.length - 1);
        if ((this.Xlabel.length - 1) > 0) {
            Xscale = (this.getWidth() - 2 * this.Margin) / (this.Xlabel.length - 1);
        }
        Yscale = (this.getHeight() - 200 - 2 * this.Margin)
                / (this.Ylabel.length - 1);
    }

    public int getMargin() {
        return Margin;
    }

    public void setMargin(int margin) {
        Margin = margin;
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(Color.parseColor("#f5f4f4"));
        // canvas.drawColor(Color.parseColor("#FE6978"));
        Paint p1 = new Paint();
        p1.setStyle(Paint.Style.STROKE);
        p1.setAntiAlias(true);
        p1.setColor(Color.WHITE);
        p1.setStrokeWidth(2);
        init();
        this.drawXLine(canvas, p1);
        this.drawYLine(canvas, p1);
        this.drawTable(canvas);
        this.drawData(canvas);
    }

    // 画表格
    private void drawTable(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        //   paint.setColor(getResources().getColor(R.color.font_selector_score));
        Path path = new Path();
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        paint.setPathEffect(effects);
        // 纵向线
        if (Xlabel.length > 1) {
            for (int i = 1; (i * Xscale <= (this.getWidth() - this.Margin)) && (i < Xlabel.length); i++) {
                Log.e("Xlabel", "i" + Xscale + "//" + (this.getWidth() - this.Margin) + "//" + Xlabel.length);
                int startX = Xpoint + i * Xscale;
                int startY = Ypoint;
                int stopY = Ypoint - (this.Ylabel.length - 1) * Yscale;
                path.moveTo(startX, startY);
                path.lineTo(startX, stopY);
                canvas.drawPath(path, paint);
            }
        }
        // 横向线
        for (int i = 1; ((Ypoint - i * Yscale) >= this.Margin) && (i < Ylabel.length); i++) {
            Log.e("Ypo", (Ypoint - i * Yscale) + "");
            int startX = Xpoint;
            int startY = Ypoint - i * Yscale;
            int stopX = Xpoint + (this.Xlabel.length - 1) * Xscale;
            path.moveTo(startX, startY);
            path.lineTo(stopX, startY);
            paint.setColor(Color.DKGRAY);
            canvas.drawPath(path, paint);
            paint.setColor(Color.WHITE);
//            paint.setTextSize(this.Margin / 2);
            paint.setTextSize(20);
            if (Ylabel[i].length() >= 4) {
                canvas.drawText(this.Ylabel[i], this.Margin / 4, startY
                        + this.Margin / 4 - 10, paint);
            } else if (this.Ylabel[i].length() == 3) {
                canvas.drawText(this.Ylabel[i], (this.Margin / 4) + 10, startY
                        + this.Margin / 4 - 10, paint);
            } else if (this.Ylabel[i].length() == 2) {
                canvas.drawText(this.Ylabel[i], (this.Margin / 4) + 20, startY
                        + this.Margin / 4 - 10, paint);
            } else if (this.Ylabel[i].length() == 1) {
                canvas.drawText(this.Ylabel[i], (this.Margin / 4) + 30, startY
                        + this.Margin / 4 - 10, paint);
            }
//            canvas.drawText(this.Ylabel[i], this.Margin / 4, startY
//                    + this.Margin / 4 - 10, paint);
        }
    }

    // 画横纵轴
    private void drawXLine(Canvas canvas, Paint p) {
        canvas.drawLine(Xpoint, Ypoint, this.Margin, this.Margin, p);
        //画箭头
//        canvas.drawLine(Xpoint, this.Margin, Xpoint - Xpoint / 3, this.Margin
//                + this.Margin / 3, p);
//        canvas.drawLine(Xpoint, this.Margin, Xpoint + Xpoint / 3, this.Margin
//                + this.Margin / 3, p);
    }

    private void drawYLine(Canvas canvas, Paint p) {
        canvas.drawLine(Xpoint, Ypoint, this.getWidth() - this.Margin, Ypoint,
                p);
        //画箭头
//        canvas.drawLine(this.getWidth() - this.Margin, Ypoint, this.getWidth()
//                - this.Margin - this.Margin / 3, Ypoint - this.Margin / 3, p);
//        canvas.drawLine(this.getWidth() - this.Margin, Ypoint, this.getWidth()
//                - this.Margin - this.Margin / 3, Ypoint + this.Margin / 3, p);
    }

    // 画数据
    private void drawData(Canvas canvas) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.WHITE);
//        p.setTextSize(this.Margin / 2);
        p.setTextSize(20);
        if (Xlabel.length > 1) {
            // 纵向线
            for (int i = 1; (i * Xscale <= (this.getWidth() - this.Margin)) && (i < Data.length); i++) {
                Log.e("scale", i + "//" + Xscale + "//" + (this.getWidth() - this.Margin));

                int startX = 0;
                if (i == 1) {
                    startX = Xpoint;
                    canvas.drawText(this.Xlabel[i - 1], startX - 20, this.getHeight() - 200 - this.Margin / 4, p);
                    canvas.drawCircle(startX, calY(Data[i - 1]), 4, p);
                    // canvas.drawLine(Xpoint + (i - 1) * Xscale, calY(Data[i - 1]), startX, calY(Data[i]), p);
                }
                startX = Xpoint + i * Xscale;
                canvas.drawText(this.Xlabel[i], startX - 20, this.getHeight() - 200 - this.Margin / 4, p);
                canvas.drawCircle(startX, calY(Data[i]), 4, p);
                canvas.drawLine(Xpoint + (i - 1) * Xscale, calY(Data[i - 1]), startX, calY(Data[i]), p);

            }
        } else {
            int startX = Xpoint;
            canvas.drawText(this.Xlabel[0], startX - 20, this.getHeight() - 200 - this.Margin / 4, p);
            canvas.drawCircle(startX, calY(Data[0]), 4, p);
        }

        xpoint2 = getWidth() / 2;//中心点
        Paint _paint = new Paint();
        //新建矩形r2
        RectF r2 = new RectF();
        r2.left = xpoint2 - 228;
        r2.right = xpoint2 + 228;
        r2.top = getHeight() - 150;
        r2.bottom = getHeight() - 50;

        //画出圆角矩形r2
        _paint.setColor(Color.WHITE);
        canvas.drawRoundRect(r2, 40, 40, _paint);

        //画平均分数
        Paint p2 = new Paint();
        p2.setAntiAlias(true);
        p2.setColor(Color.parseColor("#ff981f"));
        p2.setTextSize(30);
        canvas.drawText("平均得分:", xpoint2 - 120, getHeight() - 85, p2);
        p2.setTextSize(30);
        canvas.drawText(agvScore, xpoint2 + 20, getHeight() - 85, p2);
    }

    /**
     * @param y
     * @return
     */
    private int calY(int y) {
        int y0 = 0;
        int y1 = 0;
        //	Log.i("zzzz", "y:"+y);
        try {
            y0 = Integer.parseInt(Ylabel[0]);
            //		Log.i("zzzz", "y0"+y0);
            y1 = Integer.parseInt(Ylabel[1]);
            //		Log.i("zzzz","y1"+y1);
        } catch (Exception e) {
            //		Log.i("zzzz", "string changed is err");
            return 0;
        }
        try {
            //		Log.i("zzzz", "返回数据"+(Ypoint-(y-y0)*Yscale/(y1-y0)) );
            return Ypoint - ((y - y0) * Yscale / (y1 - y0));
        } catch (Exception e) {
            //	Log.i("zzzz", "return is err");
            return 0;
        }
    }
}
