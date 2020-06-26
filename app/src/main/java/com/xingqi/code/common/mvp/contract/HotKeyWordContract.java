package com.xingqi.code.common.mvp.contract;


import com.xingqi.code.common.mvp.model.entity.HotKeyWord;
import com.xingqi.code.commonlib.mvp.IModel;
import com.xingqi.code.commonlib.mvp.IView;

import java.util.List;

import io.reactivex.Observable;

public interface HotKeyWordContract {

    interface View extends IView {
        void showKeyWord(List<HotKeyWord> hotKeyWordList);
    }
    interface Model extends IModel {
        Observable<List<HotKeyWord>> getHotKeyWordList();
    }
}
