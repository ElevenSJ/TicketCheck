package com.sj.ticket.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sj.ticket.R;
import com.sj.ticket.activity.bean.TicketBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: on 2018/6/23.
 * 创建人: 孙杰
 * 功能描述:
 */
public class TicketPagerAdapter extends PagerAdapter {

    private List<View> viewList = new ArrayList<>();
    private List<TicketBean> ticketList;
    private Context mContext;

    public TicketPagerAdapter(Context mContext, List<TicketBean> ticketList) {
        this.mContext = mContext;
        this.ticketList = ticketList;
        ticketList.add(null);
        viewList.add(inflaterItemView());
    }
    public void addOrUpdateItemView(TicketBean ticketBean) {
        if (ticketList.get(ticketList.size()-1)==null||ticketList.get(ticketList.size()-1).getStudent()==null){
            ticketList.remove(ticketList.size()-1);
        }else{
            viewList.add(inflaterItemView());
        }
        ticketList.add(ticketBean);
        notifyDataSetChanged();
    }
    private View inflaterItemView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_ticket_info, null);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return viewList == null ? 0 : viewList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        UpdateTicket(view, ticketList.get(position));
        container.addView(view);
        return viewList.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private void UpdateTicket(View view, TicketBean ticketBean) {
        if (ticketBean != null) {
            ImageView imgCheckResult = view.findViewById(R.id.img_check_result);
            TextView txtResult = view.findViewById(R.id.txt_result);
            TextView txtName = view.findViewById(R.id.txt_name);
            TextView txtCourseTime = view.findViewById(R.id.txt_course_time);
            TextView txtCourseAddress = view.findViewById(R.id.txt_course_address);
            TextView txtStudentInfo = view.findViewById(R.id.txt_student_info);
            if (ticketBean.getStudent()!=null) {
                imgCheckResult.setSelected(true);
                txtResult.setText("检票成功");
                txtResult.setTextColor(mContext.getResources().getColor(android.R.color.black));
                if (ticketBean.getClassX() != null) {
                    txtName.setText(ticketBean.getClassX().getName());
                    txtCourseTime.setText(ticketBean.getClassX().getClassTime());
                    txtCourseAddress.setText(ticketBean.getClassX().getClassPlace());
                }
                if (ticketBean.getStudent() != null) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(ticketBean.getStudent().getUserName());
                    stringBuffer.append("\n");
                    stringBuffer.append(ticketBean.getStudent().getSex().equals("0") ? "女" : "男");
                    stringBuffer.append("\n");
                    stringBuffer.append(ticketBean.getStudent().getAge());
                    stringBuffer.append("\n");
                    stringBuffer.append(ticketBean.getStudent().getPhone());
                    txtStudentInfo.setText(stringBuffer.toString());
                }
            }else{
                imgCheckResult.setSelected(false);
                txtResult.setText("检票失败");
                txtResult.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));
                txtName.setText("课程名称");
                txtCourseTime.setText("上课时间");
                txtCourseAddress.setText("上课地点");
                txtStudentInfo.setText("");
            }
        }
    }

}
