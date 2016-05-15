package com.yaphet.account.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.yaphet.account.R;
import com.yaphet.account.adapter.AccountAdapter;
import com.yaphet.account.application.MyApplication;
import com.yaphet.account.bean.AccountBean;
import com.yaphet.account.view.TitleBuilder;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/8.
 */
public class ActivityDetail extends BaseActivity implements View.OnClickListener{

    private EditText et_month;
    private Button bt_search;
    private DbManager dbManager;
    private List<AccountBean> list_account;
    private ListView list_detail_veiw;
    private List<AccountBean> list_accout_detail;
    private AccountAdapter mAdapter;

    @Override
    public int setLayout() {
        return R.layout.account_detail;
    }

    @Override
    public void findView() {
        et_month = (EditText) findViewById(R.id.et_month);
        bt_search = (Button) findViewById(R.id.bt_search);
        list_detail_veiw = (ListView) findViewById(R.id.list_detail_view);
        new TitleBuilder(mContext).setMiddleTitleText("收支详情").setLeftImageRes(R.mipmap.layout_top_back);
    }

    @Override
    public void setListener() {
      bt_search.setOnClickListener(this);
        new TitleBuilder(mContext).setLeftTextOrImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityDetail.this.finish();
            }
        });
    }

    @Override
    public void loadData() {
        list_account = new ArrayList<>();
        list_accout_detail = new ArrayList<>();
        //得到数据库管理器
        MyApplication applicationContext = (MyApplication)getApplicationContext();
        dbManager = x.getDb(applicationContext.getDaoConfig());
        mAdapter = new AccountAdapter(mContext);
        list_detail_veiw.setAdapter(mAdapter);
        list_detail_veiw.setEmptyView(LayoutInflater.from(mContext).inflate(R.layout.empty,null));
    }

    @Override
    public void onClick(View v) {
        list_accout_detail.clear();
        if(v.getId()==R.id.bt_search){
            String month = et_month.getText().toString();
            if(TextUtils.isEmpty(month)){
                showToast("请输入月份");
                return;
            }
            try {
                list_account = dbManager.selector(AccountBean.class).findAll();
                for(int i = 0;i<list_account.size();i++){
                    AccountBean accountBean = list_account.get(i);
                    if(month.equals(accountBean.getTime().substring(5,7))){
                        Log.e("info", "执行到了这里");
                        list_accout_detail.add(accountBean);
                    }else{
                        continue;
                    }
                }
                if(list_account.size()>0) {
                    mAdapter.setList(list_accout_detail);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }
}
