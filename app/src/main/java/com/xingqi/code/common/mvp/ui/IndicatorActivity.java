package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xingqi.code.common.R;
import com.xingqi.code.common.adapter.ViewPagerAdapter;
import com.xingqi.code.common.mvp.ui.fragment.FragmentFive;
import com.xingqi.code.common.mvp.ui.fragment.FragmentFour;
import com.xingqi.code.common.mvp.ui.fragment.FragmentOne;
import com.xingqi.code.common.mvp.ui.fragment.FragmentSix;
import com.xingqi.code.common.mvp.ui.fragment.FragmentThree;
import com.xingqi.code.common.mvp.ui.fragment.FragmentTwo;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.base.BaseFragment;
import com.xingqi.code.commonlib.mvp.BasePresenter;
import com.xingqi.code.commonlib.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IndicatorActivity extends BaseActivity {

    @BindView(R.id.viewpager_indicator)
    ViewPagerIndicator viewpagerIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

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
        return R.layout.activity_indicator;
    }

    @Override
    public boolean isRootPage() {
        return false;
    }

    @Override
    public void release() {

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
        fragmentList.add(fragmentOne);
        fragmentList.add(fragmentTwo);
        fragmentList.add(fragmentThree);
        fragmentList.add(fragmentFour);
        fragmentList.add(fragmentFive);
        fragmentList.add(fragmentSix);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewpager.setAdapter(adapter);
        viewpagerIndicator.setViewPager(viewpager);
        viewpagerIndicator.setVisibleCount(3);
        List<String> titles = new ArrayList<String>(){
            {
                add("JAVA");
                add("C++");
                add("ANDROID");
                add("IOS");
                add("PYTHON");
                add("OBJECT-C");
            }
        };
        viewpagerIndicator.setTabTitles(titles);
        viewpager.setCurrentItem(0);
    }


}
