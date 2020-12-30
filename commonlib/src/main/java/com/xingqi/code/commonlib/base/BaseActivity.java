package com.xingqi.code.commonlib.base;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xingqi.code.commonlib.R;
import com.xingqi.code.commonlib.entity.EventMessage;
import com.xingqi.code.commonlib.mvp.BasePresenter;
import com.xingqi.code.commonlib.rxlifecycle.ActivityLifecycleable;
import com.xingqi.code.commonlib.swipeback.SwipeBackActivityHelper;
import com.xingqi.code.commonlib.swipeback.SwipeBackLayout;
import com.xingqi.code.commonlib.utils.CommonUtils;
import com.xingqi.code.commonlib.utils.ToolbarUtil;
import com.xingqi.code.commonlib.utils.ScreenUtil;
import com.xingqi.code.commonlib.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
        implements IActivity, ActivityLifecycleable , Toolbar.OnMenuItemClickListener {
    protected P mPresenter;
    private SwipeBackActivityHelper mHelper;
    private Unbinder mUnbinder;
    private Toolbar toolbar;
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        //设置侧滑返回
        setUpSwipeBack();
        //使用注解配置页面
        toolbar = ToolbarUtil.initToolbar(this.getClass(),this);
        mPresenter = initPresenter();
        initData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage event) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventMessage event) {
    }

    @Override
    public boolean registerEventBus() {
        return false;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();

    }
    protected abstract P initPresenter();

    private CompositeDisposable mCompositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
    @Override
    protected void onDestroy() {
        release();
        disposable();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;

        super.onDestroy();
    }
    @Override
    public void disposable(){
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
        }
    }
    @Override
    public Context getOwnContext(){
        return this;
    }




    protected boolean isSupportSwipeBack(){
        if(!isRootPage()){
            return true;
        }else{
            return false;
        }
    }
    private void setUpSwipeBack(){
        // 可以调用该方法，设置是否允许滑动退出
        SwipeBackLayout mSwipeBackLayout = mHelper.getSwipeBackLayout();
        mSwipeBackLayout.setEnableGesture(isSupportSwipeBack());
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeSize(ScreenUtil.dip2px(this,30));
    }

    //返回键监听
    @Override
    public void onBackPressed() {
        if(isRootPage()){
            exitAfterTwice();
        }else{
            super.onBackPressed();
            overridePendingTransition(resumeAnim(),finishAnim());
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private long exitTime = 0;
    private void exitAfterTwice(){
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.toast(this,"再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            CommonUtils.exitApp();
        }
    }
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public int finishAnim() {
        return R.anim.page_finish;
    }

    @Override
    public int resumeAnim() {
        return R.anim.page_resume;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return false;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
