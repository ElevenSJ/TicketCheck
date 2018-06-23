package com.sj.ticket.app;

import com.sj.module_lib.base.BaseApplication;
import com.sj.module_lib.utils.Utils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * 创建时间: on 2018/6/17.
 * 创建人: 孙杰
 * 功能描述:
 */
public class TicketApplication  extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        ZXingLibrary.initDisplayOpinion(this);
    }
}
