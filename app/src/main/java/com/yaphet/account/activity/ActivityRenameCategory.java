package com.yaphet.account.activity;

import android.view.View;
import android.widget.ListView;

import com.yaphet.account.R;
import com.yaphet.account.adapter.CategoryDeleteAdapter;
import com.yaphet.account.adapter.CategoryRenameAdapter;
import com.yaphet.account.application.MyApplication;
import com.yaphet.account.bean.CategoryBean;
import com.yaphet.account.view.TitleBuilder;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/5/8.
 */
public class ActivityRenameCategory extends BaseActivity {

    private TitleBuilder mBuilder;
    private ListView list_category_view;
    private CategoryRenameAdapter mAdapter;
    private DbManager dbManager;
    private List<CategoryBean> list_category;

    @Override
    public int setLayout() {
        return R.layout.activity_rename_category;
    }

    @Override
    public void findView() {
        new TitleBuilder(mContext).setMiddleTitleText("重命名类别").setLeftImageRes(R.mipmap.layout_top_back);
        list_category_view = (ListView) findViewById(R.id.list_category_view);
    }

    @Override
    public void setListener() {
         new TitleBuilder(mContext).setLeftTextOrImageListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ActivityRenameCategory.this.finish();
             }
         });
    }

    @Override
    public void loadData() {
        dbManager = x.getDb(((MyApplication) getApplication()).getDaoConfig());
        try {
            list_category = dbManager.selector(CategoryBean.class).findAll();
            mAdapter = new CategoryRenameAdapter(list_category,mContext,dbManager);
            list_category_view.setAdapter(mAdapter);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
