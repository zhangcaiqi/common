package com.xingqi.code.commonlib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.trello.rxlifecycle2.android.FragmentEvent;
import com.xingqi.code.commonlib.R;
import com.xingqi.code.commonlib.annotation.ToolbarConfig;
import com.xingqi.code.commonlib.entity.EventMessage;
import com.xingqi.code.commonlib.mvp.BasePresenter;
import com.xingqi.code.commonlib.rxlifecycle.FragmentLifecycleable;
import com.xingqi.code.commonlib.utils.ToolbarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IFragment, FragmentLifecycleable {
    protected P mPresenter;
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Toolbar toolbar;
    public static <T extends BaseFragment> T getInstance(Bundle bundle,Class<T> fragmentClass){
        try {
            T t = fragmentClass.newInstance();
            t.setArguments(bundle);
            return t;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CompositeDisposable mCompositeDisposable;

    protected void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = initPresenter();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(isSetAppBarStyle()){
            Activity activity = getActivity();
            if(activity instanceof AppCompatActivity){
                toolbar = ToolbarUtil.initToolbar(this.getClass(), (AppCompatActivity) activity);
            }
            ToolbarConfig toolbarConfig = ToolbarUtil.getToolbarConfig(this.getClass());
            if(null != toolbarConfig){
                boolean hasOptionMenu = toolbarConfig.hasOptionMenu();
                setHasOptionsMenu(hasOptionMenu);
            }
        }
    }

    protected abstract P initPresenter();
    @Override
    public void onDestroy() {
        disposable();
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
        return getActivity();
    }



    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public boolean isSetAppBarStyle() {
        return false;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
