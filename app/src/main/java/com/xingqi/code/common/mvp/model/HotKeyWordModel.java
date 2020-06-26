package com.xingqi.code.common.mvp.model;


import com.xingqi.code.common.mvp.contract.HotKeyWordContract;
import com.xingqi.code.common.mvp.model.api.Api;
import com.xingqi.code.common.mvp.model.api.service.HotKeyWordService;
import com.xingqi.code.common.mvp.model.entity.HotKeyWord;
import com.xingqi.code.commonlib.mvp.BaseModel;
import com.xingqi.code.commonlib.mvp.RepositoryManager;

import java.util.List;

import io.reactivex.Observable;

public class HotKeyWordModel extends BaseModel implements HotKeyWordContract.Model {
    @Override
    public Observable<List<HotKeyWord>> getHotKeyWordList() {
        HotKeyWordService service = RepositoryManager.getSingleton().obtainRetrofitService(Api.APP_DOMAIN,HotKeyWordService.class);
        return service.getHotKeyWordList();
    }
}
