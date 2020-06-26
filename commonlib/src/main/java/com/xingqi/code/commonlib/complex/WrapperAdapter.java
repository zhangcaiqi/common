package com.xingqi.code.commonlib.complex;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private RecyclerView.Adapter realAdapter;
    private List<HeaderItemCreator> headerList;
    private List<FooterItemCreator> footerList;

    public WrapperAdapter(RecyclerView.Adapter adapter, List<HeaderItemCreator> headerList,List<FooterItemCreator> footerList){
        this.realAdapter = adapter;
        this.headerList = headerList;
        this.footerList = footerList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HeaderItemCreator headerItemCreator = findHeader(viewType);
        if(null != headerItemCreator){
            return headerItemCreator.onCreateViewHolder(parent,viewType);
        }
        FooterItemCreator footerItemCreator = findFooter(viewType);
        if(null != footerItemCreator){
            footerItemCreator.onCreateViewHolder(parent,viewType);
        }
        return realAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(isHeaderRow(position)){
            HeaderItemCreator headerItemCreator = headerList.get(position);
            headerItemCreator.onBindViewHolder(holder,position);
        } else if(isFooterRow(position)){
            int footerRealPosition = getFooterRealPosition(position);
            FooterItemCreator footerItemCreator = footerList.get(footerRealPosition);
            footerItemCreator.onBindViewHolder(holder,position);
        }else{
            realAdapter.onBindViewHolder(holder,position-headerList.size());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(isHeaderRow(position)){
            return headerList.get(position).hashCode();
        }
        if(isFooterRow(position)){
            int footerRealPosition = getFooterRealPosition(position);
            return footerList.get(footerRealPosition).hashCode();
        }
        return super.getItemViewType(position);

    }

    @Override
    public long getItemId(int position) {
        if(isHeaderRow(position)){
            return headerList.get(position).hashCode();
        }
        if(isFooterRow(position)){
            int footerRealPosition = getFooterRealPosition(position);
            return footerList.get(footerRealPosition).hashCode();
        }
        return position;
    }

    @Override
    public int getItemCount() {
        return headerList.size() + footerList.size() + realAdapter.getItemCount();
    }
    boolean isHeaderRow(int position){
        return position < headerList.size();
    }
    boolean isFooterRow(int position){
        return position >= getItemCount() - footerList.size();
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

    private int getFooterRealPosition(int position){
        return position - headerList.size() - realAdapter.getItemCount();
    }

    HeaderItemCreator findHeaderByPosition(int position){
        return headerList.get(position);
    }

    FooterItemCreator findFooterByPosition(int position){
        int realPosition = getFooterRealPosition(position);
        return footerList.get(realPosition);
    }
}
