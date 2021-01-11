package com.xingqi.code.common.mvp.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.xingqi.code.common.R;
import com.xingqi.code.commonlib.base.BaseLazyLoadFragment;
import com.xingqi.code.commonlib.mvp.BasePresenter;

import butterknife.BindView;

public class BitmapFragment extends BaseLazyLoadFragment {
    Bitmap bitmap;
    @BindView(R.id.iv_bitmap)
    ImageView ivBitmap;

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getArguments();
        int index = bundle.getInt("index");

        if (index == 1) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_1);
        } else if (index == 2) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_2);
        } else if (index == 3) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_3);
        } else if (index == 4) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_4);
        }

        ivBitmap.setImageBitmap(bitmap);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bitmap;
    }

    @Override
    public void release() {

    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}
