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
import com.sj.ticket.activity.bean.StudentInfo;

/**
 * 创建时间: on 2018/4/3.
 * 创建人: 孙杰
 * 功能描述:
 */

public class TicketRyvAdapter extends RecyclerArrayAdapter<StudentInfo> {
    Context context;

    public TicketRyvAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public TicketRyvHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new TicketRyvHolder(parent);
    }

    private static class TicketRyvHolder extends BaseViewHolder<StudentInfo> {
        TextView txtStudentInfo;

        public TicketRyvHolder(ViewGroup parent) {
            super(parent, R.layout.item_student_info);
            txtStudentInfo = $(R.id.txt_student_info);
        }

        @Override
        public void setData(final StudentInfo data) {
            super.setData(data);
            if (data.getStudentJSON() != null) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(data.getStudentJSON().getCusname());
                stringBuffer.append("\n");
                stringBuffer.append(data.getStudentJSON().getSex().equals("0") ? "女" : "男");
                stringBuffer.append("\n");
                stringBuffer.append(data.getStudentJSON().getPhone());
                stringBuffer.append("\n");
                stringBuffer.append(data.getStudentJSON().getRegistrationTime());
                txtStudentInfo.setText(stringBuffer.toString());
            }
        }
    }

}
