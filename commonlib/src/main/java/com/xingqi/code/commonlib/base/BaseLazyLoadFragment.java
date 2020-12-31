package com.xingqi.code.commonlib.base;



import com.xingqi.code.commonlib.mvp.BasePresenter;



public abstract class BaseLazyLoadFragment<P extends BasePresenter> extends BaseFragment<P> {


    private boolean isDataLoaded; // 数据是否已请求


    /**
     * 保证在initData后触发
     */
    @Override
    public void onResume() {
        super.onResume();
        if(!isDataLoaded){
            lazyLoad();
            isDataLoaded = true;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDataLoaded = false;
    }
    public abstract void lazyLoad();

    @Override
    public void initData() {

    }
}
