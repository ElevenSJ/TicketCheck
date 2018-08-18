package com.sj.ticket.http;

import android.support.annotation.Keep;

/**
 * 创建时间: on 2018/3/31.
 * 创建人: 孙杰
 * 功能描述:请求地址配置
 */
@Keep
public class UrlConfig {
    //baseUrl
    public static final String BASE_URL = "http://prj-peixun.app-service-node.com";

    //获取验证码
    public static final String GET_SMSCODE_URL = "/appSendSmsValidCodeToPhone";
    //登录
    public static final String LOGIN_URL = "/appLogin";
    //注销
    public static final String LOGIN_OUT_URL = "/appLoginOut";
    //查询班级列表
    public static final String CLASS_LIST_URL = "/appClassList";
    //查询班级详情
    public static final String CLASS_BY_ID_URL = "/appClassById";
    //app检票
    public static final String SCANNING_QR_URL = "/appScanningQR";
    //检票记录
    public static final String TICKET_CHECK_LIST_URL = "/appCheckRecord";

}
