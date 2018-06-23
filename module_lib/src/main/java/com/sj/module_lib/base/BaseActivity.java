package com.sj.module_lib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sj.module_lib.widgets.CustomDialog;


/**
 * 创建时间: on 2018/3/29.
 * 创建人: 孙杰
 * 功能描述:activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    private CustomDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isProgressShowing()) {
            dismissProgress();
        }
        progressDialog = null;
    }

    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new CustomDialog(this);
        }
        if (!isProgressShowing()) {
            progressDialog.show();
        }
    }

    public void dismissProgress() {
        if (isProgressShowing()) {
            progressDialog.dismiss();
        }
    }

    public boolean isProgressShowing() {
        if (progressDialog != null) {
            return progressDialog.isShowing();
        } else {
            return false;
        }
    }

    public abstract int getContentView();

    public void initView() {
    }

    public void initEvent() {
    }

}
