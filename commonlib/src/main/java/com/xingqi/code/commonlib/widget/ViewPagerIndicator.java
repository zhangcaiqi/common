package com.xingqi.code.commonlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.xingqi.code.commonlib.R;
import com.xingqi.code.commonlib.utils.ScreenUtil;

import java.util.List;

public class ViewPagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {

    private Context context;
    //屏幕中可见的tab数量
    private int mVisibleCount = 4;

    private Paint mPaint;
    //指示器颜色
    private int indicatorColor = Color.BLACK;
    //tab标题文字颜色
    private int textColor = Color.BLACK;
    //tab标题文字大小
    private int textSize = 12;
    //tab标题放大比例
    private float textSizeRatio = 0.8f;
    //tab宽度
    private int mTabWidth;
    //指示器默认的宽度
    private float minWidthRatio = 1.0f/8;
    //指示器可扩展伸长mTabWidth的30%,再进行平移40%，最后缩小到minWidthRatio的尺寸
    private float stretchRatio = 0.3f;
    //指示器高度
    private int indicatorHeight = 8;
    //指示器和标题之间的间隙
    private int indicatorSpacing = 10;

    private ViewPager viewPager;


    private Canvas mCanvas;
    //viewpager回调中的的position
    private int position;
    //viewpager回调中的offset
    private float positionOffset;
    //指示器默认再tab中央时的偏移量
    private float centerOffset;
    //指示器宽度
    private float mIndicatorWidth;
    //viewpager回调中的position,上述position会随着offset改变，用于计算向左还是向右滑动
    private int selectedPosition;

