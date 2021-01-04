package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xingqi.code.common.R;
import com.xingqi.code.common.adapter.ViewPagerAdapter;
import com.xingqi.code.common.mvp.ui.fragment.FragmentOne;
import com.xingqi.code.common.mvp.ui.fragment.FragmentThree;
import com.xingqi.code.common.mvp.ui.fragment.FragmentTwo;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.base.BaseFragment;
import com.xingqi.code.commonlib.mvp.BasePresenter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BottomNavigationActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bottom_navigation;
    }

    @Override
    public void initData() {

        FragmentOne fragmentOne = BaseFragment.getInstance(null, FragmentOne.class);
        FragmentTwo fragmentTwo = BaseFragment.getInstance(null,FragmentTwo.class);
        FragmentThree fragmentThree = BaseFragment.getInstance(null,FragmentThree.class);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(fragmentOne);
        fragmentList.add(fragmentTwo);
        fragmentList.add(fragmentThree);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    bottomNavigation.setSelectedItemId(R.id.tab_people);
                }else if(position == 1){
                    bottomNavigation.setSelectedItemId(R.id.tab_manage);
                }else if(position == 2){
                    bottomNavigation.setSelectedItemId(R.id.tab_picture);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tab_people:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tab_manage:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tab_picture:
                viewPager.setCurrentItem(2);
                break;
        }
        return true;
    }
}