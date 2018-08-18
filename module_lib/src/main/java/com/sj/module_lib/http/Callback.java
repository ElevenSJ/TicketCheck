package com.sj.module_lib.http;

import android.content.Intent;
import android.support.annotation.Keep;

import com.google.gson.Gson;
import com.jady.retrofitclient.callback.HttpCallback;
import com.orhanobut.logger.Logger;
import com.sj.module_lib.utils.ToastUtils;
import com.sj.module_lib.utils.Utils;


/**
 * 创建时间: on 2018/3/31.
 * 创建人: 孙杰
 * 功能描述:请求回调基类
 */
@Keep
public abstract class Callback extends HttpCallback<String> {
    @Override
    public void onResolve(String json) {
        Logger.i("返回报文" + json);
        BaseResponse baseResponse = new Gson().fromJson(json, BaseResponse.class);
        Logger.i("返回报文" + baseResponse.getData().toString() +",length:"+baseResponse.getData().toString().length());
        if (baseResponse.success) {
            if (baseResponse.getData().toString().equals("[]")||baseResponse.getData().toString().equals("\"\"")||baseResponse.getData().toString().length()<=2){
                onSuccess(baseResponse.message);
            }else{
                onSuccessData(json);
            }
        } else {
            onFailed(baseResponse.code, baseResponse.message);
        }
        onFinish();
    }
    @Override
    public void onFailed(String error_code, String error_message) {
        onFailure(error_code, error_message);
        //其他设备登录统一处理
        if (error_code.equals("9")&&error_message.equals("当前账号已在其他设备登录")){
            if (Utils.getMainActivity()!=null){
                Intent intent = new Intent(Utils.getContext(), Utils.getMainActivity());
                intent.putExtra("LoginOut",true);
                Utils.getContext().startActivity(intent);
            }
        }
        if ((error_code.equals("9")&&error_message.equals("当前账号已在其他设备登录"))||enableShowToast()) {
            ToastUtils.showShortToast(error_message);
        }
        onFinish();
    }

    public abstract void onSuccess(String message);

    public abstract void onSuccessData(String json);

    public abstract void onFailure(String error_code, String error_message);

    public boolean enableShowToast() {
        return true;
    }

    public void onFinish() {
    }

}
