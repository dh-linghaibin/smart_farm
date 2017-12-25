package com.jt28.a6735.mirro.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.tool.ColorUtils;
import com.jt28.a6735.mirro.tool.GetResources;

/**
 * Created by a6735 on 2017/9/7.
 */

public class CustomProgressbar extends View {
    // 设置圈的宽度
    private int mCircleWidth= 6;
    // 设置颜色填充画笔
    private Paint mPaint;
    // 设置当前进度
    private int mProgress;
    private int mProgress2 = -90;
    // 设置当前进度加载速度
    private int speed=2;
    private boolean flag = false;

    private Context context;

    //圆环颜色
    private int[] doughnutColors = new int[]{Color.parseColor("#dee8f1"), Color.parseColor("#42a5f6")};

    public CustomProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressbar(Context context) {
        this(context, null);
    }

    /**
     * 必要的初始化，获取一些自定义的值
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomProgressbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        mPaint = new Paint();
        // 绘图线程 此线程为耗时线程，放在子线程中执行，防止主线程的卡顿
        new Thread() {
            public void run() {
                while (true) {
                    if(flag) {
                        mProgress++;
                        if (mProgress == 360) {
                            mProgress = 0;
                        }
                        mProgress2++;
                        if (mProgress == 0) {
                            mProgress2 = -90;
                        }
                        // 刷新UI
                        // postInvalidate()此方法可以直接在UI线程调用，invalidate()则需要在handler中进行调用
                        postInvalidate();
                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {

                    }
                }
            }
        }.start();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logger.e("view内部设置OnClickListener","");
                Log.d("lhb","按下");
                if(voice_but) {
                    voice_but = false;
                    flag = !flag;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //execute the task
                            voice_but = true;
                        }
                    }, 3000);
                }
            }
        });
    }

    private boolean voice_but = true;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取圆心的x坐标
        int center = getWidth() / 2;
        int width = getWidth();
        int height = getHeight();
        if(flag) {
            // 获取圆的半径
            int radius = center - mCircleWidth / 2;
            // 设置填充的宽度
            mPaint.setStrokeWidth(mCircleWidth);
            mPaint.setAntiAlias(true);
            // 设置填充的style
            mPaint.setStyle(Paint.Style.STROKE);
            // new RectF(left, top, right, bottom) 为距离x轴，y轴之间的距离
            // 定义rect的形状
            RectF f = new RectF(center - radius, center - radius, center + radius, center + radius);
            Paint paint = new Paint();
            paint.setAntiAlias(true);//抗锯齿
            paint.setColor(Color.parseColor("#42a5f6"));
            canvas.drawCircle(center, center, center - 13, paint);
            // 设置圆环颜色
            mPaint.setColor(ColorUtils.getCurrentColor(mProgress / 360f, doughnutColors));
            /*
            * public void drawArc(RectF oval, float startAngle, float sweepAngle,
            * boolean useCenter, Paint paint) oval :指定圆弧的外轮廓矩形区域。 startAngle:
            * 圆弧起始角度，单位为度。 sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。 useCenter:
            * 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。 paint: 绘制圆弧的画板属性，如颜色，是否填充等。
            */
            canvas.drawArc(f, mProgress2, mProgress, false, mPaint);

            Paint paint_t = new Paint();
            paint_t.setAntiAlias(true);//抗锯齿
            paint_t.setColor(Color.parseColor("#ffffff"));
            paint_t.setTextSize(20);
            paint_t.setTextAlign(Paint.Align.CENTER);
            float baseLine = height / 2 - (paint_t.getFontMetrics().descent + paint_t.getFontMetrics().ascent) / 2;
            canvas.drawText("提交", width / 2, baseLine, paint_t);
        } else {
            float w = (float) (width*0.8);
            float h = (float) (height*0.8);
            RectF iconRect = new RectF((float) (width*0.1), (float) (height*0.1), width, height);
            Bitmap icon = GetResources.getWeatherIcon(context, R.mipmap.btn_voice_default, w, h);
            canvas.drawBitmap(icon, null, iconRect, null);//画图标
        }
    }
}
