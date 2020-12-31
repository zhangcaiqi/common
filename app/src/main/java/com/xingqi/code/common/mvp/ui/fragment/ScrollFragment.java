package com.xingqi.code.common.mvp.ui.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xingqi.code.common.R;
import com.xingqi.code.common.adapter.RecyclerPersonAdapter;
import com.xingqi.code.common.data.DataProvider;
import com.xingqi.code.commonlib.base.BaseLazyLoadFragment;
import com.xingqi.code.commonlib.mvp.BasePresenter;

import butterknife.BindView;

public class ScrollFragment extends BaseLazyLoadFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public void lazyLoad() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getOwnContext()));
        RecyclerPersonAdapter adapter = new RecyclerPersonAdapter(DataProvider.getRandomData(20));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scroll;
    }

    @Override
    public void release() {

    }
}
