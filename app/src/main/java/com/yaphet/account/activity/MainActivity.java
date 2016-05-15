package com.yaphet.account.activity;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yaphet.account.R;
import com.yaphet.account.adapter.CategoryAdapter;
import com.yaphet.account.application.MyApplication;
import com.yaphet.account.bean.CategoryBean;
import com.yaphet.account.fragment.AccountFragment;
import com.yaphet.account.fragment.SettingFragment;
import com.yaphet.account.fragment.StatisticFragment;
import com.yaphet.account.view.TitleBuilder;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gary on 2016/4/24.
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mGroup;
    private RadioButton radio_account,radio_statics,radio_setting;
    private AccountFragment accountFragment;
    private SettingFragment settingFragment;
    private StatisticFragment statisticFragment;
    private android.support.v4.app.FragmentManager mManager;



    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void findView() {
        initTitles();
        mGroup = (RadioGroup) findViewById(R.id.radio_group);
        radio_account = (RadioButton) findViewById(R.id.radio_account);
        radio_statics = (RadioButton) findViewById(R.id.radio_statics);
        radio_setting = (RadioButton) findViewById(R.id.radio_setting);
    }

    @Override
    public void setListener() {
         mGroup.setOnCheckedChangeListener(this);
//        radio_account.setOnCheckedChangeListener((RadioButton.OnCheckedChangeListener)this);
//        radio_statics.setOnCheckedChangeListener((RadioButton.OnCheckedChangeListener)this);
//        radio_setting.setOnCheckedChangeListener((RadioButton.OnCheckedChangeListener)this);
    }

    @Override
    public void loadData() {
        mManager = getSupportFragmentManager();
        accountFragment = new AccountFragment();
        mManager.beginTransaction().add(R.id.container,accountFragment).show(accountFragment).commit();

    }

    private void initTitles(){
        new TitleBuilder(this).setMiddleTitleText("E记账").setVisibilty(View.GONE);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
       switch (checkedId){
           case R.id.radio_account:
               if(statisticFragment!=null){
                   mManager.beginTransaction().hide(statisticFragment).commit();
               }
               if(settingFragment!=null){
                   mManager.beginTransaction().hide(settingFragment).commit();
               }
               mManager.beginTransaction().show(accountFragment).commit();
               break;
           case R.id.radio_statics:
               if(statisticFragment==null){
                   statisticFragment = new StatisticFragment();
                   mManager.beginTransaction().add(R.id.container,statisticFragment).show(statisticFragment).commit();
               }else{
                   mManager.beginTransaction().show(statisticFragment).commit();
               }
               if(accountFragment!=null){
                   mManager.beginTransaction().hide(accountFragment).commit();
               }
               if(settingFragment!=null){
                   mManager.beginTransaction().hide(settingFragment).commit();
               }
               break;
           case R.id.radio_setting:
               if(settingFragment==null){
                   settingFragment  =  new SettingFragment();
                   mManager.beginTransaction().add(R.id.container,settingFragment).show(settingFragment).commit();
               }else{
                   mManager.beginTransaction().show(settingFragment).commit();
               }
               if(accountFragment!=null){
                   mManager.beginTransaction().hide(accountFragment).commit();
               }
               if(statisticFragment!=null){
                   mManager.beginTransaction().hide(statisticFragment).commit();
               }
               break;
           default: break;
       }
    }
}
