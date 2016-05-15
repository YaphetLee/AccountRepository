package com.yaphet.account.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yaphet.account.R;
import com.yaphet.account.utils.ShareUtils;

/**
 * Created by Administrator on 2016/5/5.
 */
public class ActivitySettingPassword extends BaseActivity implements View.OnClickListener{

    private EditText et_input_password;
    private Button btn_settings_password;
    private ImageView img_back;
    @Override
    public int setLayout() {
        return R.layout.activity_set_password;
    }

    @Override
    public void findView() {
        et_input_password = (EditText) findViewById(R.id.et_input_password);
        btn_settings_password = (Button) findViewById(R.id.btn_settings);
        img_back = (ImageView) findViewById(R.id.img_back);
    }

    @Override
    public void setListener() {
         btn_settings_password.setOnClickListener(this);
         img_back.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.btn_settings){
           String password = et_input_password.getText().toString().trim();
           if(TextUtils.isEmpty(password)){
               showToast("请输入密码");
               return;
           }
           ShareUtils.setPassword(mContext,password);
           if(ShareUtils.getPassword(mContext).length()>0){
               showToast("设置密码成功，请重新登录！");
               ActivitySettingPassword.this.finish();
               mContext.startActivity(new Intent(mContext,LoginActivity.class));
           }
       }
        if(v.getId()==R.id.img_back){
            ActivitySettingPassword.this.finish();
        }
    }
}
