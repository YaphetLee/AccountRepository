package com.yaphet.account.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yaphet.account.R;
import com.yaphet.account.activity.ActivityAnaylse;
import com.yaphet.account.activity.ActivityDetail;
import com.yaphet.account.application.MyApplication;
import com.yaphet.account.bean.AccountBean;
import com.yaphet.account.bean.CategoryBean;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.Date;
import java.util.List;

/**
 * Created by Gary on 2016/4/24.
 */
public class StatisticFragment extends Fragment implements View.OnClickListener{

    private View statics_view;
    private TextView tv_avai_money,tv_income_money,tv_expend_money;
    private Button btn_income_detail,btn_expend_anaylse;
    private DbManager dbManager;
    private List<AccountBean> list_account;
    private Date mDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        statics_view = inflater.inflate(R.layout.frag_statics, container, false);
        findView(statics_view);
        setlistener();
        mDate = new Date();
        dbManager = x.getDb(((MyApplication)getActivity().getApplicationContext()).getDaoConfig());
        loadData();
        return statics_view;
    }

    private void loadData(){

        try {
            list_account = dbManager.selector(AccountBean.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        double income_money = 0;
        double expend_money = 0;

        double income_money_all = 0;
        double expend_money_all = 0;

        if(list_account!=null) {
            for (int i = 0; i < list_account.size(); i++) {
                AccountBean accountBean = list_account.get(i);
                //TODO   当月的现金支付总额
                if ("收入".equals(accountBean.getCategory())) {

                    income_money_all = income_money_all + Double.parseDouble(accountBean.getMoney());

                    int month = Integer.parseInt(accountBean.getTime().substring(5, 7));
                    if (month == (mDate.getMonth() + 1)) {
                        income_money = income_money + Double.parseDouble(accountBean.getMoney());
                    }
                }

                if ("现金支付".equals(accountBean.getPayType())) {

                    expend_money_all = expend_money_all + Double.parseDouble(accountBean.getMoney());

                    int month = Integer.parseInt(accountBean.getTime().substring(5, 7));
                    if (month == (mDate.getMonth() + 1)) {
                        expend_money = expend_money + Double.parseDouble(accountBean.getMoney());
                    }
                }
            }
        }
            tv_income_money.setText(income_money + "");
            tv_expend_money.setText(expend_money + "");
            tv_avai_money.setText((income_money_all - expend_money_all) + "");

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            loadData();
        }
    }

    private void findView(View view){
        tv_avai_money = (TextView) view.findViewById(R.id.tv_avai_money);
        tv_income_money = (TextView) view.findViewById(R.id.tv_income_money);
        tv_expend_money = (TextView) view.findViewById(R.id.tv_expend_money);
        btn_income_detail = (Button) view.findViewById(R.id.btn_income_detail);
        btn_expend_anaylse = (Button) view.findViewById(R.id.btn_expend_ansyalse);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.btn_income_detail:
                 Intent intent = new Intent(getActivity(), ActivityDetail.class);
                 getActivity().startActivity(intent);
                 break;
             case R.id.btn_expend_ansyalse:
                 Intent intent1 = new Intent(getActivity(), ActivityAnaylse.class);
                 getActivity().startActivity(intent1);
                 break;
         }
    }

    private void setlistener(){
        btn_income_detail.setOnClickListener(this);
        btn_expend_anaylse.setOnClickListener(this);
    }
}
