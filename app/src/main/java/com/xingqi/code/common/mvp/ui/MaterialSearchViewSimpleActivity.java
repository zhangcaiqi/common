package com.xingqi.code.common.mvp.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.xingqi.code.common.R;
import com.xingqi.code.commonlib.annotation.ToolbarConfig;
import com.xingqi.code.commonlib.base.SimpleActivity;
import com.xingqi.code.commonlib.mvp.BasePresenter;
import com.xingqi.code.commonlib.utils.SnackbarUtils;

import butterknife.BindView;

@ToolbarConfig(title = "MaterialSearchViewSimple")
public class MaterialSearchViewSimpleActivity extends SimpleActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_material_search_view_simple;
    }

    @Override
    public void initData() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                onSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
    }
    private void onSearch(String queryText){
        SnackbarUtils.shortShow(searchView,queryText);
    }

}