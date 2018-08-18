package com.sj.ticket.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sj.module_lib.base.BaseActivity;
import com.sj.ticket.R;
import com.sj.ticket.activity.LoginActivity;
import com.sj.ticket.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * 创建时间: on 2018/6/19.
 * 创建人: 孙杰
 * 功能描述:
 */
public abstract class AppBaseActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.img_title_back)
    ImageView imgTitleBack;

    @Nullable
    @BindView(R.id.img_title_right)
    ImageView imgTitleRight;

    @Nullable
    @BindView(R.id.txt_message_title)
    TextView txtMessageTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    public void setImgTitleRight(boolean isVisiable, View.OnClickListener clickListener) {
        if (imgTitleRight != null) {
            imgTitleRight.setVisibility(isVisiable ? View.VISIBLE : View.INVISIBLE);
            imgTitleRight.setOnClickListener(clickListener);
        }
    }

    public void setTitleTxt(String titleTxt) {
        if (txtMessageTitle != null) {
            txtMessageTitle.setVisibility(titleTxt == null || TextUtils.isEmpty(titleTxt) ? View.INVISIBLE : View.VISIBLE);
            if (titleTxt != null && !TextUtils.isEmpty(titleTxt)) {
                txtMessageTitle.setText(titleTxt);
            }
        }
    }

    @Optional
    @OnClick(R.id.img_title_back)
    public void onTitleBackClick() {
        finish();
    }

    @Optional
    @OnClick(R.id.img_title_right)
    public void onTitleRightClick() {
        showExitDialog();
    }

    public void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setMessage("是否确认退出程序");//提示内容
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (AppBaseActivity.this instanceof MainActivity) {
                    ((MainActivity) AppBaseActivity.this).exitApp();
                } else {
                    Intent intent = new Intent(AppBaseActivity.this, MainActivity.class);
                    intent.putExtra("LoginOut", true);
                    startActivity(intent);
                    finish();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
