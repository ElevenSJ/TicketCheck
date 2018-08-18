package com.sj.ticket.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jady.retrofitclient.HttpManager;
import com.orhanobut.logger.Logger;
import com.sj.module_lib.http.Callback;
import com.sj.module_lib.http.GsonResponsePasare;
import com.sj.module_lib.utils.SPUtils;
import com.sj.module_lib.utils.ToastUtils;
import com.sj.ticket.R;
import com.sj.ticket.activity.bean.TicketBean;
import com.sj.ticket.adapter.TicketPagerAdapter;
import com.sj.ticket.base.AppBaseActivity;
import com.sj.ticket.http.UrlConfig;
import com.sj.ticket.utils.SPName;
import com.uuzuche.lib_zxing.activity.CaptureFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建时间: on 2018/6/20.
 * 创建人: 孙杰
 * 功能描述:
 */
public class CustomGunCaptureActivity extends AppBaseActivity {

    @BindView(R.id.view_page)
    ViewPager viewPager;
    @BindView(R.id.et_qcode)
    EditText etQcode;

//    String classId;
    String token;

    List<TicketBean> ticketList = new ArrayList<>();
    TicketPagerAdapter myPagerAdapter;//适配器

    Handler hander = new Handler(Looper.getMainLooper());
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            etQcode.setEnabled(false);
            doCheck(etQcode.getText().toString().toLowerCase());
        }
    };

    private void doCheck(String result) {
        Logger.i("扫描信息:"+result);
        if (TextUtils.isEmpty(result)) {
            ToastUtils.showShortToast("未扫描到信息");
            etQcode.setEnabled(true);
            etQcode.setText("");
            etQcode.requestFocus();
            etQcode.setFocusableInTouchMode(true);
            return;
        }
        if (result.indexOf("student") == -1||result.indexOf("class") == -1) {
            ToastUtils.showShortToast("信息不正确");
            etQcode.setEnabled(true);
            etQcode.setText("");
            etQcode.requestFocus();
            etQcode.setFocusableInTouchMode(true);
            return;
        }
        showProgress();
        String[] strs = result.split("&");
        String studentId = "";
        String classId = "";
        for (String str : strs) {
            if (str.startsWith("student")) {
                String[] studentArray = str.split("=");
                studentId =studentArray.length>0?studentArray[1]:"";
            }
            if (str.startsWith("class")) {
                String[] classArray = str.split("=");
                classId =classArray.length>0?classArray[1]:"";
            }
        }
//        Uri.Builder builder = Uri.parse(UrlConfig.BASE_URL + UrlConfig.SCANNING_QR_URL).buildUpon();
//        builder.appendQueryParameter("token", token);

        Map<String, Object> parameters = new ArrayMap<>(3);
        parameters.put("token", token);
        parameters.put("studentId", studentId);
        parameters.put("classId", classId);
        HttpManager.get(UrlConfig.SCANNING_QR_URL, parameters, new Callback() {
            @Override
            public void onSuccess(String json) {

            }

            @Override
            public void onSuccessData(String json) {
                TicketBean ticketBean = new GsonResponsePasare<TicketBean>() {
                }.deal(json);
                checkTicketComplete(ticketBean);
            }

            @Override
            public void onFailure(String error_code, String error_message) {
                checkTicketComplete(new TicketBean());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissProgress();
                etQcode.setEnabled(true);
                etQcode.setText("");
                etQcode.requestFocus();
                etQcode.setFocusableInTouchMode(true);
            }
        });
    }

    private void checkTicketComplete(TicketBean ticketBean) {
        myPagerAdapter.addOrUpdateItemView(ticketBean);
        viewPager.setCurrentItem(myPagerAdapter.getCount() - 1,true);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_custom_gun_capture;
    }

    @Override
    public void initView() {
        super.initView();
//        classId = getIntent().getStringExtra("id");
        token = (String) SPUtils.getInstance().getSharedPreference(SPName.TOKEN_ID, "");
        setTitleTxt("扫码检票");
        myPagerAdapter = new TicketPagerAdapter(this, ticketList);//创建适配器实例
        viewPager.setAdapter(myPagerAdapter);//为ViewPager设置适配器
    }

    @Override
    public void initEvent() {
        super.initEvent();
        etQcode.requestFocus();
        etQcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                hander.removeCallbacks(runnable);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    hander.postDelayed(runnable, 600);
                }
            }
        });
    }
}
