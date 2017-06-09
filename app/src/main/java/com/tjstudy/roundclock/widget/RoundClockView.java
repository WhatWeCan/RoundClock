package com.tjstudy.roundclock.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.tjstudy.roundclock.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义圆形钟表
 * Created by tjstudy on 2017/3/6.
 */
public class RoundClockView extends View {

    private Paint mPaint;
    private int width;
    private int height;
    private int circleWidth;
    private float smallScale;
    private float bigScale;
    private Paint mTxtPaint;
    private int secWidth;
    private Paint mSecPaint;
    private int miWidth;
    private Paint miPaint;
    private int houWidth;
    private Paint houPaint;
    private float secDegree;
    private float miDegree;
    private float houDegree;
    private TypedArray typedArray;
    private float mTimeSize;
    private int mScaleColor;
    private int mTimeColor;
    private int mSecColor;
    private int mMiColor;
    private int mHouColor;
    private float mSecHeight;
    private float mMiHeight;
    private float mHouHeight;
    private int sScaleWidth;
    private int bScaleWidth;
    private String ymdTime;
    private String hmsTime;
    private Paint nowTimePaint;
    private float mNowTimeTxtSize;
    private int mNowTimeTxtColor;

    public RoundClockView(Context context) {
        this(context, null);
    }

