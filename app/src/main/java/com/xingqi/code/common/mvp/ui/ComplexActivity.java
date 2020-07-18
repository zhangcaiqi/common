package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.common.R;
import com.xingqi.code.common.adapter.ComplexAdapter;
import com.xingqi.code.common.complex.FooterOne;
import com.xingqi.code.common.complex.HeaderOne;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.complex.HeaderPage;
import com.xingqi.code.commonlib.complex.RecyclerComplex;
import com.xingqi.code.commonlib.mvp.BasePresenter;

import butterknife.BindView;

public class ComplexActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public boolean isRootPage() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_complex;
    }

    @Override
    public void release() {

    }

    @Override
    public void initData() {
        ComplexAdapter adapter = new ComplexAdapter(this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setAdapter(adapter);
        RecyclerComplex.with(recyclerView)
                .addHeader(new HeaderOne(this))
                .addHeader(new HeaderOne(this))
                .addFooter(new FooterOne(this))
                .addFooter(new FooterOne(this))
                .addFooter(new FooterOne(this))
//                .setFullSpan(false)
                .build();
    }


    @Override
    public boolean hasToolbar() {
        return false;
    }

    @Override
    public String toolbarTitle() {
        return null;
    }

    @Override
    public int statusBarColor() {
        return R.color.colorPrimary;
    }
}
