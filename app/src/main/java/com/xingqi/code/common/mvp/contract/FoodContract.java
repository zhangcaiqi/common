package com.xingqi.code.common.mvp.contract;

import com.xingqi.code.common.mvp.model.entity.BasePageResult;
import com.xingqi.code.common.mvp.model.entity.Food;
import com.xingqi.code.commonlib.mvp.IModel;
import com.xingqi.code.commonlib.mvp.IView;

import java.util.List;

import io.reactivex.Observable;

public interface FoodContract {

    interface View extends IView{
        void appendData(boolean pullToRefresh,List<Food> foodList);
    }

    interface Model extends IModel {
        Observable<BasePageResult<Food>> findFoodPage(int page, int pageSize);
    }
}
