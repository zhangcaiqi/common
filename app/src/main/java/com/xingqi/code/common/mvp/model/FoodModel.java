package com.xingqi.code.common.mvp.model;

import com.xingqi.code.common.mvp.contract.FoodContract;
import com.xingqi.code.common.mvp.model.api.Api;
import com.xingqi.code.common.mvp.model.api.service.FoodService;
import com.xingqi.code.common.mvp.model.entity.BasePageResult;
import com.xingqi.code.common.mvp.model.entity.Food;
import com.xingqi.code.common.mvp.model.entity.FoodRequest;
import com.xingqi.code.commonlib.mvp.BaseModel;
import com.xingqi.code.commonlib.mvp.RepositoryManager;

import java.util.List;

import io.reactivex.Observable;

public class FoodModel extends BaseModel implements FoodContract.Model {
    @Override
    public Observable<BasePageResult<Food>> findFoodPage(int page, int pageSize) {
        FoodService foodService = RepositoryManager.getSingleton().obtainRetrofitService(Api.APP_DOMAIN,FoodService.class);
        FoodRequest foodRequest = new FoodRequest();
        foodRequest.page = page;
        foodRequest.rows = pageSize;
        foodRequest.isHot = 1;
        return foodService.findFoodPage(foodRequest);
    }
}
