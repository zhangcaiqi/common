package com.xingqi.code.common.mvp.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xingqi.code.common.R;
import com.xingqi.code.common.adapter.ViewPagerAdapter;
import com.xingqi.code.common.mvp.ui.fragment.BitmapFragment;
import com.xingqi.code.commonlib.annotation.ToolbarConfig;
import com.xingqi.code.commonlib.base.BaseActivity;
import com.xingqi.code.commonlib.base.BaseFragment;
import com.xingqi.code.commonlib.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


@ToolbarConfig(title = "Palette")
public class PaletteActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    public int getLayoutId() {
        return R.layout.activity_palette;
    }
    BitmapFragment fragment1;
    BitmapFragment fragment2;
    BitmapFragment fragment3;
    BitmapFragment fragment4;
    @Override
    public void initData() {
        Bundle bundle1 = new Bundle();
        bundle1.putInt("index",1);
        fragment1 = BaseFragment.getInstance(bundle1,BitmapFragment.class);

        Bundle bundle2 = new Bundle();
        bundle2.putInt("index",2);
        fragment2 = BaseFragment.getInstance(bundle2,BitmapFragment.class);

        Bundle bundle3 = new Bundle();
        bundle3.putInt("index",3);
        fragment3 = BaseFragment.getInstance(bundle3,BitmapFragment.class);

        Bundle bundle4 = new Bundle();
        bundle4.putInt("index",4);
        fragment4 = BaseFragment.getInstance(bundle4,BitmapFragment.class);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);

        List<String> tabList = new ArrayList<>();
        tabList.add("tab1");
        tabList.add("tab2");
        tabList.add("tab3");
        tabList.add("tab4");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList){

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabList.get(position);
            }
        };

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);//必须在 viewPager.setAdapter()之后使用
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        viewPager.addOnPageChangeListener(this);
    }

    boolean isInit = false;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!isInit) {
            isInit = true;
            setPaletteColor(position);
        }
    }

    private void setPaletteColor(int position) {
        Bitmap bitmap = null;
        if (position == 0) {
            bitmap = fragment1.getBitmap();
        } else if (position == 1) {
            bitmap = fragment2.getBitmap();
        } else if (position == 2) {
            bitmap = fragment3.getBitmap();
        } else if (position == 3) {
            bitmap = fragment4.getBitmap();
        }
        if (bitmap == null) {
            return;
        }
        Palette.from(bitmap)
                .generate(palette -> {
                    Palette.Swatch vibrant = palette.getVibrantSwatch();
                    if(null == vibrant){
                        for(Palette.Swatch swatch:palette.getSwatches()){
                            vibrant = swatch;
                            break;
                        }
                    }
                    int rbg = vibrant.getRgb();

                    toolbar.setBackgroundColor(rbg);
                    tabLayout.setBackgroundColor(rbg);

                    tabLayout.setBackgroundColor(rbg);
                    toolbar.setBackgroundColor(rbg);
                    if (Build.VERSION.SDK_INT > 21) {
                        Window window = getWindow();
                        //状态栏改变颜色。
                        int color = changeColor(rbg);
                        window.setStatusBarColor(color);
                    }
                });
    }
    private int changeColor(int rgb) {
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        red = (int) Math.floor(red * (1 - 0.2));
        green = (int) Math.floor(green * (1 - 0.2));
        blue = (int) Math.floor(blue * (1 - 0.2));
        return Color.rgb(red, green, blue);
    }
    @Override
    public void onPageSelected(int position) {
        setPaletteColor(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}