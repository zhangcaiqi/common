package com.xingqi.code.commonlib.paginate;

import android.os.Build;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class RecyclerPaginate extends Paginate{

    private RecyclerView recyclerView;
    private Paginate.Callbacks callback;
    private  int loadingTriggerThreshold;
    private WrapperAdapter wrapperAdapter;
    private WrapperSpanSizeLookup wrapperSpanSizeLookup;


    public RecyclerPaginate(Builder builder){
        this.recyclerView = builder.recyclerView;
        this.callback = builder.callback;
        this.loadingTriggerThreshold = builder.loadingTriggerThreshold;
        //滚动监听
        recyclerView.addOnScrollListener(mOnScrollListener);
        //包装adapter
        if (builder.displayLoadingItem) {
            // Wrap existing adapter with new adapter that will add loading row
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            wrapperAdapter = new WrapperAdapter(adapter, builder.loadingListItemCreator,builder.noMoreListItemCreator);
            adapter.registerAdapterDataObserver(mDataObserver);
            recyclerView.setAdapter(wrapperAdapter);

            // For GridLayoutManager use separate/customisable span lookup for loading row
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                wrapperSpanSizeLookup = new WrapperSpanSizeLookup(
                        ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanSizeLookup(),
                        builder.loadingListItemSpanLookup,
                        wrapperAdapter);
                ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanSizeLookup(wrapperSpanSizeLookup);
            }
        }
    }
    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            checkEndOffset(); // Each time when list is scrolled check if end of the list is reached
        }
    };
    private final RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            wrapperAdapter.notifyDataSetChanged();
            onAdapterDataChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            wrapperAdapter.notifyItemRangeInserted(positionStart, itemCount);
            onAdapterDataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            wrapperAdapter.notifyItemRangeChanged(positionStart, itemCount);
            onAdapterDataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            wrapperAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
            onAdapterDataChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            wrapperAdapter.notifyItemRangeRemoved(positionStart, itemCount);
            onAdapterDataChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            wrapperAdapter.notifyItemMoved(fromPosition, toPosition);
            onAdapterDataChanged();
        }
    };
    private void onAdapterDataChanged() {
        wrapperAdapter.displayLoadingRow(!callback.hasLoadedAllItems());
        wrapperAdapter.displayNoMoreRow(callback.hasLoadedAllItems());
        checkEndOffset();
    }
    void checkEndOffset() {
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();

        int firstVisibleItemPosition;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            // https://code.google.com/p/android/issues/detail?id=181461
            if (recyclerView.getLayoutManager().getChildCount() > 0) {
                firstVisibleItemPosition = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPositions(null)[0];
            } else {
                firstVisibleItemPosition = 0;
            }
        } else {
            throw new IllegalStateException("LayoutManager needs to subclass LinearLayoutManager or StaggeredGridLayoutManager");
        }

        // Check if end of the list is reached (counting threshold) or if there is no items at all
        if ((totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + loadingTriggerThreshold)
                || totalItemCount == 0) {
            // Call load more only if loading is not currently in progress and if there is more items to load
            if (!callback.isLoading() && !callback.hasLoadedAllItems()) {
                callback.onLoadMore();
            }
        }
    }
    public static class Builder{

        public Builder(RecyclerView recyclerView, Paginate.Callbacks callback) {
            this.recyclerView = recyclerView;
            this.callback = callback;
        }

        private RecyclerView recyclerView;

        private Paginate.Callbacks callback;

        private boolean displayLoadingItem = true;


        private LoadingListItemCreator loadingListItemCreator;

        private NoMoreListItemCreator noMoreListItemCreator;

        private int loadingTriggerThreshold;

        private LoadingListItemSpanLookup loadingListItemSpanLookup;


        public Builder setDisplayLoadingItem(boolean displayLoadingItem) {
            this.displayLoadingItem = displayLoadingItem;
            return this;
        }


        public Builder setLoadingListItemCreator(LoadingListItemCreator loadingListItemCreator) {
            this.loadingListItemCreator = loadingListItemCreator;
            return this;
        }

        public Builder setNoMoreListItemCreator(NoMoreListItemCreator noMoreListItemCreator) {
            this.noMoreListItemCreator = noMoreListItemCreator;
            return this;
        }

        public Builder setLoadingTriggerThreshold(int loadingTriggerThreshold) {
            this.loadingTriggerThreshold = loadingTriggerThreshold;
            return this;
        }

        public Builder setLoadingListItemSpanLookup(LoadingListItemSpanLookup loadingListItemSpanLookup) {
            this.loadingListItemSpanLookup = loadingListItemSpanLookup;
            return this;
        }

        public RecyclerPaginate build(){
            if (recyclerView.getAdapter() == null) {
                throw new IllegalStateException("Adapter needs to be set!");
            }
            if (recyclerView.getLayoutManager() == null) {
                throw new IllegalStateException("LayoutManager needs to be set on the RecyclerView");
            }
            if(null == loadingListItemCreator){
                loadingListItemCreator = LoadingListItemCreator.DEFAULT;
            }
            if(null == noMoreListItemCreator){
                noMoreListItemCreator = NoMoreListItemCreator.DEFAULT;
            }
            if (loadingListItemSpanLookup == null) {
                loadingListItemSpanLookup = new DefaultLoadingListItemSpanLookup(recyclerView.getLayoutManager());
            }
            return new RecyclerPaginate(this);
        }
    }

    @Override
    public void setHasMoreDataToLoad(boolean hasMoreDataToLoad) {
        if (wrapperAdapter != null) {
            wrapperAdapter.displayLoadingRow(hasMoreDataToLoad);
            wrapperAdapter.displayNoMoreRow(!hasMoreDataToLoad);
        }
    }

    @Override
    public void unbind() {
        recyclerView.removeOnScrollListener(mOnScrollListener);   // Remove scroll listener
        if (recyclerView.getAdapter() instanceof WrapperAdapter) {
            WrapperAdapter wrapperAdapter = (WrapperAdapter) recyclerView.getAdapter();
            RecyclerView.Adapter adapter = wrapperAdapter.getWrappedAdapter();
            adapter.unregisterAdapterDataObserver(mDataObserver); // Remove data observer
            recyclerView.setAdapter(adapter);                     // Swap back original adapter
        }
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager && wrapperSpanSizeLookup != null) {
            // Swap back original SpanSizeLookup
            GridLayoutManager.SpanSizeLookup spanSizeLookup = wrapperSpanSizeLookup.getWrappedSpanSizeLookup();
            ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanSizeLookup(spanSizeLookup);
        }
    }

    public static Builder with(RecyclerView recyclerView, Paginate.Callbacks callback){
        return new Builder(recyclerView,callback);
    }

}
