package com.yaphet.account.application;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.yaphet.account.bean.CategoryBean;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class MyApplication extends Application{

    private DbManager.DaoConfig daoConfig;
    private  String  dbPath;
    private  static int DBVERSION = 1;   //数据库版本
    private  static List<CategoryBean> list_category;

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Xutils3.0
        x.Ext.init(this);
        //开启debug模式
        x.Ext.setDebug(true);
        dbPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //创建数据库名称
        daoConfig = new DbManager.DaoConfig().setDbName("account_db").setDbVersion(DBVERSION).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                // TODO 这块做数据库版本迭代
                Log.e("info", "数据库版本更新了");
            }
        });
        try {
            list_category = x.getDb(daoConfig).selector(CategoryBean.class).findAll();
            if(list_category==null){
                generateData();
            }else {
                //TODO  不做处理
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void generateData() {
        DbManager db = x.getDb(daoConfig);
        CategoryBean cate1 = new CategoryBean();
        cate1.setTypeName("收入");
        CategoryBean cate2 = new CategoryBean();
        cate2.setTypeName("信用卡还款");
        CategoryBean cate3 = new CategoryBean();
        cate3.setTypeName("餐饮");
        CategoryBean cate4 = new CategoryBean();
        cate4.setTypeName("食品");
        CategoryBean cate5 = new CategoryBean();
        cate5.setTypeName("饮料");
        CategoryBean cate6 = new CategoryBean();
        cate6.setTypeName("日用品");
        CategoryBean cate7 = new CategoryBean();
        cate7.setTypeName("交通");
        CategoryBean cate8 = new CategoryBean();
        cate8.setTypeName("旅游");
        CategoryBean cate9 = new CategoryBean();
        cate9.setTypeName("电器");
        CategoryBean cate10 = new CategoryBean();
        cate10.setTypeName("房租");
        CategoryBean cate11 = new CategoryBean();
        cate11.setTypeName("水电费");
        CategoryBean cate12 = new CategoryBean();
        cate12.setTypeName("燃气费");
        CategoryBean cate13 = new CategoryBean();
        cate13.setTypeName("网费");
        //将这些类别保存到数据库中
        try {

            db.save(cate1);
            db.save(cate2);
            db.save(cate3);
            db.save(cate4);
            db.save(cate5);
            db.save(cate6);
            db.save(cate7);
            db.save(cate8);
            db.save(cate9);
            db.save(cate10);
            db.save(cate11);
            db.save(cate12);
            db.save(cate13);
        //    list_category = db.selector(CategoryBean.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}
