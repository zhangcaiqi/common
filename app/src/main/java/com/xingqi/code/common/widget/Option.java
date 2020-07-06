package com.xingqi.code.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.xingqi.code.common.R;


public class Option extends ConstraintLayout {
    //属性
    private int titleLeftMargin = 10;
    private Drawable leftIcon;
    private Drawable rightIcon;
    private String title = "";
    private String subTitle = "";
    private String optionValue = "";

    //控件
    private ImageView ivLeft;

    private LinearLayout titleWrap;

    private ImageView ivRight;

    private TextView tvTitle;

    private TextView tvSubTitle;

    private TextView tvValue;

    private int underLineColor = Color.GRAY;


    public Option(Context context) {
        this(context,null);
    }

    public Option(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_option,this);
        ivLeft = view.findViewById(R.id.iv_left);
        titleWrap = view.findViewById(R.id.title_wrap);
        tvTitle = view.findViewById(R.id.tv_title);
        tvSubTitle = view.findViewById(R.id.tv_sub_title);

        ivRight = view.findViewById(R.id.iv_right);
        tvValue = view.findViewById(R.id.tv_value);


        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.Option);
        titleLeftMargin = typedArray.getDimensionPixelSize(R.styleable.Option_titleLeftMargin,titleLeftMargin);
        title = typedArray.getString(R.styleable.Option_title);
        subTitle = typedArray.getString(R.styleable.Option_subTitle);
        optionValue = typedArray.getString(R.styleable.Option_optionValue);
        underLineColor = typedArray.getColor(R.styleable.Option_underLineColor,underLineColor);
        leftIcon = typedArray.getDrawable(R.styleable.Option_leftIcon);
        rightIcon = typedArray.getDrawable(R.styleable.Option_rightIcon);


        if(null != leftIcon){
            ivLeft.setImageDrawable(leftIcon);
        }
        LayoutParams layoutParams = (LayoutParams) titleWrap.getLayoutParams();
        layoutParams.leftMargin = titleLeftMargin;
        titleWrap.setLayoutParams(layoutParams);

        tvTitle.setText(title);
        if(null != subTitle){
            tvSubTitle.setText(subTitle);
            tvSubTitle.setVisibility(VISIBLE);
        }
        if(null != rightIcon){
            ivRight.setImageDrawable(rightIcon);
            ivRight.setVisibility(VISIBLE);
        }

        tvValue.setText(optionValue);
        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,measureHeight(heightMeasureSpec));

    }

    private int measureHeight(int heightMeasureSpec){
        int height = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if(mode == MeasureSpec.EXACTLY){
            height = size;
        }else{

        }
        return height;
    }
}
