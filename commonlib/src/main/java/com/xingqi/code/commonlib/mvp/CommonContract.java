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

        default void addData(String jsonData){}

        default void addSingleFileData(MultipartBody.Part part, String jsonData){

        }

        default void addMultiFileData(MultipartBody.Part[] parts, String jsonData){

        }

        default void editData(Object... cdt){}

        default void deleteData(Object... cdt){}

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
