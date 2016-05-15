package com.yaphet.account.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yaphet.account.R;
import com.yaphet.account.adapter.CategoryAdapter;
import com.yaphet.account.application.MyApplication;
import com.yaphet.account.bean.AccountBean;
import com.yaphet.account.bean.CategoryBean;
import com.yaphet.account.utils.ShareUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gary on 2016/4/24.
 */
public class AccountFragment extends Fragment implements View.OnClickListener{

    private View accountView;
    private EditText et_money,et_place;
    private Button btn_account,btn_category,btn_time,bt_payType;
    private TextView tv_credit_pay_money,tv_month_pay_money,tv_repay,tv_credit_edu,tv_month_edu;
    private LinearLayout linear_pay_type;
    private PopupWindow mPopupWindow,mTimeWindow;
    private List<CategoryBean> list_category;
    private List<AccountBean> list_account;
    private Context mContext;
    private CategoryAdapter mAdapter;
    private View popupView;
    private ListView listView;
    private DatePicker mDatePicker;
    private AccountBean accountBean; //个人账户
    private  DbManager db;
    private AlertDialog mDialog;
    private Date mDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accountView = inflater.inflate(R.layout.frag_account,container,false);
        findView(accountView);
        loadData();
        setListener();
        return accountView;

        //隐藏软键盘
        // imm.hideSoftInputFromWindow(editView.getWindowToken(), 0);
    }

    private void findView(View view){
        et_money = (EditText) view.findViewById(R.id.et_money);
        btn_time = (Button) view.findViewById(R.id.btn_time);
        bt_payType = (Button) view.findViewById(R.id.bt_pay_type);
        tv_credit_pay_money = (TextView) view.findViewById(R.id.tv_credit_pay_money);
        tv_month_pay_money = (TextView) view.findViewById(R.id.tv_month_pay_money);
        tv_credit_edu = (TextView) view.findViewById(R.id.tv_credit_pay_money_edu);
        tv_month_edu = (TextView) view.findViewById(R.id.tv_month_pay_money_edu);
        tv_repay = (TextView) view.findViewById(R.id.repay_money);
        et_place = (EditText) view.findViewById(R.id.et_place);
        btn_account = (Button) view.findViewById(R.id.btn_jizhang);
        btn_category = (Button) view.findViewById(R.id.btn_category);
        linear_pay_type = (LinearLayout) view.findViewById(R.id.linear_pay_type);
    }

    private void loadData(){
//        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(et_money.getWindowToken(),0);
        list_account = new ArrayList<>();
        mDate = new Date();
        //得到数据库管理器
        MyApplication applicationContext = (MyApplication)getActivity().getApplicationContext();
        db = x.getDb(applicationContext.getDaoConfig());
        mContext = getActivity();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            Log.e("---->>","显示里这里");
            try {
                list_account = db.selector(AccountBean.class).findAll();
                double credit_money = 0;
                double month_money = 0;
                double credit_money_last = 0;
                double credit_repay_money = 0;
                if(list_account!=null) {
                    for (int i = 0; i < list_account.size(); i++){
                        AccountBean accountBean = list_account.get(i);
                        //TODO   上个月的信用卡支出总额
                        if("信用卡支付".equals(accountBean.getPayType())){
                            int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                            if(month ==( mDate.getMonth())) {
                                credit_money_last = credit_money_last + Double.parseDouble(accountBean.getMoney());
                            }
                        }
                        //TODO   当月的信用卡支付总额
                        if("信用卡支付".equals(accountBean.getPayType())){
                            int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                            if(month ==( mDate.getMonth()+1)) {
                                credit_money = credit_money + Double.parseDouble(accountBean.getMoney());
                            }
                        }else if("现金支付".equals(accountBean.getPayType())) {
                            int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                            if(month ==( mDate.getMonth()+1)) {
                                month_money = month_money + Double.parseDouble(accountBean.getMoney());
                            }
                        }

                        if("信用卡还款".equals(accountBean.getCategory())){
                            int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                            if(month ==( mDate.getMonth()+1)) {
                                credit_repay_money = credit_repay_money + Double.parseDouble(accountBean.getMoney());
                            }
                        }

                    }
                    tv_repay.setText((credit_money_last-credit_repay_money)+"");
                    if(credit_money > Double.parseDouble(ShareUtils.getCardMoney(mContext))){
                        tv_credit_pay_money.setTextColor(Color.RED);
                    }else{
                        tv_credit_pay_money.setTextColor(Color.BLACK);
                    }

                    if(month_money > Double.parseDouble(ShareUtils.getMonthMoney(mContext))){
                        tv_month_pay_money.setTextColor(Color.RED);
                    }else{
                        tv_month_pay_money.setTextColor(Color.BLACK);
                    }
                    tv_credit_pay_money.setText(credit_money+"");
                    tv_month_pay_money.setText(month_money+"");
                }else{
                    tv_month_pay_money.setText("0");
                    tv_credit_pay_money.setText("0");
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        generateData();
        mAdapter = new CategoryAdapter(list_category,mContext);
        tv_credit_edu.setText("/" + ShareUtils.getCardMoney(mContext));
        tv_month_edu.setText("/" + ShareUtils.getMonthMoney(mContext));
        try {
            list_account = db.selector(AccountBean.class).findAll();
            double credit_money = 0;
            double month_money = 0;
            double credit_money_last = 0;
            double credit_repay_money = 0;
            if(list_account!=null) {
                for (int i = 0; i < list_account.size(); i++){
                    AccountBean accountBean = list_account.get(i);
                    //TODO   上个月的信用卡支出总额
                    if("信用卡支付".equals(accountBean.getPayType())){
                        int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                        if(month ==( mDate.getMonth())) {
                            credit_money_last = credit_money_last + Double.parseDouble(accountBean.getMoney());
                        }
                    }
                    //TODO   当月的信用卡支付总额
                    if("信用卡支付".equals(accountBean.getPayType())){
                        int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                        if(month ==( mDate.getMonth()+1)) {
                            credit_money = credit_money + Double.parseDouble(accountBean.getMoney());
                        }
                    }else if("现金支付".equals(accountBean.getPayType())) {
                        int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                        if(month ==( mDate.getMonth()+1)) {
                            month_money = month_money + Double.parseDouble(accountBean.getMoney());
                        }
                    }

                    if("信用卡还款".equals(accountBean.getCategory())){
                        int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                        if(month ==( mDate.getMonth()+1)) {
                            credit_repay_money = credit_repay_money + Double.parseDouble(accountBean.getMoney());
                        }
                    }

                }
                tv_repay.setText((credit_money_last-credit_repay_money)+"");
                if(credit_money > Double.parseDouble(ShareUtils.getCardMoney(mContext))){
                    tv_credit_pay_money.setTextColor(Color.RED);
                }else{
                    tv_credit_pay_money.setTextColor(Color.BLACK);
                }

                if(month_money > Double.parseDouble(ShareUtils.getMonthMoney(mContext))){
                    tv_month_pay_money.setTextColor(Color.RED);
                }else{
                    tv_month_pay_money.setTextColor(Color.BLACK);
                }
                tv_credit_pay_money.setText(credit_money+"");
                tv_month_pay_money.setText(month_money+"");
            }else{
                tv_month_pay_money.setText("0");
                tv_credit_pay_money.setText("0");
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    private void setListener(){
        btn_account.setOnClickListener(this);
        btn_category.setOnClickListener(this);
        btn_time.setOnClickListener(this);
        bt_payType.setOnClickListener(this);
        btn_category.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String payType = s.toString().trim();
                if("收入".equals(payType)){
                    linear_pay_type.setVisibility(View.GONE);
                }else{
                    linear_pay_type.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void showPopoupWindow(){
        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        int width = (metrics.widthPixels * 5)/ 6;
        int height = (metrics.heightPixels ) / 2;
        popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_category,null);
        listView = (ListView) popupView.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btn_category.setText(list_category.get(position).getTypeName());
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
            }
        });
        //accountView.setBackgroundColor(Color.parseColor("#ee000000"));
        mPopupWindow = new PopupWindow(popupView,width,height);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
     //   mPopupWindow.setBackgroundDrawable(mContext.getDrawable(R.mipmap.ic_launcher));
      //  mPopupWindow.showAsDropDown(btn_category, -50, 0, Gravity.CENTER);
        mPopupWindow.showAtLocation(btn_category, Gravity.CENTER,0,0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                accountView.setBackgroundColor(Color.parseColor("#770000ff"));
            }
        });

    }


    private void generateData() {
        db  = x.getDb(((MyApplication) getActivity().getApplication()).getDaoConfig());
        try {
            list_category = db.selector(CategoryBean.class).findAll();
            Log.e("info",list_category.size()+"");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_category:
                showPopoupWindow();
                break;
            case R.id.btn_jizhang:
                if(TextUtils.isEmpty(et_money.getText().toString())){
                    Toast.makeText(mContext, "输入金额不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("选择类别".equals(btn_category.getText().toString())){
                    Toast.makeText(mContext, "请输入选择类别", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(btn_time.getText().toString())){
                    Toast.makeText(mContext, "时间不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(et_place.getText().toString())){
                    Toast.makeText(mContext, "输入地点不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                 if(linear_pay_type.getVisibility()==View.VISIBLE){
                     if("选择支付方式".equals(( bt_payType.getText().toString()))){
                         Toast.makeText(mContext, "请选择支付方式", Toast.LENGTH_SHORT).show();
                         return;
                     }
                 }
                account();
                break;
            case R.id.btn_time:
                showTimePicker();
                break;
            case R.id.bt_pay_type:
                showPayDialog();
                break;
            default: break;
        }
    }
    //显示alertDialog
    private void showPayDialog(){
        //自定义布局
        View layout = getActivity().getLayoutInflater().inflate(R.layout.paytype_settings,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        mDialog = builder.create();
        View payView = LayoutInflater.from(getActivity()).inflate(R.layout.paytype_settings,null);
        final TextView tv_credit_pay = (TextView) payView.findViewById(R.id.tv_credit_pay);
        final TextView tv_money_pay = (TextView) payView.findViewById(R.id.tv_money_pay);
        tv_credit_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_payType.setText(tv_credit_pay.getText().toString());
                mDialog.dismiss();
            }
        });

        tv_money_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_payType.setText(tv_money_pay.getText().toString());
                mDialog.dismiss();
            }
        });
         mDialog.setView(payView);
         mDialog.show();
        }

    private void showTimePicker(){
        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = (metrics.heightPixels * 4) / 5;
        View timeView = LayoutInflater.from(mContext).inflate(R.layout.popup_time,null);
        mTimeWindow = new PopupWindow(timeView,width,height);

        mDatePicker = (DatePicker) timeView.findViewById(R.id.datePicker);
        Button btn_sure = (Button) timeView.findViewById(R.id.btn_sure);
        Button btn_cancel = (Button) timeView.findViewById(R.id.btn_cancel);

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeWindow.dismiss();
                String month =( mDatePicker.getMonth() + 1) + "";
                Log.e("------------>>>",month);
                if(String.valueOf(month).length()==2){
                    btn_time.setText(mDatePicker.getYear()+"-"+month+"-"+mDatePicker.getDayOfMonth());
                }else {
                    btn_time.setText(mDatePicker.getYear() + "-" + "0" + month + "-" + mDatePicker.getDayOfMonth());
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimeWindow.isShowing()){
                    mTimeWindow.dismiss();
                }
            }
        });
        mTimeWindow.showAtLocation(btn_time,10,10,Gravity.CENTER);
    }
   //记账
    private void account(){
        accountBean = new AccountBean();
        String money = et_money.getText().toString().trim();
        String category = btn_category.getText().toString().trim();
        String time = btn_time.getText().toString().trim();
        String place = et_place.getText().toString().trim();
        if("收入".equals(category)){
            accountBean.setPayType(null);
        }else{
            String payType = bt_payType.getText().toString().trim();
            accountBean.setPayType(payType);
        }
        accountBean.setMoney(money);
        accountBean.setCategory(category);
        accountBean.setTime(time);
        accountBean.setPlace(place);

/*            try {
            db.save(accountBean);
            Toast.makeText(getActivity(), "记账成功", Toast.LENGTH_SHORT).show();
                list_account = db.selector(AccountBean.class).findAll();
                double credit_money = 0;
                double month_money = 0;
                if(list_account!=null) {
                    for (int i = 0; i < list_account.size(); i++){
                        AccountBean accountBean = list_account.get(i);
                        if("信用卡支付".equals(accountBean.getPayType())){
                            int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                            if(month ==( mDate.getMonth() +1)) {
                                credit_money = credit_money + Double.parseDouble(accountBean.getMoney());
                            }
                        }else if("现金支付".equals(accountBean.getPayType())) {
                            int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                            if(month ==( mDate.getMonth()+1)) {
                                month_money = month_money + Double.parseDouble(accountBean.getMoney());
                            }
                        }
                    }
                    tv_credit_pay_money.setText(credit_money+"");
                    tv_month_pay_money.setText(month_money+"");

                    et_money.setText("");
                    btn_category.setText("选择类别");
                    et_place.setText("");
                    btn_time.setText("输入时间");
                    if(credit_money > Double.parseDouble(ShareUtils.getCardMoney(mContext))){
                        tv_credit_pay_money.setTextColor(Color.RED);
                    }else{
                        tv_credit_pay_money.setTextColor(Color.BLACK);
                    }
                    if(month_money > Double.parseDouble(ShareUtils.getMonthMoney(mContext))){
                        tv_month_pay_money.setTextColor(Color.RED);
                    }else{
                        tv_month_pay_money.setTextColor(Color.BLACK);
                    }

                }else{
                    tv_credit_pay_money.setText("0");
                    tv_month_pay_money.setText("0");
                }
            } catch (DbException e) {
                e.printStackTrace();
            }*/




        try {
            db.save(accountBean);
            Toast.makeText(getActivity(), "记账成功", Toast.LENGTH_SHORT).show();
            et_money.setText("");
            btn_category.setText("选择类别");
            et_place.setText("");
            btn_time.setText("输入时间");
            list_account = db.selector(AccountBean.class).findAll();
            double credit_money = 0;
            double month_money = 0;
            double credit_money_last = 0;
            double credit_repay_money = 0;
            if(list_account!=null) {
                for (int i = 0; i < list_account.size(); i++){
                    AccountBean accountBean = list_account.get(i);
                    //TODO   上个月的信用卡支出总额
                    if("信用卡支付".equals(accountBean.getPayType())){
                        int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                        if(month ==( mDate.getMonth())) {
                            credit_money_last = credit_money_last + Double.parseDouble(accountBean.getMoney());
                        }
                    }
                    //TODO   当月的信用卡支付总额
                    if("信用卡支付".equals(accountBean.getPayType())){
                        int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                        if(month ==( mDate.getMonth()+1)) {
                            credit_money = credit_money + Double.parseDouble(accountBean.getMoney());
                        }
                    }else if("现金支付".equals(accountBean.getPayType())) {
                        int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                        if(month ==( mDate.getMonth()+1)) {
                            month_money = month_money + Double.parseDouble(accountBean.getMoney());
                        }
                    }

                    if("信用卡还款".equals(accountBean.getCategory())){
                        int month =Integer.parseInt(accountBean.getTime().substring(5, 7));
                        if(month ==( mDate.getMonth()+1 )) {
                            credit_repay_money = credit_repay_money + Double.parseDouble(accountBean.getMoney());
                        }
                    }

                }
                tv_repay.setText((credit_money_last-credit_repay_money)+"");

                if(credit_money > Double.parseDouble(ShareUtils.getCardMoney(mContext))){
                    tv_credit_pay_money.setTextColor(Color.RED);
                }else{
                    tv_credit_pay_money.setTextColor(Color.BLACK);
                }

                if(month_money > Double.parseDouble(ShareUtils.getMonthMoney(mContext))){
                    tv_month_pay_money.setTextColor(Color.RED);
                }else{
                    tv_month_pay_money.setTextColor(Color.BLACK);
                }
                tv_credit_pay_money.setText(credit_money+"");
                tv_month_pay_money.setText(month_money+"");
            }else{
                tv_month_pay_money.setText("0");
                tv_credit_pay_money.setText("0");
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
