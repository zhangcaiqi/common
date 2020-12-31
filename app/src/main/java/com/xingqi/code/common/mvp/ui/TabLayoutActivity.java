package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xingqi.code.common.R;
import com.xingqi.code.common.adapter.ViewPagerAdapter;
import com.xingqi.code.common.mvp.ui.fragment.FragmentFive;
import com.xingqi.code.common.mvp.ui.fragment.FragmentFour;
import com.xingqi.code.common.mvp.ui.fragment.FragmentOne;
import com.xingqi.code.common.mvp.ui.fragment.FragmentSix;
import com.xingqi.code.common.mvp.ui.fragment.FragmentThree;
import com.xingqi.code.common.mvp.ui.fragment.FragmentTwo;
import com.xingqi.code.common.mvp.ui.fragment.ScrollFragment;
import com.xingqi.code.commonlib.annotation.ToolbarConfig;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.base.BaseFragment;
import com.xingqi.code.commonlib.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@ToolbarConfig(title = "TabLayout测试")
public class TabLayoutActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
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
    public boolean isRootPage() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    public void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        Fragment fragmentOne = BaseFragment.getInstance(null, FragmentOne.class);
        Fragment fragmentTwo = BaseFragment.getInstance(null, FragmentTwo.class);
        Fragment fragmentThree = BaseFragment.getInstance(null, FragmentThree.class);
        Fragment fragmentFour = BaseFragment.getInstance(null, FragmentFour.class);
        Fragment fragmentFive = BaseFragment.getInstance(null, FragmentFive.class);
        Fragment fragmentSix = BaseFragment.getInstance(null, FragmentSix.class);
        Fragment scrollFragment = BaseFragment.getInstance(null, ScrollFragment.class);

        fragmentList.add(scrollFragment);
        fragmentList.add(fragmentOne);
        fragmentList.add(fragmentTwo);
        fragmentList.add(fragmentThree);
        fragmentList.add(fragmentFour);
        fragmentList.add(fragmentFive);
        fragmentList.add(fragmentSix);

        List<String> tabList = new ArrayList<>();
        tabList.add("tab1");
        tabList.add("tab2");
        tabList.add("tab3");
        tabList.add("tab4");
        tabList.add("tab5");
        tabList.add("tab6");
        tabList.add("tab7");

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList){

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabList.get(position);
            }
        };
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);//必须在 viewPager.setAdapter()之后使用
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void release() {

    }
}