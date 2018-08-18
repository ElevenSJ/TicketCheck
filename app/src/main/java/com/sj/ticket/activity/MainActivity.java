package com.sj.ticket.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.ArrayMap;

import com.jady.retrofitclient.HttpManager;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sj.module_lib.http.Callback;
import com.sj.module_lib.http.GsonResponsePasare;
import com.sj.module_lib.utils.SPUtils;
import com.sj.module_lib.utils.Utils;
import com.sj.ticket.R;
import com.sj.ticket.activity.bean.CourseBean;
import com.sj.ticket.activity.bean.LoginBean;
import com.sj.ticket.adapter.CourseRyvAdapter;
import com.sj.ticket.base.AppBaseActivity;
import com.sj.ticket.http.UrlConfig;
import com.sj.ticket.utils.SPName;

import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class MainActivity extends AppBaseActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnMoreListener{

    @BindView(R.id.ryl_view)
    EasyRecyclerView rylView;

    CourseRyvAdapter mAdapter;

    String token;
    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.setMainActivity(MainActivity.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        token = (String)SPUtils.getInstance().getSharedPreference(SPName.TOKEN_ID,"");

        setTitleTxt("班级列表");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rylView.setLayoutManager(layoutManager);
        DividerDecoration dividerDecoration = new DividerDecoration(getResources().getColor(R.color.gray_AD), 1, 16, 16);
        dividerDecoration.setDrawLastItem(false);
        rylView.addItemDecoration(dividerDecoration);
        mAdapter = new CourseRyvAdapter(this);
//        mAdapter.setMore(R.layout.layout_load_more, this);
//        mAdapter.setNoMore(R.layout.layout_load_no_more);
        rylView.setAdapterWithProgress(mAdapter);
        rylView.setRefreshListener(this);
        rylView.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {
        rylView.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //互斥登录，退出当前设备
        if (intent != null) {
            if (intent.getBooleanExtra("LoginOut", false)) {
                exitApp();
            }
        }
    }
    @Override
    public void onRefresh() {
        getData();
    }

    private void getData() {
        Map<String, Object> parameters = new ArrayMap<>(1);
        parameters.put("token", token);
        HttpManager.get(UrlConfig.CLASS_LIST_URL, parameters, new Callback() {
            @Override
            public void onSuccess(String json) {
            }
            @Override
            public void onSuccessData(String json) {
                List<CourseBean> courseBeanList = new GsonResponsePasare< List<CourseBean>>() {
                }.deal(json);
                if (courseBeanList!=null){
                    mAdapter.clear();
                    mAdapter.addAll(courseBeanList);
                }
            }
            @Override
            public void onFailure(String error_code, String error_message) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                rylView.setRefreshing(false);
            }
        });
    }

    @Override
    public void onMoreShow() {

    }

    @Override
    public void onMoreClick() {

    }


    public void exitApp() {
        showProgress();
        Map<String, Object> parameters = new ArrayMap<>(1);
        parameters.put("token", token);
        HttpManager.get(UrlConfig.LOGIN_OUT_URL, parameters, new Callback() {
            @Override
            public void onSuccess(String json) {

            }
            @Override
            public void onSuccessData(String json) {

            }
            @Override
            public void onFailure(String error_code, String error_message) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissProgress();
                SPUtils.getInstance().commit(new String[]{SPName.TOKEN_ID, SPName.IS_LOGIN, SPName.USER_ACCOUNT}, new Object[]{"", false, ""});
                finish();
            }
        });
    }
}
