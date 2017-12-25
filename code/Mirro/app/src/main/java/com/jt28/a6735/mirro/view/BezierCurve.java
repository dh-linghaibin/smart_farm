package com.jt28.a6735.mirro.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.tool.GetResources;

import java.util.ArrayList;

/**
 * 贝塞尔曲线展示界面
 *
 * <p> 提供的方法
 * 开始贝塞尔曲线（required） @<code> void start() </code>
 * 停止贝塞尔曲线（optional）@<code> void stop() </code>
 * 增加控制点（optional））@<code> boolean addPoint() </code>
 * 删除控制点（optional） @<code> boolean delPoint()</code>
 * 获取贝塞尔曲线阶数（optional）@<code> int getOrder() </code>
 * 设置移动速率（optional）@<code> void setRate(int rate) </code>
 * 设置是否显示切线（optional）（optional）@<code> void setTangent(boolean tangent)</code>
 * 设置是否循环（optional）（optional）@<code> void setLoop(boolean loop) </code>
 * 设置贝塞尔曲线阶数（optional）@<code> void setOrder(int order) </code>
 * <p/>
 */

/**
 * Created by a6735 on 2017/8/23.
 */

public class BezierCurve extends View {
    // 贝塞尔曲线最大阶数
    private static final int MAX_COUNT = 9;
    // 合法区域宽度
    private static final int REGION_WIDTH = 10;
    // 矩形尺寸
    private static final int FINGER_RECT_SIZE = 60;
    // 贝塞尔曲线线宽
    private static final int BEZIER_WIDTH = 3;
    // 切线线宽
    private static final int TANGENT_WIDTH = 2;
    // 控制点连线线宽
    private static final int CONTROL_WIDTH = 6;
    // 控制点半径
    private static final int CONTROL_RADIUS = 6;
    // 文字画笔尺寸
    private static final int TEXT_SIZE = 20;
    // 文本高度
    private static final int TEXT_HEIGHT = 60;
    // 移动速率
    private static final int RATE = 20;
    private static final int HANDLER_WHAT = 100;
    // 1000帧
    private static final int FRAME = 1000;
    // 切线颜色
    private static final String[] TANGENT_COLORS = {"#42a5f6", "#7a67ee", "#ee82ee", "#ffd700", "#1c86ee",
            "#8b8b00"};
    //抬头标示
    private static String[] TAI_TOU = {"星期一","星期二","星期三","星期四","星期五","星期六","星期天"};
    private static String[] TAI_TOU_tq = {"晴","阴","多云","大雪","中雪","小雨","阵雪"};
    private static int[] weizhi = {69,208,347,486,625,764,903};
    private static int[] TAI_TOU_temp_l = {10,10,10,10,10,10,10};
    private static int[] TAI_TOU_temp_d = {10,10,10,10,10,10,10};
    private boolean arrave = false;
    private float arrave_val_1 = 0;
    private float arrave_val_2 = 0;
    //选中的日期
    private static int TAI_TOU_C = 0;

    private static final int STATE_READY = 0x0001;
    private static final int STATE_RUNNING = 0x0002;
    private static final int STATE_STOP = 0x0004;
    private static final int STATE_TOUCH = 0x0010;

