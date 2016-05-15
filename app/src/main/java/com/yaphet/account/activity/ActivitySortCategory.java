package com.yaphet.account.activity;

import android.database.Cursor;
import android.view.View;
import android.widget.ListView;

import com.yaphet.account.R;
import com.yaphet.account.adapter.CategoryDeleteAdapter;
import com.yaphet.account.adapter.CategorySortAdapter;
import com.yaphet.account.application.MyApplication;
import com.yaphet.account.bean.CategoryBean;
import com.yaphet.account.view.TitleBuilder;

import org.xutils.DbManager;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/8.
 */
public class ActivitySortCategory extends BaseActivity{

    private TitleBuilder mBuilder;
    private ListView list_category_view;
    private DbManager dbManager;
    private List<CategoryBean> list_category;
    private List<CategoryBean> list_cate;
    private CategorySortAdapter mAdapter;
    private String sort;
    private String sql;

    @Override
    public int setLayout() {
        return R.layout.activity_delete_category;
    }

    @Override
    public void findView() {
        new TitleBuilder(mContext).setMiddleTitleText("排序类别").setLeftImageRes(R.mipmap.layout_top_back);
        list_category_view = (ListView) findViewById(R.id.list_category_view);
    }

    @Override
    public void setListener() {
        new TitleBuilder(mContext).setLeftTextOrImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySortCategory.this.finish();
            }
        });
    }

    @Override
    public void loadData() {
        sort = getIntent().getStringExtra("sort");
        dbManager = x.getDb(((MyApplication) getApplication()).getDaoConfig());
        list_cate = new ArrayList<>();
        try {
            if("1".equals(sort)){
                 sql = "select * from category order by id asc";
                 sortCategoryAsc();
            }else if("2".equals(sort)){
                sql = "select * from category order by id desc";
                sortCategoryDesc();
            }
            list_category = dbManager.selector(CategoryBean.class).findAll();
            mAdapter = new CategorySortAdapter(list_category,mContext,dbManager);
            list_category_view.setAdapter(mAdapter);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void sortCategoryAsc(){
        try {
            list_category = dbManager.selector(CategoryBean.class).findAll();
            dbManager.delete(CategoryBean.class);
            if(list_category.get(0).getId() > list_category.get(list_category.size()/2).getId()) {
                for (int i = 0; i < list_category.size(); i++) {
                    dbManager.save(list_category.get(i));
                }
            }else{
                for(int i= list_category.size()-1;i>=0;i--){
                    dbManager.save(list_category.get(i));
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void sortCategoryDesc(){
        try {
            list_category = dbManager.selector(CategoryBean.class).findAll();
            dbManager.delete(CategoryBean.class);    //删除表中的所有数据

            if(list_category.get(0).getId() < list_category.get(list_category.size()/2).getId()) {
                for(int i= list_category.size()-1;i>=0;i--){
                    dbManager.save(list_category.get(i));
                }
            }else{
                for(int i =0;i<list_category.size();i++){
                    dbManager.save(list_category.get(i));
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
