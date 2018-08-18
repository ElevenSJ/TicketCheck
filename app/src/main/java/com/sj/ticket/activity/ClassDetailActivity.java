package com.sj.ticket.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.ArrayMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.jady.retrofitclient.HttpManager;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sj.module_lib.glide.ImageUtils;
import com.sj.module_lib.http.Callback;
import com.sj.module_lib.http.GsonResponsePasare;
import com.sj.module_lib.utils.SPUtils;
import com.sj.ticket.R;
import com.sj.ticket.activity.bean.CourseBean;
import com.sj.ticket.activity.bean.StudentInfo;
import com.sj.ticket.activity.bean.TicketBean;
import com.sj.ticket.adapter.CourseRyvAdapter;
import com.sj.ticket.adapter.TicketRyvAdapter;
import com.sj.ticket.base.AppBaseActivity;
import com.sj.ticket.http.UrlConfig;
import com.sj.ticket.utils.SPName;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sunj on 2018/8/13.
 */

public class ClassDetailActivity extends AppBaseActivity {
    @BindView(R.id.ryl_view)
    EasyRecyclerView rylView;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_course_time)
    TextView txtCourseTime;
    @BindView(R.id.txt_course_address)
    TextView txtCourseAddress;
    @BindView(R.id.img_icon)
    ImageView imgIcon;

    TicketRyvAdapter mAdapter;
    CourseBean courseBean;
    String token;

    @Override
    public int getContentView() {
        return R.layout.activity_class_detail;
    }

    @Override
    public void initView() {
        super.initView();
        token = (String) SPUtils.getInstance().getSharedPreference(SPName.TOKEN_ID,"");
        courseBean = (CourseBean) getIntent().getSerializableExtra("data");
        setTitleTxt("检票记录");
        initCourseInfo();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rylView.setLayoutManager(layoutManager);
//        DividerDecoration dividerDecoration = new DividerDecoration(getResources().getColor(R.color.gray_AD), 1, 16, 16);
//        dividerDecoration.setDrawLastItem(false);
//        rylView.addItemDecoration(dividerDecoration);
        mAdapter = new TicketRyvAdapter(this);
        rylView.setAdapter(mAdapter);
//        mAdapter.setMore(R.layout.layout_load_more, this);
//        mAdapter.setNoMore(R.layout.layout_load_no_more);
//        rylView.setRefreshListener(this);
        rylView.post(new Runnable() {
            @Override
            public void run() {
                if (courseBean!=null){
                    getTicketInfo(courseBean.getId());
                }
            }
        });
    }

    private void getTicketInfo(String id) {
        Map<String, Object> parameters = new ArrayMap<>(2);
        parameters.put("token", token);
        parameters.put("classId", id);
        HttpManager.get(UrlConfig.TICKET_CHECK_LIST_URL, parameters, new Callback() {
            @Override
            public void onSuccess(String json) {

            }

            @Override
            public void onSuccessData(String json) {
                List<StudentInfo> studentInfoList = new GsonResponsePasare< List<StudentInfo>>() {
                }.deal(json);
                if (studentInfoList!=null){
                    mAdapter.clear();
                    mAdapter.addAll(studentInfoList);
                }
            }

            @Override
            public void onFailure(String error_code, String error_message) {
            }
            @Override
            public void onFinish() {
                super.onFinish();
                rylView.setRefreshing(false);
            }
        });
    }


    private void initCourseInfo() {
        if (courseBean==null){
            return;
        }
        ImageUtils.loadImageWithError(courseBean.getIcon(), R.mipmap.ic_launcher, imgIcon);
        txtName.setText(courseBean.getName());
        txtCourseTime.setText(String.format("上课时间：%s", courseBean.getClassTime()));
        txtCourseAddress.setText(String.format("上课地点：%s", courseBean.getClassPlace()));
    }

}