    // 贝塞尔曲线路径
    private Path mBezierPath = null;
    // 贝塞尔曲线画笔
    private Paint mBezierPaint = null;
    // 贝塞尔曲线路径
    private Path mBezierPath2 = null;
    // 贝塞尔曲线画笔
    private Paint mBezierPaint2 = null;
    // 移动点画笔
//    private Paint mMovingPaint = null;
    // 控制点画笔
//    private Paint mControlPaint = null;
//    // 切线画笔
//    private Paint mTangentPaint = null;
    // 固定线画笔
    private Paint mLinePaint = null;
    // 点画笔
    private Paint mTextPointPaint = null;
    // 文字画笔
    private Paint mTextPaint = null;
    private Paint mTextPaint2 = null;
    // 移动速率
    private int mR = 0;
    // 速率
    private int mRate = RATE;
    // 状态
    private int mState;
    // 画布宽高
    private int mWidth = 0, mHeight = 0;
    //开始位置
    private int sWidth = 0,jWidth = 0;
    private int sHeight = 0,jHeight = 0;
    // 贝塞尔曲线点集
    private ArrayList<PointF> mBezierPoints_l = null;
    private ArrayList<PointF> mBezierPoints_d = null;
    // 贝塞尔曲线移动点
    private PointF mBezierPoint = null;
    // 贝塞尔曲线移动点
    private PointF mBezierPoint2 = null;
    // 控制点集
    private ArrayList<PointF> mControlPoints_l = null;
    private ArrayList<PointF> mControlPoints_d = null;
    // 当前移动的控制点
    private PointF mCurPoint;
    // 设置是否循环
    private boolean mLoop = false;
    // 设置是否显示切线
    private float iconWidth = 45;  //天气图标的边长

    public void SetTAl(String[] TAI_TOU){
        this.TAI_TOU = TAI_TOU;
    }
    public void SetTAI_TOU_tq(String[] TAI_TOU_tq) {
        this.TAI_TOU_tq = TAI_TOU_tq;
    }

    public void SetTemp(int[] TAI_TOU_temp_l,int[] TAI_TOU_temp_d) {
        this.TAI_TOU_temp_l = TAI_TOU_temp_l;
        this.TAI_TOU_temp_d = TAI_TOU_temp_d;
        int temp_lp = GetResources.detarr(TAI_TOU_temp_l);
        int temp_hp = GetResources.detarr(TAI_TOU_temp_d);

        mControlPoints_l.clear();

        mControlPoints_l.add(new PointF(29,  200+((TAI_TOU_temp_l[0]-temp_lp)*10) ));
        mControlPoints_l.add(new PointF(69,  200+((TAI_TOU_temp_l[0]-temp_lp)*10) ));
        mControlPoints_l.add(new PointF(208, 200+((TAI_TOU_temp_l[1]-temp_lp)*10)));
        mControlPoints_l.add(new PointF(347, 200+((TAI_TOU_temp_l[2]-temp_lp)*10)));
        mControlPoints_l.add(new PointF(486, 200+((TAI_TOU_temp_l[3]-temp_lp)*10)));
        mControlPoints_l.add(new PointF(625, 200+((TAI_TOU_temp_l[4]-temp_lp)*10)));
        mControlPoints_l.add(new PointF(764, 200+((TAI_TOU_temp_l[5]-temp_lp)*10)));
        mControlPoints_l.add(new PointF(903, 200+((TAI_TOU_temp_l[6]-temp_lp)*10)));
        mControlPoints_l.add(new PointF(930, 200+((TAI_TOU_temp_l[6]-temp_lp)*10) ));

        mControlPoints_d.clear();
        mControlPoints_d.add(new PointF(29,  270+((TAI_TOU_temp_d[0]-temp_hp)*10) ));
        mControlPoints_d.add(new PointF(69,  270+((TAI_TOU_temp_d[0]-temp_hp)*10)));
        mControlPoints_d.add(new PointF(208, 270+((TAI_TOU_temp_d[1]-temp_hp)*10)));
        mControlPoints_d.add(new PointF(347, 270+((TAI_TOU_temp_d[2]-temp_hp)*10)));
        mControlPoints_d.add(new PointF(486, 270+((TAI_TOU_temp_d[3]-temp_hp)*10)));
        mControlPoints_d.add(new PointF(625, 270+((TAI_TOU_temp_d[4]-temp_hp)*10)));
        mControlPoints_d.add(new PointF(764, 270+((TAI_TOU_temp_d[5]-temp_hp)*10)));
        mControlPoints_d.add(new PointF(903, 270+((TAI_TOU_temp_d[6]-temp_hp)*10)));
        mControlPoints_d.add(new PointF(930, 270+((TAI_TOU_temp_d[6]-temp_hp)*10) ));
    }

