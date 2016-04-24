package com.yaphet.account.activity;

import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.yaphet.account.R;
import com.yaphet.account.fragment.AccountFragment;
import com.yaphet.account.fragment.SettingFragment;
import com.yaphet.account.fragment.StatisticFragment;
import com.yaphet.account.view.TitleBuilder;

/**
 * Created by Gary on 2016/4/24.
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private TitleBuilder mBuilder;
    private RadioGroup mGroup;
    private AccountFragment accountFragment;
    private SettingFragment settingFragment;
    private StatisticFragment statisticFragment;
    private android.support.v4.app.FragmentManager mManager;
    //添加了东西

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void findView() {
        mBuilder = new TitleBuilder(mContext);
        mGroup = (RadioGroup) findViewById(R.id.radio_group);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void loadData() {
        mBuilder.setMiddleTitleText("记账");
        mManager = getSupportFragmentManager();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
