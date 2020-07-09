package com.xingqi.code.commonlib.complex;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.commonlib.paginate.LoadingListItemCreator;
import com.xingqi.code.commonlib.paginate.NoDataListItemCreator;
import com.xingqi.code.commonlib.paginate.NoMoreListItemCreator;

import java.util.List;

public  class ComplexAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter adapter;
    private List<HeaderItemCreator> headerList;
    private List<FooterItemCreator> footerList;

    private boolean isShowStatusRow ;

    private int ITEM_TYPE_STATUS_ROW = - 1;


    private LoadingListItemCreator loadingListItemCreator;
    private NoMoreListItemCreator noMoreListItemCreator;
    private NoDataListItemCreator noDataListItemCreator;

    private int page = 1;

    private int pageSize;

    private int total;

    private PaginateCallback callback;


    public ComplexAdapter(RecyclerView.Adapter adapter,
                          List<HeaderItemCreator> headerList,
                          List<FooterItemCreator> footerList,
                          LoadingListItemCreator loadingListItemCreator,
                          NoMoreListItemCreator noMoreListItemCreator,
                          NoDataListItemCreator noDataListItemCreator,
                          int pageSize,
                          PaginateCallback callback) {
        this.adapter = adapter;
        this.headerList = headerList;
        this.footerList = footerList;
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
        }

        FooterItemCreator footerItemCreator = this.findFooter(viewType);
        if(null != footerItemCreator){
            return footerItemCreator.onCreateViewHolder(parent, viewType);
        }
        if(viewType == ITEM_TYPE_STATUS_ROW ){
            if(total == 0){
                return noDataListItemCreator.onCreateViewHolder(parent,viewType);
            }else if(hasLoadedAllItems()){
                return noMoreListItemCreator.onCreateViewHolder(parent, viewType);
            }else{

                return loadingListItemCreator.onCreateViewHolder(parent,viewType);
            }
        }
        return adapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isHeaderRow(position)) {
            findHeaderByPosition(position).onBindViewHolder(holder,position);
        }else if(isFooterRow(position)){
            findFooterByPosition(position).onBindViewHolder(holder,position);
        }else if(isStatusRow(position)){
            //无绑定数据
            if(!hasLoadedAllItems()){
                callback.onLoadMore(page,pageSize);
                page++;
            }

        }else{
            adapter.onBindViewHolder(holder,position);
        }

    }

    @Override
    public int getItemCount() {
        int itemCount = headerList.size() + footerList.size() + adapter.getItemCount();
        if(isShowStatusRow){
            itemCount = itemCount + 1;
        }
        return itemCount;
    }
    @Override
    public int getItemViewType(int position) {
        if(isHeaderRow(position)){
            return headerList.get(position).hashCode();
        } else if(isFooterRow(position)){
            int footerRealPosition = getFooterRealPosition(position);
            return footerList.get(footerRealPosition).hashCode();
        }else if(isStatusRow(position)){
            return ITEM_TYPE_STATUS_ROW;
        }
        return super.getItemViewType(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isHeaderRow(int position){
        return position < headerList.size();
    }
    public boolean isFooterRow(int position){
        return position >= getItemCount() - footerList.size();
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

    protected  FooterItemCreator findFooterByPosition(int position){
        int realPosition = getFooterRealPosition(position);
        return footerList.get(realPosition);
    }
    private int getFooterRealPosition(int position){
        return position - headerList.size() - adapter.getItemCount();
    }
    HeaderItemCreator findHeader(int itemType){
        for(HeaderItemCreator headerItemCreator:headerList){
            if(itemType == headerItemCreator.hashCode()){
                return headerItemCreator;
            }
        }
        return null;
    }
    FooterItemCreator findFooter(int itemType){
        for(FooterItemCreator footerItemCreator:footerList){
            if(itemType == footerItemCreator.hashCode()){
                return footerItemCreator;
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
}