    private int getIconResId(String weather) {
        int resId;
        switch (weather) {
            case "晴":
                resId = R.mipmap.icon_sunny_pressed_36;
                break;
            case "阴":
                resId = R.mipmap.icon_cloudy_pressed_36;
                break;
            case "多云":
                resId = R.mipmap.icon_overcast_pressed_36;
                break;
            case "雾":
                resId = R.mipmap.icon_fog_pressed_36;
                break;
            case "小雨":
                resId = R.mipmap.icon_rain_pressed_36;
                break;
            case "小雪":
                resId = R.mipmap.icon_heavysnow_pressed_36;
                break;
            case "中雨":
                resId = R.mipmap.icon_rain_pressed_36;
                break;
            case "阵雨":
                resId = R.mipmap.icon_shower_pressed_36;
                break;
            case "雷阵雨":
                resId = R.mipmap.icon_thunderstorm_pressed_36;
                break;
            case "阵雪":
                resId = R.mipmap.icon_snowshower_pressed_36;
                break;
            case "中雪":
                resId = R.mipmap.icon_sleet_pressed_36;
                break;
            case "大雪":
                resId = R.mipmap.icon_snow_pressed_36;
                break;
            case "雨夹雪":
                resId = R.mipmap.icon_sleet_pressed_36;
                break;
            case "霾":
                resId = R.mipmap.icon_fog_pressed_36;
                break;
            case "雷":
                resId = R.mipmap.icon_thunderstorm_pressed_36;
                break;
            default:
                resId = R.mipmap.icon_sunny_pressed_36;
                break;
        }
        return resId;
    }

