package com.xingqi.code.commonlib.paginate;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

class WrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_LOADING = Integer.MAX_VALUE - 50; // Magic
    private static final int ITEM_VIEW_TYPE_NO_MORE = Integer.MAX_VALUE - 100;

    private final RecyclerView.Adapter wrappedAdapter;
    private final LoadingListItemCreator loadingListItemCreator;
    private final NoMoreListItemCreator noMoreListItemCreator;
    private boolean displayLoadingRow = true;
    private boolean displayNoMoreRow = false;

    public WrapperAdapter(RecyclerView.Adapter adapter,
                          LoadingListItemCreator creator,
                          NoMoreListItemCreator noMoreListItemCreator) {
        this.wrappedAdapter = adapter;
        this.loadingListItemCreator = creator;
        this.noMoreListItemCreator = noMoreListItemCreator;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_LOADING) {
            return loadingListItemCreator.onCreateViewHolder(parent, viewType);
        }else if(viewType == ITEM_VIEW_TYPE_NO_MORE){
            return noMoreListItemCreator.onCreateViewHolder(parent,viewType);
        } else{
            return wrappedAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isLoadingRow(position)) {
            loadingListItemCreator.onBindViewHolder(holder, position);
        } else if(isNoMoreRow(position)){
            noMoreListItemCreator.onBindViewHolder(holder,position);
        }else {
            wrappedAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return (displayLoadingRow || displayNoMoreRow)? wrappedAdapter.getItemCount() + 1 : wrappedAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(isLoadingRow(position))return ITEM_VIEW_TYPE_LOADING;
        if(isNoMoreRow(position))return ITEM_VIEW_TYPE_NO_MORE;
        return wrappedAdapter.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return isLoadingRow(position) || isNoMoreRow(position) ? RecyclerView.NO_ID : wrappedAdapter.getItemId(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
        wrappedAdapter.setHasStableIds(hasStableIds);
    }

    public RecyclerView.Adapter getWrappedAdapter() {
        return wrappedAdapter;
    }

    boolean isDisplayLoadingRow() {
        return displayLoadingRow;
    }

    void displayLoadingRow(boolean displayLoadingRow) {
        if (this.displayLoadingRow != displayLoadingRow) {
            this.displayLoadingRow = displayLoadingRow;
            notifyDataSetChanged();
        }
    }

    void displayNoMoreRow(boolean displayNoMoreRow){
        if(this.displayNoMoreRow != displayNoMoreRow){
            this.displayNoMoreRow = displayNoMoreRow;
            notifyDataSetChanged();
        }
    }

    boolean isLoadingRow(int position) {
        return displayLoadingRow && position == getLoadingRowPosition();
    }

    private int getLoadingRowPosition() {
        return displayLoadingRow ? getItemCount() - 1 : -1;
    }

    boolean isNoMoreRow(int position){
        return displayNoMoreRow && position == getNoMoreRowPosition();
    }

    private int getNoMoreRowPosition(){
        return displayNoMoreRow ? getItemCount() - 1 : -1;
    }
}