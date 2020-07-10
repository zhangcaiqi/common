package com.xingqi.code.commonlib.complex;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.commonlib.paginate.LoadingListItemCreator;
import com.xingqi.code.commonlib.paginate.NoDataListItemCreator;
import com.xingqi.code.commonlib.paginate.NoMoreListItemCreator;

import java.util.List;

public  class HeaderPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter adapter;
    private List<HeaderItemCreator> headerList;

    private boolean isShowStatusRow ;

    private LoadingListItemCreator loadingListItemCreator;
    private NoMoreListItemCreator noMoreListItemCreator;
    private NoDataListItemCreator noDataListItemCreator;

    private int page = 2;

    private int pageSize;

    private int total;

    private PaginateCallback callback;

    public final static int ITEM_TYPE_HAS_MORE = 1;
    public final static int ITEM_TYPE_NO_MORE = 2;
    public final static int ITEM_TYPE_NO_DATA = 3;
    public volatile int itemType = ITEM_TYPE_HAS_MORE;

    public HeaderPageAdapter(RecyclerView.Adapter adapter,
                             List<HeaderItemCreator> headerList,
                             LoadingListItemCreator loadingListItemCreator,
                             NoMoreListItemCreator noMoreListItemCreator,
                             NoDataListItemCreator noDataListItemCreator,
                             int pageSize,
                             PaginateCallback callback) {
        this.adapter = adapter;
        this.headerList = headerList;
        this.loadingListItemCreator = loadingListItemCreator;
        this.noMoreListItemCreator = noMoreListItemCreator;
        this.noDataListItemCreator = noDataListItemCreator;
        this.pageSize = pageSize;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HeaderItemCreator headerItemCreator = this.findHeader(viewType);
        if (null != headerItemCreator) {
            return headerItemCreator.onCreateViewHolder(parent, viewType);
        }else if(viewType == ITEM_TYPE_NO_DATA ){
            return noDataListItemCreator.onCreateViewHolder(parent,viewType);
        }else if(viewType == ITEM_TYPE_HAS_MORE){
            return loadingListItemCreator.onCreateViewHolder(parent,viewType);
        }else if(viewType == ITEM_TYPE_NO_MORE){
            return noMoreListItemCreator.onCreateViewHolder(parent, viewType);
        }
        return adapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isHeaderRow(position)) {
            findHeaderByPosition(position).onBindViewHolder(holder,position);
        }else if(isStatusRow(position)){
            //无绑定数据
            if(!hasLoadedAllItems()){
                callback.onLoadMore(page,pageSize);
                page++;
            }
        }else{
            adapter.onBindViewHolder(holder,position-headerList.size());
        }

    }

    @Override
    public int getItemCount() {
        int itemCount = headerList.size()  + adapter.getItemCount();
        if(isShowStatusRow){
            itemCount = itemCount + 1;
        }
        return itemCount;
    }
    @Override
    public int getItemViewType(int position) {
        if(isHeaderRow(position)){
            return headerList.get(position).hashCode();
        } else if(isStatusRow(position)){
            return itemType;
        }
        return super.getItemViewType(position);

    }


    public boolean isHeaderRow(int position){
        return position < headerList.size();
    }

    public boolean isStatusRow(int position){
        if(isShowStatusRow){
            int itemCount = getItemCount();
            if(position == itemCount -1){
                return true;
            }
        }
        return false ;
    }
    public HeaderItemCreator findHeaderByPosition(int position){
        return headerList.get(position);
    }

    HeaderItemCreator findHeader(int itemType){
        for(HeaderItemCreator headerItemCreator:headerList){
            if(itemType == headerItemCreator.hashCode()){
                return headerItemCreator;
            }
        }
        return null;
    }

    public boolean hasLoadedAllItems() {
        int pageNum = total / pageSize;
        pageNum = total % pageSize == 0 ? pageNum : pageNum + 1;
        return page > pageNum;
    }

    public void setShowStatusRow(boolean showStatusRow) {
        isShowStatusRow = showStatusRow;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public RecyclerView.Adapter getWrappedAdapter() {
        return adapter;
    }

    public void setItemType(int itemType){
        this.itemType = itemType;
    }

    public void resetPage(){
        this.page = 2;
    }
}
