package com.yaphet.account.activity;

import android.view.View;
import android.widget.ListView;

import com.yaphet.account.R;
import com.yaphet.account.adapter.CategoryDeleteAdapter;
import com.yaphet.account.application.MyApplication;
import com.yaphet.account.bean.CategoryBean;
import com.yaphet.account.view.TitleBuilder;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/5/7.
 */
public class ActivityDeleteCategory extends BaseActivity{

    private TitleBuilder mBuilder;
    private ListView list_category_view;
    private DbManager dbManager;
    private List<CategoryBean> list_category;
    private CategoryDeleteAdapter mAdapter;

    @Override
    public int setLayout() {
        return R.layout.activity_delete_category;
    }

    @Override
    public void findView() {
        new TitleBuilder(mContext).setMiddleTitleText("删除类别").setLeftImageRes(R.mipmap.layout_top_back);
        list_category_view = (ListView) findViewById(R.id.list_category_view);
    }

    @Override
    public void setListener() {
     new TitleBuilder(mContext).setLeftTextOrImageListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            ActivityDeleteCategory.this.finish();
         }
     });
    }

    @Override
    public void loadData() {
       dbManager = x.getDb(((MyApplication) getApplication()).getDaoConfig());
        try {
            list_category = dbManager.selector(CategoryBean.class).findAll();
            mAdapter = new CategoryDeleteAdapter(list_category,mContext,dbManager);
            list_category_view.setAdapter(mAdapter);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
