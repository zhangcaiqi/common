package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.common.R;
import com.xingqi.code.common.adapter.FoodAdapter;
import com.xingqi.code.common.mvp.contract.FoodContract;
import com.xingqi.code.common.mvp.model.FoodModel;
import com.xingqi.code.common.mvp.model.entity.Food;
import com.xingqi.code.common.mvp.presenter.FoodPresenter;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.complex.PaginateCallback;
import com.xingqi.code.commonlib.complex.HeaderPage;

import java.util.List;

import butterknife.BindView;

public class FoodListActivity extends BaseActivity<FoodPresenter> implements FoodContract.View
                                    , PaginateCallback {

    @BindView(R.id.rv_food)
    RecyclerView rvFood;
    FoodAdapter foodAdapter;
    HeaderPage<Food> complex;
    int pageSize = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected FoodPresenter initPresenter() {
        return new FoodPresenter(new FoodModel(),this);
    }

    @Override
    public boolean isRootPage() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_food_list;
    }

    @Override
    public void initData() {
        foodAdapter = new FoodAdapter(this);
        rvFood.setLayoutManager(new LinearLayoutManager(this));
        rvFood.setAdapter(foodAdapter);
        complex = HeaderPage.with(rvFood,this)
                .pageSize(pageSize)
                .build();
        mPresenter.findFoodPage(true,1,pageSize);
    }


    @Override
    public void release() {

    }

    @Override
    public void appendData(boolean pullToRefresh,List<Food> foodList) {
        complex.appendData(pullToRefresh,589,foodList);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onLoadMore(int page, int pageSize) {
        mPresenter.findFoodPage(false,page,pageSize);
    }
}
