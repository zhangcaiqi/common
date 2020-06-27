package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xingqi.code.common.R;
import com.xingqi.code.common.adapter.ViewPagerAdapter;
import com.xingqi.code.common.mvp.ui.fragment.FragmentOne;
import com.xingqi.code.common.mvp.ui.fragment.FragmentThree;
import com.xingqi.code.common.mvp.ui.fragment.FragmentTwo;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.base.BaseFragment;
import com.xingqi.code.commonlib.mvp.BasePresenter;
import com.xingqi.code.commonlib.utils.ScreenUtil;
import com.xingqi.code.commonlib.widget.BottomTabView;
import com.xingqi.code.commonlib.widget.WindowInsetsFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TabActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.bottom_tab)
    BottomTabView bottomTab;

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
        return R.layout.activity_tab;
    }

    @Override
    public void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        Fragment fragmentOne = BaseFragment.getInstance(null, FragmentOne.class);
        Fragment fragmentTwo = BaseFragment.getInstance(null, FragmentTwo.class);
        Fragment fragmentThree = BaseFragment.getInstance(null, FragmentThree.class);
        fragmentList.add(fragmentOne);
        fragmentList.add(fragmentTwo);
        fragmentList.add(fragmentThree);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(this);
        BottomTabView.Tab tab1 = BottomTabView.Tab.with(this)
                .tabHeight(54)
                .text("制作")
                .textSize(12)
                .textMarginBottom(2)
                .textMarginTop(2)
                .imWidth(28)
                .imHeight(28)
                .imgResId(R.drawable.icon_zhizuo)
                .imgResIdSelect(R.drawable.icon_zhizuo_xuanzhong)
                .listener((tab,position)->{
                    viewpager.setCurrentItem(position);
                })
                .build();
        BottomTabView.Tab tab2 = BottomTabView.Tab.with(this)
                .tabHeight(84)
                .text("拍照")
                .textSize(12)
                .imWidth(79)
                .imHeight(79)
                .imgResId(R.drawable.icon_paizhao)
                .imgResIdSelect(R.drawable.icon_paizhao)
                .listener((tab,position)->{
                    viewpager.setCurrentItem(position);
                })
                .build();
        BottomTabView.Tab tab3 = BottomTabView.Tab.with(this)
                .tabHeight(54)
                .text("个人")
                .textSize(12)
                .textMarginBottom(2)
                .textMarginTop(2)
                .imWidth(28)
                .imHeight(28)
                .imgResId(R.drawable.icon_geren)
                .imgResIdSelect(R.drawable.icon_geren_xuanzhong)
                .listener((tab,position)->{
                    viewpager.setCurrentItem(position);
                })
                .build();
        bottomTab.addTab(tab1)
                    .addTab(tab2)
                    .addTab(tab3).setCurrent(0);


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
    public void release() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomTab.setCurrent(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
