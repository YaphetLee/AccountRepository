package com.yaphet.account.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yaphet.account.R;
import com.yaphet.account.activity.ActivityCreditSetting;
import com.yaphet.account.activity.ActivityDeleteCategory;
import com.yaphet.account.activity.ActivityMonthSetting;
import com.yaphet.account.activity.ActivityRemovePassword;
import com.yaphet.account.activity.ActivityRenameCategory;
import com.yaphet.account.activity.ActivitySettingPassword;
import com.yaphet.account.activity.ActivitySortCategory;
import com.yaphet.account.application.MyApplication;
import com.yaphet.account.bean.AccountBean;
import com.yaphet.account.bean.CategoryBean;
import com.yaphet.account.utils.ShareUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by Gary on 2016/4/24.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{

    private View settingView;
    private TextView tv_add_category,tv_rename_category,tv_sort_category;
    private TextView tv_delete_category;
    private TextView tv_restatics;
    private TextView tv_credit_settings,tv_monthPay_settings;
    private TextView tv_setting_password,tv_remove_password;
    private AlertDialog mDialog;
    private CategoryBean categoryBean;
    private DbManager dbManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingView = inflater.inflate(R.layout.frag_settings,container,false);
        findView(settingView);
        setListener();
        loadData();
        return settingView;
    }

    private void loadData(){
        MyApplication application = (MyApplication) getActivity().getApplicationContext();
        dbManager = x.getDb(application.getDaoConfig());
    }

    private void findView(View view){
        tv_add_category = (TextView) view.findViewById(R.id.tv_type_category);
        tv_rename_category = (TextView) view.findViewById(R.id.tv_rename_category);
        tv_restatics = (TextView) view.findViewById(R.id.tv_restatics);
        tv_credit_settings = (TextView) view.findViewById(R.id.tv_credit_setting);
        tv_monthPay_settings = (TextView) view.findViewById(R.id.tv_monthpay_setting);
        tv_setting_password = (TextView) view.findViewById(R.id.tv_set_password);
        tv_remove_password = (TextView) view.findViewById(R.id.tv_remove_password);
        tv_delete_category = (TextView) view.findViewById(R.id.tv_delete_category);
        tv_sort_category = (TextView) view.findViewById(R.id.tv_sort_category);
    }

    private void setListener(){
        tv_remove_password.setOnClickListener(this);
        tv_setting_password.setOnClickListener(this);
        tv_monthPay_settings.setOnClickListener(this);
        tv_credit_settings.setOnClickListener(this);
        tv_add_category.setOnClickListener(this);
        tv_remove_password.setOnClickListener(this);
        tv_restatics.setOnClickListener(this);
        tv_delete_category.setOnClickListener(this);
        tv_rename_category.setOnClickListener(this);
        tv_sort_category.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_set_password:    //  设置密码
                Intent intent = new Intent(getActivity(), ActivitySettingPassword.class);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_remove_password:
                Intent intent1 = new Intent(getActivity(), ActivityRemovePassword.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.tv_type_category:
                showDialog();
                break;
            case R.id.tv_delete_category:
                Intent intent4 = new Intent(getActivity(), ActivityDeleteCategory.class);
                getActivity().startActivity(intent4);
                break;
            case R.id.tv_credit_setting:
                Intent intent2 = new Intent(getActivity(), ActivityCreditSetting.class);
                getActivity().startActivity(intent2);
                break;
            case R.id.tv_monthpay_setting:
                Intent intent3 = new Intent(getActivity(), ActivityMonthSetting.class);
                getActivity().startActivity(intent3);
                break;
            case R.id.tv_rename_category:
                Intent intent5 = new Intent(getActivity(), ActivityRenameCategory.class);
                getActivity().startActivity(intent5);
                break;
            case R.id.tv_sort_category:
                showSortDialog();
                break;
            case R.id.tv_restatics:
                try {
                    dbManager.delete(AccountBean.class);
                    Toast.makeText(getActivity(), "已清空记录！", Toast.LENGTH_SHORT).show();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    //显示alertDialog
    private void showDialog(){
        //自定义布局
        View layout = getActivity().getLayoutInflater().inflate(R.layout.category_settings,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        mDialog = builder.create();
        View categoryView = LayoutInflater.from(getActivity()).inflate(R.layout.category_settings,null);
        final EditText et_category = (EditText) categoryView.findViewById(R.id.et_category);
        Button btn_category_ok = (Button) categoryView.findViewById(R.id.btn_category_ok);
        Button btn_category_cancel = (Button) categoryView.findViewById(R.id.btn_category_cancel);
        btn_category_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = et_category.getText().toString().trim();
                if(TextUtils.isEmpty(category)){
                    Toast.makeText(getActivity(), "请输入类别", Toast.LENGTH_SHORT).show();
                    return;
                }
                CategoryBean categoryBean = new CategoryBean();
                categoryBean.setTypeName(category);
                try {
                    dbManager.save(categoryBean);
                    Toast.makeText(getActivity(), "类别添加成功", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_category_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });

//        Window window = mDialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.9f;
//        lp.width = window.getWindowManager().getDefaultDisplay().getWidth();
//        lp.height = window.getWindowManager().getDefaultDisplay().getHeight()/ 2;
//        window.setAttributes(lp);
        mDialog.setView(categoryView);
        mDialog.show();
    }



    //显示alertDialog
    private void showSortDialog(){
        //自定义布局
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        mDialog = builder.create();
        View categoryView = LayoutInflater.from(getActivity()).inflate(R.layout.sort_category,null);
        TextView tv_asc = (TextView) categoryView.findViewById(R.id.tv_asc);
        TextView tv_desc = (TextView) categoryView.findViewById(R.id.tv_desc);
        tv_asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getActivity(),ActivitySortCategory.class);
                intent6.putExtra("sort","1");  //升序
                getActivity().startActivity(intent6);
                mDialog.dismiss();
            }
        });
        tv_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getActivity(),ActivitySortCategory.class);
                intent6.putExtra("sort","2");  //降序
                getActivity().startActivity(intent6);
                mDialog.dismiss();
            }
        });
        mDialog.setView(categoryView);
        mDialog.show();
    }
}
