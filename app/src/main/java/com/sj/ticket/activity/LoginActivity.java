package com.sj.ticket.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jady.retrofitclient.HttpManager;
import com.orhanobut.logger.Logger;
import com.sj.module_lib.http.Callback;
import com.sj.module_lib.http.GsonResponsePasare;
import com.sj.module_lib.utils.DeviceUtils;
import com.sj.module_lib.utils.SPUtils;
import com.sj.module_lib.utils.ToastUtils;
import com.sj.ticket.R;
import com.sj.ticket.activity.bean.LoginBean;
import com.sj.ticket.base.AppBaseActivity;
import com.sj.ticket.http.UrlConfig;
import com.sj.ticket.utils.SPName;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建时间: on 2018/3/29.
 * 创建人: 孙杰
 * 功能描述:登录页
 */
public class LoginActivity extends AppBaseActivity {
    @BindView(R.id.edt_phone_value)
    EditText edtPhoneValue;
    @BindView(R.id.edt_code_value)
    EditText edtCodeValue;
    @BindView(R.id.bt_getcode)
    Button btGetcode;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_register_detail)
    TextView tvRegisterDetail;

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initEvent() {
        btLogin.post(new Runnable() {
            @Override
            public void run() {
                AndPermission.with(LoginActivity.this)
                        .permission(Permission.CAMERA,
                                Permission.READ_PHONE_STATE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                // TODO what to do.
                            }
                        }).onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        // TODO what to do
                    }
                }).start();
            }
        });
    }

    /**
     * 倒计时60秒，一次1秒
     */
    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btGetcode.setEnabled(false);
            btGetcode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btGetcode.setEnabled(true);
            btGetcode.setText("获取验证码");
        }
    };

    @OnClick({R.id.bt_getcode, R.id.bt_login, R.id.tv_register_detail})
    public void onClick(View view) {
        Logger.i("onClick(View view):" + view.getId());
        switch (view.getId()) {
            case R.id.bt_getcode:
               getCode(edtPhoneValue.getText().toString().trim());
                break;
            case R.id.bt_login:
                doLogin(edtPhoneValue.getText().toString().trim(), edtCodeValue.getText().toString().trim(), DeviceUtils.getUniqueId(this));
                break;
            default:
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getBooleanExtra("isLogined", false)) {
            toMainActivity();
        }
    }

    public void toMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }


    public void refreshCodeTxt(boolean refresh) {
        if (refresh) {
            timer.start();
        } else {
            timer.onFinish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
    public void getCode(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11) {
            ToastUtils.showShortToast("手机号码不正确");
            return;
        }
        refreshCodeTxt(true);
        Map<String, Object> parameters = new ArrayMap<>(1);
        parameters.put("phone", phoneNum);
        HttpManager.get(UrlConfig.GET_SMSCODE_URL, parameters, new Callback() {
            @Override
            public void onSuccess(String s) {
                ToastUtils.showShortToast(s);
            }

            @Override
            public void onSuccessData(String json) {

            }

            @Override
            public void onFailure(String error_code, String error_message) {
                refreshCodeTxt(false);
            }
        });
    }
    public void doLogin(final String phoneNum, String codeNum, String deviceId) {
        showProgress();
        Logger.d("phoneNum:" + phoneNum + ",codeNum:" + codeNum + ",deviceId" + deviceId);
        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11) {
            ToastUtils.showShortToast("手机号码不正确");
            dismissProgress();
            return;
        }
        if (TextUtils.isEmpty(codeNum)) {
            ToastUtils.showShortToast("请输入验证码");
            dismissProgress();
            return;
        }
        Map<String, Object> parameters = new ArrayMap<>(3);
        parameters.put("phone", phoneNum);
        parameters.put("checkcode", codeNum);
        parameters.put("deviceId", deviceId);
        HttpManager.get(UrlConfig.LOGIN_URL, parameters, new Callback() {
            @Override
            public void onSuccess(String json) {
                toMainActivity();
            }

            @Override
            public void onSuccessData(String json) {
                LoginBean loginBean = new GsonResponsePasare<LoginBean>() {
                }.deal(json);
                String tokenId = loginBean.getTokenId();
                SPUtils.getInstance().commit(new String[]{SPName.TOKEN_ID, SPName.IS_LOGIN, SPName.USER_ACCOUNT}, new Object[]{tokenId, true, phoneNum});
                toMainActivity();
            }

            @Override
            public void onFailure(String error_code, String error_message) {

            }

            @Override
            public void onFinish() {
                dismissProgress();
            }
        });
    }
}
