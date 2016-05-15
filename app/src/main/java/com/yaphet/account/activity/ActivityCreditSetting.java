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
public class ActivityCreditSetting extends BaseActivity implements View.OnClickListener{

    private EditText et_input_credit;
    private Button btn_credit_settings;
    private ImageView img_back;
    @Override
    public int setLayout() {
        return R.layout.activity_set_creditmoney;
    }

    @Override
    public void findView() {
        et_input_credit = (EditText) findViewById(R.id.et_input_credit);
        btn_credit_settings = (Button) findViewById(R.id.btn_credit_setting);
        img_back = (ImageView) findViewById(R.id.img_back);
    }

    @Override
    public void setListener() {
        btn_credit_settings.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_credit_setting){
            String credit_money = et_input_credit.getText().toString();
            if (TextUtils.isEmpty(credit_money)) {
                showToast("请输入信用卡消费额度");
                return;
            }
             ShareUtils.setCardMoney(mContext,credit_money);
            if(ShareUtils.getCardMoney(mContext).length()>0){
                showToast("信用卡消费额度设置成功");
                ActivityCreditSetting.this.finish();
            }
        }

        if(v.getId()==R.id.img_back){
            ActivityCreditSetting.this.finish();
        }
    }
}
