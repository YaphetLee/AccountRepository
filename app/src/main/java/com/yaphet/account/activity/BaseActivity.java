package com.yaphet.account.activity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import org.json.JSONObject;

/**
 * Created by ouyang on 16/1/10.
 */
public abstract class BaseActivity extends FragmentActivity {

    protected ProgressDialog pd;
    public Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(setLayout());
        pd = new ProgressDialog((this));

    }



    protected void showProgerss() {
        if (pd != null) {
            pd.show();
        }
    }

    protected void hideProgress() {
        if (pd != null) {
            pd.hide();
            pd.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgress();
    }

    public abstract int setLayout();
    public abstract void findView();
    public abstract void setListener();
    public abstract void loadData();
}