    private int maxMeasureHeight;


    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        if(null != attrs){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.ViewPagerIndicator);
            indicatorColor = typedArray.getColor(R.styleable.ViewPagerIndicator_indicatorColor,indicatorColor);
            mVisibleCount = typedArray.getInt(R.styleable.ViewPagerIndicator_visibleCount,mVisibleCount);
            textColor = typedArray.getColor(R.styleable.ViewPagerIndicator_textColor,textColor);
            indicatorHeight = typedArray.getDimensionPixelOffset(R.styleable.ViewPagerIndicator_indicatorHeight,indicatorHeight);
            indicatorSpacing = typedArray.getDimensionPixelOffset(R.styleable.ViewPagerIndicator_indicatorSpacing,indicatorSpacing);
            minWidthRatio = typedArray.getFloat(R.styleable.ViewPagerIndicator_minWidthRatio,minWidthRatio);
            textSize = typedArray.getDimensionPixelSize(R.styleable.ViewPagerIndicator_textSize,textSize);
            textSizeRatio = typedArray.getFloat(R.styleable.ViewPagerIndicator_textSizeRatio,textSizeRatio);
            mTabWidth = typedArray.getDimensionPixelOffset(R.styleable.ViewPagerIndicator_tabWidth,mTabWidth);
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(indicatorColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(indicatorHeight);

        setBaselineAligned(false);
        setGravity(Gravity.BOTTOM);

        setPadding(0,0,0,indicatorSpacing);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        maxMeasureHeight = Math.max(maxMeasureHeight,getMeasuredHeight());
        int newHeight = indicatorHeight  + maxMeasureHeight;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(newHeight,mode);
        setMeasuredDimension(widthMeasureSpec,newHeightMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        this.mCanvas = canvas;
        drawIndicator();
        scrollAdapt();
        textAdapt();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerOffset = ((1-minWidthRatio)*mTabWidth)/2;
        mIndicatorWidth = minWidthRatio * mTabWidth;

    }

    private int getScreenWidth(){
        return  ScreenUtil.getScreenWidth(context);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childrenCount = getChildCount();
        if(childrenCount == 0){
            return;
        }
        mTabWidth = mTabWidth == 0 ? (getScreenWidth()/mVisibleCount) : mTabWidth;
        for(int i=0; i<childrenCount;i++){
            TextView view = (TextView) getChildAt(i);
            view.setTextSize(textSize);
            view.setTextColor(textColor);
            LinearLayout.LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.width = mTabWidth;
            view.setLayoutParams(layoutParams);
        }
        setItemClickEvent();
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
    }

    private void setItemClickEvent() {
        int count = getChildCount();
        for(int i=0;i<count;i++){
            View view = getChildAt(i);
            int finalI = i;
            view.setOnClickListener((v)->{
                viewPager.setCurrentItem(finalI);
            });
        }
    }
    private void drawIndicator(){
//        if(stretchRatio >= 0.5f){
//            throw new RuntimeException("非法stretchRatio");
//        }
        float indicatorOffsetX;
        float startX = 0f,endX;
        if(positionOffset == 0){
            indicatorOffsetX = centerOffset
                    + position*mTabWidth;
            endX =  mIndicatorWidth;
        }else if(positionOffset <= stretchRatio){
            //放大
            indicatorOffsetX = centerOffset
                    + position*mTabWidth;
            endX =  mIndicatorWidth + positionOffset*mTabWidth;
        }else if(positionOffset > stretchRatio && positionOffset <= (1-stretchRatio)){
            //平移
            float translationPercent = 1 - 2*stretchRatio;
            indicatorOffsetX = centerOffset
                    + (positionOffset-stretchRatio)*((1-stretchRatio)*mTabWidth)/translationPercent
                    + position*mTabWidth;
            endX =  mIndicatorWidth + stretchRatio*mTabWidth;
        }else {
            //缩小
            indicatorOffsetX = 2*mTabWidth
                    - centerOffset
                    - (mIndicatorWidth
                    + stretchRatio*mTabWidth)
                    + position*mTabWidth;;
            endX = mIndicatorWidth + stretchRatio*mTabWidth;
            startX = (positionOffset - 1 + stretchRatio)*mTabWidth;
        }

        mCanvas.save();
        mCanvas.translate(indicatorOffsetX,0);

        int y =  maxMeasureHeight ;
        mCanvas.drawLine(startX,y,endX,y,mPaint);
        mCanvas.restore();
    }

    private void scrollAdapt(){
        // 容器滚动，当移动到倒数最后一个的时候，开始滚动
         if (positionOffset > 0
                 && position >= (mVisibleCount - 2)
                 && getChildCount() > mVisibleCount){
             if (mVisibleCount != 1){
                 this.scrollTo((position - (mVisibleCount - 2)) * mTabWidth + (int) (mTabWidth * positionOffset), 0);
             } else {
                 this.scrollTo(position * mTabWidth + (int) (mTabWidth * positionOffset), 0);
             }
         }
    }

    private void textAdapt(){
        TextView current = (TextView) getChildAt(selectedPosition);
        float changedSize = textSizeRatio*textSize;
        float selectedSize = textSize*(1+textSizeRatio);
        if(positionOffset !=0){
            if(position >= selectedPosition){//向左滑动
                TextView next = (TextView) getChildAt(selectedPosition+1);
                float currentTextSize = selectedSize - positionOffset*changedSize;
                current.setTextSize(currentTextSize);
                float nextSize = textSize + positionOffset*changedSize;
                next.setTextSize(nextSize);
                if(positionOffset > 0.5){
                    next.setTypeface(null, Typeface.BOLD);
                    current.setTypeface(null,Typeface.NORMAL);
                }else{
                    current.setTypeface(null, Typeface.BOLD);
                    next.setTypeface(null,Typeface.NORMAL);
                }
            }else{//向右滑动
                TextView previous = (TextView) getChildAt(selectedPosition-1);
                float currentTextSize = selectedSize - (1-positionOffset)*changedSize;
                float previousSize = textSize + (1-positionOffset)*changedSize;
                current.setTextSize(currentTextSize);
                previous.setTextSize(previousSize);
                if(positionOffset < 0.5){
                    previous.setTypeface(null, Typeface.BOLD);
                    current.setTypeface(null,Typeface.NORMAL);
                }else{
                    current.setTypeface(null, Typeface.BOLD);
                    previous.setTypeface(null,Typeface.NORMAL);
                }
            }
        }else{
            //选中时
            current.setTextSize(selectedSize);
            current.setTypeface(null, Typeface.BOLD);
        }



    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.position = position;
        this.positionOffset = positionOffset;
        invalidate();
        if(null != pageChangeListener){
            pageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        this.positionOffset = 0f;
        this.selectedPosition = position;
        invalidate();
        if(null != pageChangeListener){
            pageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(null != pageChangeListener){
            pageChangeListener.onPageScrollStateChanged(state);
        }

    }

    public void setVisibleCount(int visibleCount){
        this.mVisibleCount = visibleCount;
    }

    public void setTabTitles(List<String> titles){
        if(null != titles && titles.size() > 0){
            removeAllViews();
            for(String title:titles){
                addView(generateTab(title));
            }
            setItemClickEvent();
        }

    }
    private View generateTab(String title){
        mTabWidth = mTabWidth == 0 ? (getScreenWidth()/mVisibleCount) : mTabWidth;
        TextView view = new TextView(context);
        view.setTextSize(textSize);
        view.setTextColor(textColor);
        view.setText(title);
        view.setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        layoutParams.width = mTabWidth;
        view.setLayoutParams(layoutParams);
        return view;
    }

    public interface PageChangeListener{

        void onPageScrolled(int position, float positionOffset,  int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }
    private PageChangeListener pageChangeListener;

    public void setPageChangeListener(PageChangeListener pageChangeListener) {
        this.pageChangeListener = pageChangeListener;
    }
}
