package com.xingqi.code.commonlib.paginate;

import android.widget.AbsListView;

import androidx.recyclerview.widget.RecyclerView;


public abstract class Paginate {

    public interface Callbacks {
        void onLoadMore();

        boolean isLoading();

        boolean hasLoadedAllItems();
    }

    abstract public void setHasMoreDataToLoad(boolean hasMoreDataToLoad);

    abstract public void unbind();

    public static RecyclerPaginate.Builder with(RecyclerView recyclerView, Callbacks callback) {
        return new RecyclerPaginate.Builder(recyclerView, callback);
    }


}