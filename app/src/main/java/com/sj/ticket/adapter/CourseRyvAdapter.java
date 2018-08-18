package com.sj.ticket.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.sj.module_lib.glide.ImageUtils;
import com.sj.ticket.R;
import com.sj.ticket.activity.ClassDetailActivity;
import com.sj.ticket.activity.CustomCaptureActivity;
import com.sj.ticket.activity.CustomGunCaptureActivity;
import com.sj.ticket.activity.bean.CourseBean;

/**
 * 创建时间: on 2018/4/3.
 * 创建人: 孙杰
 * 功能描述:
 */

public class CourseRyvAdapter extends RecyclerArrayAdapter<CourseBean> {
    Context context;

    public CourseRyvAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public CourseRyvHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseRyvHolder(parent);
    }

    private static class CourseRyvHolder extends BaseViewHolder<CourseBean> {

        TextView txtName;
        TextView txtTime;
        TextView txtAddress;
        ImageView imgIcon;
        Button btQrCamera;
        Button btQrGang;
        TextView txtHistory;

        public CourseRyvHolder(ViewGroup parent) {
            super(parent, R.layout.course_item);
            this.txtName = $(R.id.txt_name);
            this.txtTime = $(R.id.txt_course_time);
            this.txtAddress = $(R.id.txt_course_address);
            this.imgIcon = $(R.id.img_icon);
            this.btQrGang = $(R.id.bt_qr_gang);
            this.btQrCamera = $(R.id.bt_qr_camera);
            this.txtHistory = $(R.id.tv_history);
        }

        @Override
        public void setData(final CourseBean data) {
            super.setData(data);
            ImageUtils.loadImageWithError(data.getIcon(), R.mipmap.ic_launcher, imgIcon);
            txtName.setText(data.getName());
            txtTime.setText(String.format("上课时间：%s", data.getClassTime()));
            txtAddress.setText(String.format("上课地点：%s", data.getClassPlace()));
            btQrGang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(v.getContext(), data.getId());
                }
            });
            btQrCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CustomCaptureActivity.class);
                    intent.putExtra("id", data.getId());
                    v.getContext().startActivity(intent);
                }
            });
            txtHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ClassDetailActivity.class);
                    intent.putExtra("data", data);
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void showDialog(final Context mContext, final String id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
            builder.setMessage("请确认蓝牙是否已连接扫码枪");//提示内容
            builder.setPositiveButton("已连接", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(mContext, CustomGunCaptureActivity.class);
                    intent.putExtra("id", id);
                    mContext.startActivity(intent);
                }
            });
            builder.setNegativeButton("去连接", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                    mContext.startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}
