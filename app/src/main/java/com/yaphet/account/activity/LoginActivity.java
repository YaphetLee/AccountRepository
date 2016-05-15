package com.yaphet.account.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yaphet.account.R;
import com.yaphet.account.utils.ShareUtils;
import com.yaphet.account.view.TitleBuilder;

/**
 * Created by Administrator on 2016/5/4.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private TitleBuilder mBuilder;
    private Button btn_login;
    private EditText et_password;
    private String password;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void findView() {
        new TitleBuilder(this).setMiddleTitleText("登录");
        btn_login = (Button) findViewById(R.id.btn_login);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    @Override
    public void setListener() {
        btn_login.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        password = ShareUtils.getPassword(mContext);
        if(password.length()==0){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String pawd = et_password.getText().toString();
                if(pawd == null || "".equals(pawd)){
                    showToast("请输入密码");
                    return ;
                }
                if(pawd.equals(password)){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else{
                    showToast("您输入的密码不正确");
                    return ;
                }
                break;
        }
    }
}