    public RoundClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundClockView, defStyleAttr, 0);
        initData();
        initPaint();
    }

    private void initData() {
        //时针的宽度
        circleWidth = typedArray.getInteger(R.styleable.RoundClockView_circleWidth, 2);
        //小刻度
        smallScale = typedArray.getInteger(R.styleable.RoundClockView_smallScale, 14);
        //大刻度
        bigScale = typedArray.getInteger(R.styleable.RoundClockView_bigScale, 30);
        //小刻度 宽度
        sScaleWidth = typedArray.getInteger(R.styleable.RoundClockView_sScaleWidth, 2);
        //大刻度 宽度
        bScaleWidth = typedArray.getInteger(R.styleable.RoundClockView_bScaleWidth, 4);
        //秒针宽度
        secWidth = typedArray.getInteger(R.styleable.RoundClockView_secWidth, 4);
        //分针宽度
        miWidth = typedArray.getInteger(R.styleable.RoundClockView_miWidth, 6);
        //时针的宽度
        houWidth = typedArray.getInteger(R.styleable.RoundClockView_houWidth, 12);
        //整点的字体大小
        mTimeSize = typedArray.getDimension(R.styleable.RoundClockView_timeSize, 30);
        //刻度和圆的颜色
        mScaleColor = typedArray.getColor(R.styleable.RoundClockView_scaleColor, Color.BLACK);
        //整点字体颜色
        mTimeColor = typedArray.getColor(R.styleable.RoundClockView_timeColor, Color.BLACK);
        //秒针颜色
        mSecColor = typedArray.getColor(R.styleable.RoundClockView_secColor, Color.RED);
        //分针颜色
        mMiColor = typedArray.getColor(R.styleable.RoundClockView_miColor, Color.BLACK);
        //时针颜色
        mHouColor = typedArray.getColor(R.styleable.RoundClockView_houColor, Color.BLACK);
        //秒针长度
        mSecHeight = typedArray.getDimension(R.styleable.RoundClockView_secHeight, 200);
        //分针长度
        mMiHeight = typedArray.getDimension(R.styleable.RoundClockView_miHeight, 160);
        //时针长度
        mHouHeight = typedArray.getDimension(R.styleable.RoundClockView_houHeight, 90);

        //当前时间文本字体大小
        mNowTimeTxtSize = typedArray.getDimension(R.styleable.RoundClockView_nowTimeTxtSize, 26);
        //当前时间文本字体颜色
        mNowTimeTxtColor = typedArray.getColor(R.styleable.RoundClockView_nowTimeTxtColor, Color.BLACK);
        typedArray.recycle();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mScaleColor);
        mPaint.setStyle(Paint.Style.STROKE);

        mTxtPaint = new Paint();
        mTxtPaint.setAntiAlias(true);
        mTxtPaint.setColor(mTimeColor);
        mTxtPaint.setTextSize(mTimeSize);

        mSecPaint = new Paint();
        mSecPaint.setAntiAlias(true);
        mSecPaint.setColor(mSecColor);
        mSecPaint.setStyle(Paint.Style.STROKE);
        mSecPaint.setStrokeCap(Paint.Cap.ROUND);
        mSecPaint.setStrokeWidth(secWidth);

        miPaint = new Paint();
        miPaint.setAntiAlias(true);
        miPaint.setColor(mMiColor);
        miPaint.setStyle(Paint.Style.STROKE);
        miPaint.setStrokeCap(Paint.Cap.ROUND);
        miPaint.setStrokeWidth(miWidth);

        houPaint = new Paint();
        houPaint.setAntiAlias(true);
        houPaint.setColor(mHouColor);
        houPaint.setStrokeCap(Paint.Cap.ROUND);
        houPaint.setStyle(Paint.Style.STROKE);
        houPaint.setStrokeWidth(houWidth);

        nowTimePaint = new Paint();
        nowTimePaint.setAntiAlias(true);
        nowTimePaint.setTextSize(mNowTimeTxtSize);
        nowTimePaint.setColor(mNowTimeTxtColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = 500;
        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            height = 500;
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        //1、画外圆
        mPaint.setStrokeWidth(circleWidth);
        float bigRadius = (width - circleWidth) / 2;
        canvas.drawCircle(0, 0, bigRadius, mPaint);
        //2、画小刻度
        mPaint.setStrokeWidth(sScaleWidth);
        float smallScaleStartX = bigRadius - smallScale;
        for (int i = 0; i < 360; i += 6) {
            canvas.drawLine(smallScaleStartX, 0, bigRadius, 0, mPaint);
            canvas.rotate(6);
        }
        mPaint.setStrokeWidth(bScaleWidth);
        //3、画大刻度
        float bigScaleStartX = bigRadius - bigScale;
        for (int i = 0; i < 360; i += 30) {
            canvas.drawLine(bigScaleStartX, 0, bigRadius, 0, mPaint);
            canvas.rotate(30);
        }
        //4、画文本
        //文本所在的内圆 设置距离大刻度18像素
        float txtRadius = bigRadius - 18 - bigScale;
        //计算圆上指定刻度的点的位置
        int time = 1;
        canvas.restore();
        for (int i = -60; i < 300; i += 30) {
            double radians = Math.toRadians(i);
            String timeStr = time + "";
            Rect rect = new Rect();
            mTxtPaint.getTextBounds(timeStr, 0, timeStr.length(), rect);
            //获取内圆上的点的位置
            float x = (float) Math.cos(radians) * txtRadius - rect.width() / 2;
            float y = (float) Math.sin(radians) * txtRadius + rect.height() / 2;
            canvas.drawText(timeStr, x, y, mTxtPaint);
            time++;
        }

        setTime();
        //5、画时间文本
        float timeCenter = bigRadius / 2;
        //计算该点的x,y 位置 ---(0,timeCenter)
        Rect ymdTimeRect = new Rect();
        nowTimePaint.getTextBounds(ymdTime, 0, ymdTime.length(), ymdTimeRect);
        Rect hmsTimeRect = new Rect();
        nowTimePaint.getTextBounds(hmsTime, 0, hmsTime.length(), hmsTimeRect);
        //年月日的位置 (-文本宽/2，y-2)
        canvas.drawText(ymdTime, -ymdTimeRect.width() / 2, timeCenter - 20, nowTimePaint);
        //时分秒的位置 (-文本宽/2，y+文本高+2)
        canvas.drawText(hmsTime, -hmsTimeRect.width() / 2, timeCenter + hmsTimeRect.height(), nowTimePaint);

        //6、画时针
        //结束点
        float houRadius = mHouHeight;
        float houEndX = (float) (houRadius * Math.cos(Math.toRadians(houDegree)));
        float houEndY = (float) (houRadius * Math.sin(Math.toRadians(houDegree)));
        canvas.drawLine(-houEndX / 6, -houEndY / 6, houEndX, houEndY, houPaint);

        //7、画分针
        //结束点
        float miRadius = mMiHeight;
        float miEndX = (float) (miRadius * Math.cos(Math.toRadians(miDegree)));
        float miEndY = (float) (miRadius * Math.sin(Math.toRadians(miDegree)));
        canvas.drawLine(-miEndX / 8, -miEndY / 8, miEndX, miEndY, miPaint);

        //8、画秒针
        //结束点
        float secRadius = mSecHeight;
        float secEndX = (float) (secRadius * Math.cos(Math.toRadians(secDegree)));
        float secEndY = (float) (secRadius * Math.sin(Math.toRadians(secDegree)));
        canvas.drawLine(-secEndX / 8, -secEndY / 8, secEndX, secEndY, mSecPaint);

        //9、画中心点
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, 10, mPaint);

        postInvalidateDelayed(1000);
    }

    /**
     * 获取当前秒 分 时
     */
    private void setTime() {
        //获取系统当前时间
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        hmsTime = format.format(date);
        String[] split = hmsTime.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        int second = Integer.parseInt(split[2]);
        SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy年MM月dd日");
        ymdTime = formatYMD.format(date);

        //计算秒针的度数
        //360度=60s 1s=6度
        secDegree = (6 * second) - 90;
        //计算分针的度数
        miDegree = (6 * minute + second * 1.0f / 10) - 90;
        //计算时针的度数
        houDegree = (30 * hour + minute * 1.0f / 2) - 90;
    }
}