    private int getIconResIdC(String weather) {
        int resId;
        switch (weather) {
            case "晴":
                resId = R.mipmap.icon_sunny_48;
                break;
            case "阴":
                resId = R.mipmap.icon_cloudy_48;
                break;
            case "多云":
                resId = R.mipmap.icon_overcast_48;
                break;
            case "雾":
                resId = R.mipmap.icon_fog_48;
                break;
            case "小雨":
                resId = R.mipmap.icon_rain_48;
                break;
            case "小雪":
                resId = R.mipmap.icon_heavysnow_48;
                break;
            case "中雨":
                resId = R.mipmap.icon_rain_48;
                break;
            case "阵雨":
                resId = R.mipmap.icon_shower_48;
                break;
            case "雷阵雨":
                resId = R.mipmap.icon_thunderstorm_48;
                break;
            case "阵雪":
                resId = R.mipmap.icon_snowshower_48;
                break;
            case "中雪":
                resId = R.mipmap.icon_sleet_48;
                break;
            case "大雪":
                resId = R.mipmap.icon_snow_48;
                break;
            case "雨夹雪":
                resId = R.mipmap.icon_sleet_48;
                break;
            case "霾":
                resId = R.mipmap.icon_fog_48;
                break;
            case "雷":
                resId = R.mipmap.icon_thunderstorm_48;
                break;
            default:
                resId = R.mipmap.icon_sunny_48;
                break;
        }
        return resId;
    }
    /**
     * @param requestW
     * @param requestH
     * @return
     */
    private Bitmap getWeatherIcon2(int resId, float requestW, float requestH) {
        Bitmap bmp;
        int outWdith, outHeight;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), resId, options);
        outWdith = options.outWidth;
        outHeight = options.outHeight;
        options.inSampleSize = 1;
        if (outWdith > requestW || outHeight > requestH) {
            int ratioW = Math.round(outWdith / requestW);
            int ratioH = Math.round(outHeight / requestH);
            options.inSampleSize = Math.max(ratioW, ratioH);
        }
        options.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeResource(getResources(), resId, options);
        return bmp;
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLER_WHAT) {
                mR += mRate;
                if (mR >= mBezierPoints_l.size()) {
                    removeMessages(HANDLER_WHAT);
                    mR = 0;
                    mState &= ~STATE_RUNNING;
                    mState &= ~STATE_STOP;
                    mState |= STATE_READY | STATE_TOUCH;
                    if (mLoop) {
                        start();
                    }
                    return;
                }
                if (mR != mBezierPoints_l.size() - 1 && mR + mRate >= mBezierPoints_l.size()) {
                    mR = mBezierPoints_l.size() - 1;
                }
                if (mR != mBezierPoints_d.size() - 1 && mR + mRate >= mBezierPoints_d.size()) {
                    mR = mBezierPoints_d.size() - 1;
                }

                // Bezier点
                mBezierPoint = new PointF(mBezierPoints_l.get(mR).x, mBezierPoints_l.get(mR).y);
                mBezierPoint2 = new PointF(mBezierPoints_d.get(mR).x, mBezierPoints_d.get(mR).y);

                if (mR == mBezierPoints_l.size() - 1) {
                    mState |= STATE_STOP;
                }
                invalidate();
            }
        }
    };

    public BezierCurve(Context context) {
        super(context);
        init();
    }

    public BezierCurve(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierCurve(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 初始坐标
        mControlPoints_l = new ArrayList<>(MAX_COUNT + 1);
        int w = getResources().getDisplayMetrics().widthPixels;

        mControlPoints_l.add(new PointF(69, 170));
        mControlPoints_l.add(new PointF(208, 200));
        mControlPoints_l.add(new PointF(347, 170));
        mControlPoints_l.add(new PointF(486, 200));
        mControlPoints_l.add(new PointF(625, 170));
        mControlPoints_l.add(new PointF(764, 200));
        mControlPoints_l.add(new PointF(903, 170));

        mControlPoints_d = new ArrayList<>(MAX_COUNT + 1);
        mControlPoints_d.add(new PointF(69, 250));
        mControlPoints_d.add(new PointF(208, 250));
        mControlPoints_d.add(new PointF(347, 250));
        mControlPoints_d.add(new PointF(486, 250));
        mControlPoints_d.add(new PointF(625, 250));
        mControlPoints_d.add(new PointF(764, 250));
        mControlPoints_d.add(new PointF(903, 250));

        // 贝塞尔曲线画笔
        mBezierPaint = new Paint();
        mBezierPaint.setColor(Color.parseColor(TANGENT_COLORS[0]));
        mBezierPaint.setStrokeWidth(BEZIER_WIDTH);
        mBezierPaint.setStyle(Paint.Style.STROKE);
        mBezierPaint.setAntiAlias(true);

        mBezierPaint2 = new Paint();
        mBezierPaint2.setColor(Color.parseColor(TANGENT_COLORS[1]));
        mBezierPaint2.setStrokeWidth(BEZIER_WIDTH);
        mBezierPaint2.setStyle(Paint.Style.STROKE);
        mBezierPaint2.setAntiAlias(true);

        // 固定线画笔
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.LTGRAY);
        mLinePaint.setStrokeWidth(CONTROL_WIDTH);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.FILL);

        // 点画笔
        mTextPointPaint = new Paint();
        mTextPointPaint.setColor(Color.parseColor("#303030"));//setColor(Color.BLACK);
        mTextPointPaint.setAntiAlias(true);
        mTextPointPaint.setTextSize(TEXT_SIZE);

        // 文字画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor(TANGENT_COLORS[0]));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(20);

        mTextPaint2 = new Paint();
        mTextPaint2.setColor(Color.parseColor(TANGENT_COLORS[1]));
        mTextPaint2.setAntiAlias(true);
        mTextPaint2.setTextSize(20);

        //底线画笔
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(CONTROL_WIDTH);

        mBezierPath = new Path();
        mBezierPath2 = new Path();

        mState |= STATE_READY | STATE_TOUCH;
    }

    /**
     * 创建Bezier点集
     *
     * @return
     */
    private ArrayList<PointF> buildBezierPoints_l() {
        ArrayList<PointF> points = new ArrayList<>();
        int order = mControlPoints_l.size() - 1;
        float delta = 1.0f / FRAME;
        for (float t = 0; t <= 1; t += delta) {
            // Bezier点集
            points.add(new PointF(deCasteljauX_l(order, 0, t), deCasteljauY_l(order, 0, t)));
        }
        return points;
    }
    private ArrayList<PointF> buildBezierPoints_d() {
        ArrayList<PointF> points = new ArrayList<>();
        int order = mControlPoints_d.size() - 1;
        float delta = 1.0f / FRAME;
        for (float t = 0; t <= 1; t += delta) {
            // Bezier点集
            points.add(new PointF(deCasteljauX_d(order, 0, t), deCasteljauY_d(order, 0, t)));
        }
        return points;
    }

    private Paint linePaint; //低线画笔
    private static final int LINE_WIDTH = 2;
    private static int DEFAULT_GRAY = Color.GRAY;//底线颜色
    private int defaultPadding = 10; //折线坐标图四周留出来的偏移量
    /**
     * 底线
     * @param canvas
     */
    private void drawAxis(Canvas canvas) {
        canvas.save();

        linePaint.setColor(Color.parseColor("#b7b7b7"));
        linePaint.setStrokeWidth(1);//设置线宽

        canvas.drawLine(defaultPadding,
                mHeight - defaultPadding,
                mWidth - defaultPadding,
                mHeight - defaultPadding,
                linePaint);

        linePaint.setColor(Color.parseColor("#99caf3"));
        linePaint.setStrokeWidth(5);//设置线宽

        canvas.drawLine(defaultPadding,
                defaultPadding,
                mWidth - defaultPadding,
                defaultPadding,
                linePaint);

        canvas.restore();
    }

    /**
     * 画不同天气之间的虚线
     *
     * @param canvas
     */
    private void drawWeatherDash(Canvas canvas) {
        canvas.save();
        linePaint.setColor(Color.parseColor("#b7b7b7"));
        linePaint.setStrokeWidth(1);//设置线宽

        for(int i =0;i < 7;i++) {
            canvas.drawLine(sWidth+(sWidth/2) + i*jWidth, sHeight+10,
                    sWidth+(sWidth/2) + i*jWidth, sHeight+40,
                    linePaint);
            Log.d("xhh", String.valueOf(sWidth+(sWidth/2) + i*jWidth));
        }

        for(int i =0;i < 7;i++) {
            canvas.drawLine(sWidth+(sWidth/2) + i*jWidth, sHeight*3,
                    sWidth+(sWidth/2) + i*jWidth, mHeight-defaultPadding,
                    linePaint);
        }

        canvas.restore();
    }

    /**
     * 画图标
     *  @author
     *  @time
     *  @describe
     */
    private void drawWeatherIcons(Canvas canvas) {
        for(int i =0;i < 7;i++) {
            //经过上述校正之后可以得到图标和文字的绘制区域
            RectF iconRect = new RectF(sWidth + i*jWidth, sHeight*2, iconWidth + sWidth + i*jWidth, iconWidth + sHeight*2);
            Bitmap icon = null;
            if(TAI_TOU_C == i) {
                icon = getWeatherIcon2(getIconResId(TAI_TOU_tq[i]), iconWidth, iconWidth);
            } else {
                icon = getWeatherIcon2(getIconResIdC(TAI_TOU_tq[i]), iconWidth, iconWidth);
            }
            canvas.drawBitmap(icon, null, iconRect, null);//画图标
        }
    }

    /**
     * 画抬头
     *  @author
     *  @time
     *  @describe
     */
    private void drawWeatherTals(Canvas canvas) {
        for(int i =0;i < 7;i++) {
            canvas.drawText(TAI_TOU[i],sWidth+i*jWidth, jHeight, mTextPointPaint);
            if(TAI_TOU_C == i) {
                RectF iconRect = new RectF(sWidth+i*jWidth-jWidth/4, jHeight-40, 126 + sWidth + i*jWidth-jWidth/4, 116 + jHeight-40);
                Bitmap icon = getWeatherIcon2(R.mipmap.bg_week,126,116);
                canvas.drawBitmap(icon, null, iconRect, null);//画图标
            }
        }
    }
    /**
     * deCasteljau算法
     *
     * @param i 阶数
     * @param j 点
     * @param t 时间
     * @return
     */
    private float deCasteljauX_l(int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * mControlPoints_l.get(j).x + t * mControlPoints_l.get(j + 1).x;
        }
        return (1 - t) * deCasteljauX_l(i - 1, j, t) + t * deCasteljauX_l(i - 1, j + 1, t);
    }

    private float deCasteljauX_d(int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * mControlPoints_d.get(j).x + t * mControlPoints_d.get(j + 1).x;
        }
        return (1 - t) * deCasteljauX_d(i - 1, j, t) + t * deCasteljauX_d(i - 1, j + 1, t);
    }

    /**
     * deCasteljau算法
     *
     * @param i 阶数
     * @param j 点
     * @param t 时间
     * @return
     */
    private float deCasteljauY_l(int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * mControlPoints_l.get(j).y + t * mControlPoints_l.get(j + 1).y;
        }
        return (1 - t) * deCasteljauY_l(i - 1, j, t) + t * deCasteljauY_l(i - 1, j + 1, t);
    }
    private float deCasteljauY_d(int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * mControlPoints_d.get(j).y + t * mControlPoints_d.get(j + 1).y;
        }
        return (1 - t) * deCasteljauY_d(i - 1, j, t) + t * deCasteljauY_d(i - 1, j + 1, t);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mWidth == 0 || mHeight == 0) {
            mWidth = getWidth();
            mHeight = getHeight();
            jWidth = mWidth/7;
            sWidth = jWidth/3;
            //计算画布长度
            sHeight = mHeight/7;
            jHeight = sHeight;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mWidth = getWidth();
        mHeight = getHeight();
        jWidth = mWidth/7;
        sWidth = jWidth/3;
        //计算画布长度
        sHeight = mHeight/7;
        jHeight = sHeight;

        drawAxis(canvas);
        drawWeatherDash(canvas);
        drawWeatherIcons(canvas);
        drawWeatherTals(canvas);

        if (isRunning() && !isTouchable()) {
            if (mBezierPoint == null) {
                mBezierPath.reset();
                mBezierPoint = mBezierPoints_l.get(0);
                mBezierPath.moveTo(mBezierPoint.x, mBezierPoint.y);
            }
            if (mBezierPoint2 == null) {
                mBezierPath2.reset();
                mBezierPoint2 = mBezierPoints_d.get(0);
                mBezierPath2.moveTo(mBezierPoint2.x, mBezierPoint2.y);
            }
            // Bezier曲线
            mBezierPath.lineTo(mBezierPoint.x, mBezierPoint.y);

            canvas.drawPath(mBezierPath, mBezierPaint);
            // Bezier曲线
            mBezierPath2.lineTo(mBezierPoint2.x, mBezierPoint2.y);
            canvas.drawPath(mBezierPath2, mBezierPaint2);

            if(mBezierPoint2 != null) {
                if ((mBezierPoint2.x >= weizhi[TAI_TOU_C]) && (!arrave)) {
                    arrave = true;
                    arrave_val_1 = mBezierPoint.y;
                    arrave_val_2 = mBezierPoint2.y;
                }
            }
            if(arrave_val_1 != 0) {
                canvas.drawCircle(weizhi[TAI_TOU_C], arrave_val_1, 5, mTextPaint);// 小圆
                canvas.drawText(TAI_TOU_temp_l[TAI_TOU_C] + "°", weizhi[TAI_TOU_C]+10, arrave_val_1-20, mTextPaint);

                canvas.drawCircle(weizhi[TAI_TOU_C], arrave_val_2, 5, mTextPaint2);// 小圆
                canvas.drawText(TAI_TOU_temp_d[TAI_TOU_C] + "°", weizhi[TAI_TOU_C]+10, arrave_val_2-20, mTextPaint2);
            }
            mHandler.removeMessages(HANDLER_WHAT);
            mHandler.sendEmptyMessage(HANDLER_WHAT);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        this.currentX = event.getX(); //触摸座标X
//        this.currentY = event.getY(); //触摸座标Y


        if(event.getX() > weizhi[6]-100) {
            TAI_TOU_C = 6;
        } else if(event.getX() > weizhi[5]-100) {
            TAI_TOU_C = 5;
        } else if(event.getX() > weizhi[4]-100) {
            TAI_TOU_C = 4;
        } else if(event.getX() > weizhi[3]-100) {
            TAI_TOU_C = 3;
        } else if(event.getX() > weizhi[2]-100) {
            TAI_TOU_C = 2;
        } else if(event.getX() > weizhi[1]-100) {
            TAI_TOU_C = 1;
        } else {
            TAI_TOU_C = 0;
        }

        start();

        //invalidate();//重绘组件

        if (!isTouchable()) {
            return true;
        }
        return true;
    }

    private boolean isReady() {
        return (mState & STATE_READY) == STATE_READY;
    }

    private boolean isRunning() {
        return (mState & STATE_RUNNING) == STATE_RUNNING;
    }

    private boolean isTouchable() {
        return (mState & STATE_TOUCH) == STATE_TOUCH;
    }

    private boolean isStop() {
        return (mState & STATE_STOP) == STATE_STOP;
    }

    /**
     * 开始
     */
    public void start() {
        if (isReady()) {
            arrave_val_1 = 0;
            arrave_val_2 = 0;
            arrave = false;
            mBezierPoint = null;
            mBezierPoint2 = null;
            mBezierPoints_l = buildBezierPoints_l();
            mBezierPoints_d = buildBezierPoints_d();

            mState &= ~STATE_READY;
            mState &= ~STATE_TOUCH;
            mState |= STATE_RUNNING;
            invalidate();
        }
    }

    /**
     * 停止
     */
    public void stop() {
        if (isRunning()) {
            mHandler.removeMessages(HANDLER_WHAT);
            mR = 0;
            mState &= ~STATE_RUNNING;
            mState &= ~STATE_STOP;
            mState |= STATE_READY | STATE_TOUCH;
            invalidate();
        }
    }

    /**
     * 添加控制点
     */
    public boolean addPoint() {
        if (isReady()) {
            return true;
        }
        return false;
    }

    /**
     * 删除控制点
     */
    public boolean delPoint() {
        if (isReady()) {
            return true;
        }
        return false;
    }

    /**
     * 贝塞尔曲线阶数
     *
     * @return
     */
    public int getOrder() {
        return 8;
    }

    /**
     * 设置贝塞尔曲线阶数
     *
     * @param order
     */
    public void setOrder(int order) {
        if (getOrder() == order) {
            return;
        }
        stop();
        int size = getOrder() - order;
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                delPoint();
            }
        } else if (size < 0) {
            for (int i = -size; i > 0; i--) {
                addPoint();
            }
        }
    }

    /**
     * 设置移动速率
     *
     * @param rate
     */
    public void setRate(int rate) {
        mRate = rate;
    }

    /**
     * 设置是否显示切线
     *
     * @param tangent
     */

    /**
     * 设置是否循环
     *
     * @param loop
     */
    public void setLoop(boolean loop) {
        mLoop = loop;
    }

}
