package com.sj.ticket.activity.bean;

import android.support.annotation.Keep;

/**
 * 创建时间: on 2018/3/31.
 * 创建人: 孙杰
 * 功能描述:
 */
@Keep
public class LoginBean {
    private String tokenId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "tokenId='" + tokenId + '\'' +
                '}';
    }
}
