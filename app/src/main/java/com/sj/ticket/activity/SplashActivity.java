package com.sj.ticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jady.retrofitclient.HttpManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.sj.module_lib.BuildConfig;
import com.sj.module_lib.utils.SPUtils;
import com.sj.ticket.http.UrlConfig;
import com.sj.ticket.utils.SPName;
/**
 * 创建时间: on 2018/3/30.
 * 创建人: 孙杰
 * 功能描述:
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toGoNext();
    }

    private void initTools() {
        if (BuildConfig.DEBUG == true) {
            Logger.addLogAdapter(new AndroidLogAdapter());
        }
        HttpManager.init(this.getApplicationContext(), UrlConfig.BASE_URL);
    }


    private void toGoNext() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                if ((Boolean) SPUtils.getInstance().getSharedPreference(SPName.IS_LOGIN, false)) {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
            }
        }, 1000);
        initTools();
    }
}
