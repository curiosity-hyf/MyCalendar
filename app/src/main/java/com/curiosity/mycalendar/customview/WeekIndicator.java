package com.curiosity.mycalendar.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curiosity.mycalendar.R;

import java.util.List;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-1
 * E-mail : curiooosity.h@gmail.com
 */

public class WeekIndicator extends LinearLayout {
    private Paint mPaint;
    private Path mPath;

    private int mInitTranslationX;
    private int mTranslationX;

    private int mTriangleWidth;
    private int mTriangleHeight;
    private static final double RADIO_TRIANGLEWIDTH = 1 / 6F;
    private static int TAB_MAX_HEIGHT;

    // 可见 tab 数量
    private int mVisibleCount;
    // 默认可见 tab 数量
    private static final int DEFAULT_VISIBLE_COUNT = 4;

    private static int windowWidth;

    public WeekIndicator(Context context) {
        this(context, null);
    }

    public WeekIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WeekIndicator);
        mVisibleCount = array.getInt(R.styleable.WeekIndicator_tab_count, DEFAULT_VISIBLE_COUNT);

        if (mVisibleCount < 0) {
            mVisibleCount = DEFAULT_VISIBLE_COUNT;
        }

        array.recycle();

        // 获取屏幕宽度
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        windowWidth = metrics.widthPixels;

        // 设置画笔属性
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ffffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TAB_MAX_HEIGHT = getHeight() / 4;
        mTriangleWidth = (int) (w / mVisibleCount * RADIO_TRIANGLEWIDTH);
        mTriangleHeight = Math.min(TAB_MAX_HEIGHT, mTriangleWidth / 2);
        mInitTranslationX = 0;

//        initTriangle();
    }

    /**
     * 初始化 三角形
     */
    private void initTriangle() {
        mPath.reset();
        mPath.moveTo(mIndicatorX+15, 0);
        mPath.lineTo(mIndicatorX + mIndicatorWidth-15, 0);
        mPath.lineTo(mIndicatorX + mIndicatorWidth, 10);
        mPath.lineTo(mIndicatorX, 10);
        mPath.close();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        initTriangle();
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight()-10);

        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    private int tabWidth;
    private int mIndicatorX;
    private int mIndicatorPosition;
    private int mIndicatorWidth;

    /**
     * 指示器滚动
     *
     * @param position
     * @param positionOffset
     */
    public void scroll(int position, float positionOffset) {
        tabWidth = windowWidth / mVisibleCount;

        if(positionOffset < 0.25) {
            mIndicatorX = (int) (tabWidth*(3.0/8) * positionOffset/0.25);
            mIndicatorWidth = tabWidth + (int) (tabWidth*(1.0/8) * positionOffset/0.25) - mIndicatorX;
        } else if(positionOffset < 0.5) {
            mIndicatorX = (int) (tabWidth*(3.0/8)) + (int) (tabWidth*(1.0/8) * (positionOffset-0.25)/0.25);
            mIndicatorWidth = (int) (tabWidth*(9.0/8)) + (int) (tabWidth*(3.0/8) * (positionOffset-0.25)/0.25) - mIndicatorX;
        } else if(positionOffset < 0.75) {
            mIndicatorX = (int) (tabWidth*(4.0/8)) + (int) (tabWidth*(1.0/8) * (positionOffset-0.5)/0.25);
            mIndicatorWidth = (int) (tabWidth*(12.0/8)) + (int) (tabWidth*(3.0/8) * (positionOffset-0.5)/0.25) - mIndicatorX;
        } else {
            mIndicatorX = (int) (tabWidth*(5.0/8)) + (int) (tabWidth*(3.0/8) * (positionOffset-0.75)/0.25);
            mIndicatorWidth = (int) (tabWidth*(15.0/8)) + (int) (tabWidth*(1.0/8) * (positionOffset-0.75)/0.25) - mIndicatorX;
        }

        mTranslationX = tabWidth * position;

        if (getChildCount() > mVisibleCount && position >= mVisibleCount - 2 && positionOffset > 0) {
            if (mVisibleCount != 1) {
                this.scrollTo((int) ((position - (mVisibleCount - 2)) * tabWidth + tabWidth * positionOffset), 0);
            } else {
                this.scrollTo((int) (tabWidth * position + tabWidth * positionOffset), 0);
            }
        }
        invalidate(); // 重绘
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount == 0)
            return;

        for (int i = 0; i < childCount; ++i) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.weight = 0;
            layoutParams.width = windowWidth / mVisibleCount;
            view.setLayoutParams(layoutParams);
        }
    }


    private List<String> mList;

    public void setTabItem(List<String> list) {
        if (list != null && list.size() > 0) {
            removeAllViews();
            mList = list;

            for (int i = 0; i < list.size(); ++i) {
                addView(generateTab(list.get(i)));
            }
        }
    }

    /**
     * 生成 tab
     *
     * @param s
     * @return
     */
    private View generateTab(String s) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams ll = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ll.width = windowWidth / mVisibleCount;
        tv.setText(s);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv.setTextColor(Color.parseColor("#77ffffff"));
        tv.setLayoutParams(ll);
        return tv;
    }
}
