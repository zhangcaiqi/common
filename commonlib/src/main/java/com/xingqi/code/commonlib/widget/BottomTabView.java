package com.xingqi.code.commonlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingqi.code.commonlib.R;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.utils.ScreenUtil;

public class BottomTabView extends RelativeLayout implements View.OnClickListener{
    private static final String tabLayoutId = "tabLayoutId";
    private static final String imageIdPrefix = "bottomTabImage";
    private int backgroundColor = Color.WHITE;
    private int tabLayoutHeight = 50;
    private LinearLayout linearLayout;
    public BottomTabView(Context context) {
        this(context,null);
    }

    public BottomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.BottomTabView);
        backgroundColor = typedArray.getColor(R.styleable.BottomTabView_backgroundColor, backgroundColor);
        tabLayoutHeight = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tabLayoutHeight,tabLayoutHeight);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RelativeLayout.LayoutParams  layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        setLayoutParams(layoutParams);
        setClipChildren(false);
        setBackgroundColor(backgroundColor);

        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.BOTTOM);
        linearLayout.setClipChildren(false);

        linearLayout.setId(tabLayoutId.hashCode());
        addView(linearLayout,LayoutParams.MATCH_PARENT,tabLayoutHeight);
    }

    public BottomTabView addTab(Tab tab){
        linearLayout.addView(tab);
        tab.setOnClickListener(this);
        return this;
    }

    @Override
    public void onClick(View v) {
        Tab selectTab = (Tab) v;
        int count = linearLayout.getChildCount();
        for(int i=0;i<count;i++){
            Tab tab = (Tab) linearLayout.getChildAt(i);
            tab.unSelect();
        }
        int position = linearLayout.indexOfChild(selectTab);
        selectTab.select(position);
    }

    public void setCurrent(int position){
        Tab currentTab = (Tab) linearLayout.getChildAt(position);
        int count = linearLayout.getChildCount();
        for(int i=0;i<count;i++){
            Tab tab = (Tab) linearLayout.getChildAt(i);
            tab.unSelect();
        }
        currentTab.select(position);
    }
    public static class Tab extends RelativeLayout{
        private TextView textView;
        private ImageView imageView;
        private Builder builder;
        public Tab(Context context,Builder builder) {
            super(context);
            this.builder = builder;
            generate();
        }

        private void generate(){
            Context context = builder.context;
            String textId = builder.text;
            int tabHeight = ScreenUtil.dip2px(context,builder.mTabItemHeight);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,tabHeight);
            lp.gravity = Gravity.BOTTOM;
            lp.weight = 1;
            setLayoutParams(lp);
            RelativeLayout.LayoutParams textLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textView = new TextView(builder.context);
            textView.setId(textId.hashCode());
            textView.setText(builder.text);
            textView.setTextColor(builder.textColor);
            textView.setTextSize(builder.textSize);
            textLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            textLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            textLp.topMargin = ScreenUtil.dip2px(context,builder.textViewMarginTop);
            textLp.bottomMargin = ScreenUtil.dip2px(context,builder.textViewMarginBottom);
            textView.setLayoutParams(textLp);
            addView(textView);
            imageView = new ImageView(builder.context);
            String imageViewId = imageIdPrefix + builder.text;
            imageView.setId(imageViewId.hashCode());
            int imageWidth = ScreenUtil.dip2px(context,builder.imWidth);
            int imageHeight = ScreenUtil.dip2px(context,builder.imHeight);
            RelativeLayout.LayoutParams imLp = new RelativeLayout.LayoutParams(imageWidth,imageHeight);
            imLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            imLp.addRule(RelativeLayout.ABOVE,textId.hashCode());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(builder.imgResId);
            imageView.setLayoutParams(imLp);
            addView(imageView);
        }

        private void select(int position){
            textView.setTextColor(builder.textColorSelect);
            imageView.setImageResource(builder.imgResIdSelect);
            OnTabSelectListener listener = builder.listener;
            if(null != listener){
                listener.onTabSelect(this,position);
            }
        }

        private void unSelect(){
            textView.setTextColor(builder.textColor);
            imageView.setImageResource(builder.imgResId);
        }
        public static Builder with(Context context){
            return new Builder(context);
        }
        public static class Builder{
            private Context context;

            public Builder(Context context) {
                this.context = context;
            }

            private int mTabItemHeight = 50;
            private String text;
            private int textColor = Color.BLACK;
            private int textColorSelect = Color.RED;
            private int textSize = 12;

            private int textViewMarginBottom ;
            private int textViewMarginTop ;
            private int imWidth;
            private int imHeight;
            private int imgResId;
            private int imgResIdSelect;

            private OnTabSelectListener listener;

            public Builder tabHeight(int mTabItemHeight) {
                this.mTabItemHeight = mTabItemHeight;
                return this;
            }

            public Builder text(String text) {
                this.text = text;
                return this;
            }

            public Builder textColor(int textColor) {
                this.textColor = textColor;
                return this;
            }

            public Builder textSize(int textSize) {
                this.textSize = textSize;
                return this;
            }

            public Builder textMarginBottom(int textViewMarginBottom) {
                this.textViewMarginBottom = textViewMarginBottom;
                return this;
            }

            public Builder textMarginTop(int textViewMarginTop) {
                this.textViewMarginTop = textViewMarginTop;
                return this;
            }

            public Builder imWidth(int imWidth) {
                this.imWidth = imWidth;
                return this;
            }

            public Builder imHeight(int imHeight) {
                this.imHeight = imHeight;
                return this;
            }

            public Builder imgResId(int imgResId) {
                this.imgResId = imgResId;
                return this;
            }

            public Builder textColorSelect(int textColorSelect) {
                this.textColorSelect = textColorSelect;
                return this;
            }

            public Builder imgResIdSelect(int imgResIdSelect) {
                this.imgResIdSelect = imgResIdSelect;
                return this;
            }

            public Builder listener(OnTabSelectListener listener) {
                this.listener = listener;
                return this;
            }

            public Tab build(){
                return new Tab(context,this);
            }
        }

    }

    public interface OnTabSelectListener{
        void onTabSelect(Tab tab,int index);
    }
}
