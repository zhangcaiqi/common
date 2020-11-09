package com.xingqi.code.commonlib.mvp;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public interface CommonContract {

    interface Model<T> extends IModel {


        default  Observable<BasePageResult<T>> getPageData(int page, int pageSize, Object... cdt){
            return null;
        }

        default Observable<BaseResult<T>> getData(Object... cdt){
            return null;
        }

        default Observable<BaseResult> addData(String jsonData){
            return null;
        }

        default Observable<BaseResult> addSingleFileData(MultipartBody.Part part, String jsonData){
            return null;
        }

        default Observable<BaseResult> addMultiFileData(MultipartBody.Part[] parts, String jsonData){
            return null;
        }

        default Observable<BaseResult> editData(Object... cdt){ return null;}

        default Observable<BaseResult> deleteData(Object... cdt){ return null;}

    }

    interface View<T> extends IView {
        
        default void appendData(BasePageResult<T> result){
            
        }
        
        default void showData(BaseResult<T> result){
            
        }
        
        default void onAddOver(BaseResult result){
            
        }
        
        default void onEditOver(BaseResult result){
            
        }
        
        default void onDeleteOver(BaseResult result){
            
        }
    }
}
