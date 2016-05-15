package com.yaphet.account.activity;

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
public class ActivityRemovePassword extends BaseActivity implements View.OnClickListener{

    private EditText et_password;
    private Button btn_remove;
    private ImageView img_back;

    @Override
    public int setLayout() {
        return R.layout.activity_remove_password;
    }

    @Override
    public void findView() {
        et_password = (EditText) findViewById(R.id.et_input_remove_password);
        btn_remove = (Button) findViewById(R.id.btn_remove);
        img_back = (ImageView) findViewById(R.id.img_back);
    }

    @Override
    public void setListener() {
         btn_remove.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_remove){
            String password = et_password.getText().toString().trim();
            if(TextUtils.isEmpty(password)){
                showToast("请输入密码");
            }
            if(password.equals(ShareUtils.getPassword(mContext))){
                ShareUtils.setPassword(mContext,"");
                showToast("解绑密码成功");
                ActivityRemovePassword.this.finish();
            }else{
                showToast("密码不正确，请重新输入");
            }
        }
        if(v.getId()==R.id.img_back){
            ActivityRemovePassword.this.finish();
        }
    }
}
