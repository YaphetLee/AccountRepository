package com.yaphet.account.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yaphet.account.R;
import com.yaphet.account.utils.ShareUtils;

/**
 * Created by Administrator on 2016/5/7.
 */
public class ActivityMonthSetting extends BaseActivity implements View.OnClickListener{

    private EditText et_input_month;
    private Button btn_setting;
    private ImageView img_back;
    @Override
    public int setLayout() {
        return R.layout.activity_set_monthmoney;
    }

    @Override
    public void findView() {
        et_input_month = (EditText) findViewById(R.id.et_input_month);
        btn_setting = (Button) findViewById(R.id.btn_setting);
        img_back = (ImageView) findViewById(R.id.img_back);
    }

    @Override
    public void setListener() {
        btn_setting.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.btn_setting){
           String month_money = et_input_month.getText().toString();
           if (TextUtils.isEmpty(month_money)) {
               showToast("月消费额度不能为0");
           }
           ShareUtils.setMonthMoney(mContext,month_money);
           if(ShareUtils.getMonthMoney(mContext).length()>0){
               showToast("设置成功");
               ActivityMonthSetting.this.finish();
           }
       }
        if(v.getId()==R.id.img_back){
            ActivityMonthSetting.this.finish();
        }
    }

}
