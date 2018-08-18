package com.sj.ticket.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.animation.AccelerateInterpolator;

import com.jady.retrofitclient.HttpManager;
import com.orhanobut.logger.Logger;
import com.sj.module_lib.http.Callback;
import com.sj.module_lib.http.GsonResponsePasare;
import com.sj.module_lib.utils.SPUtils;
import com.sj.module_lib.utils.ToastUtils;
import com.sj.module_lib.widgets.FixedSpeedScroller;
import com.sj.ticket.R;
import com.sj.ticket.activity.bean.TicketBean;
import com.sj.ticket.adapter.TicketPagerAdapter;
import com.sj.ticket.base.AppBaseActivity;
import com.sj.ticket.http.UrlConfig;
import com.sj.ticket.utils.SPName;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 创建时间: on 2018/6/20.
 * 创建人: 孙杰
 * 功能描述:
 */
public class CustomCaptureActivity extends AppBaseActivity {

    @BindView(R.id.view_page)
    ViewPager viewPager;
    FixedSpeedScroller mScroller;

    List<TicketBean> ticketList = new ArrayList<>();
    TicketPagerAdapter myPagerAdapter;//适配器

    CaptureFragment captureFragment;

//    String classId;
    String token;

    Handler hander = new Handler(Looper.getMainLooper());
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            captureFragment.setRestart();
        }
    };
    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Logger.i(result);
            doCheck(result);
        }

        @Override
        public void onAnalyzeFailed() {
            doCheck(null);
        }
    };


    private void doCheck(String result) {
        if (TextUtils.isEmpty(result)) {
            ToastUtils.showShortToast("未扫描到信息");
            hander.post(runnable);
            return;
        }
        if (result.indexOf("student") == -1||result.indexOf("class") == -1) {
            ToastUtils.showShortToast("信息不正确");
            hander.post(runnable);
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

            }
        });
    }

    private void checkTicketComplete(TicketBean ticketBean) {
        myPagerAdapter.addOrUpdateItemView(ticketBean);
        viewPager.setCurrentItem(myPagerAdapter.getCount()-1,true);
        hander.postDelayed(runnable,500);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_custom_capture;
    }

    @Override
    public void initView() {
        super.initView();
//        classId = getIntent().getStringExtra("id");
        token = (String) SPUtils.getInstance().getSharedPreference(SPName.TOKEN_ID, "");

        myPagerAdapter = new TicketPagerAdapter(this, ticketList);//创建适配器实例
        viewPager.setAdapter(myPagerAdapter);//为ViewPager设置适配器

        viewPager.post(new Runnable() {
            @Override
            public void run() {
                setViewPagerScorll();
                /**
                 * 执行扫面Fragment的初始化操作
                 */
                captureFragment = new CaptureFragment();
                CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
                captureFragment.setAnalyzeCallback(analyzeCallback);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
            }
        });
    }

    private void setViewPagerScorll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new FixedSpeedScroller(viewPager.getContext(), new AccelerateInterpolator());
            mField.set(viewPager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
