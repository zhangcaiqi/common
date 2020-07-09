package com.xingqi.code.common.mvp.model.api.service;

import com.xingqi.code.common.mvp.model.entity.BasePageResult;
import com.xingqi.code.common.mvp.model.entity.Food;
import com.xingqi.code.common.mvp.model.entity.FoodRequest;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FoodService {
    @POST("/data/foodPage")
    Observable<BasePageResult<Food>> findFoodPage(@Body FoodRequest foodRequest);
}
