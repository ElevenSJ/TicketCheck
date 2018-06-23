package com.sj.ticket.activity.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 创建时间: on 2018/6/20.
 * 创建人: 孙杰
 * 功能描述:
 */
public class TicketBean {

    /**
     * student : {"phone":"15870805822","sex":"0","id":"922337050930182028892ab5662129147449c1740ec9aa983d3","userName":"测试2","age":"18","email":"123123@qq.com"}
     * class : {"number":"1","code":"上海","classPlace":"上课地点","creationTime":"2018-06-10 17:01:00","icon":"https://public.app-storage-node.com/FitfGWBBSjXixCz0dNvvJ5WEQEPw?attname=å\u009c\u0086ç\u0099½ç»¿1.png","name":"2","id":"9223370507381222833c7ade081a1d4466e92822f090ca3759a","curriculumId":"9223370509203272147188a678de9764702bcca8f6fdf3557be","classTime":"2018-05-11","lectureId":"922337050929627137854dc6e5327ec450998a31825994266d9","status":"0"}
     */

    private StudentBean student;
    @JSONField(name="class")
    private ClassBean classX;

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public ClassBean getClassX() {
        return classX;
    }

    public void setClassX(ClassBean classX) {
        this.classX = classX;
    }

    public static class StudentBean {
        /**
         * phone : 15870805822
         * sex : 0
         * id : 922337050930182028892ab5662129147449c1740ec9aa983d3
         * userName : 测试2
         * age : 18
         * email : 123123@qq.com
         */

        private String phone;
        private String sex;
        private String id;
        private String userName;
        private String age;
        private String email;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class ClassBean {
        /**
         * number : 1
         * code : 上海
         * classPlace : 上课地点
         * creationTime : 2018-06-10 17:01:00
         * icon : https://public.app-storage-node.com/FitfGWBBSjXixCz0dNvvJ5WEQEPw?attname=åç½ç»¿1.png
         * name : 2
         * id : 9223370507381222833c7ade081a1d4466e92822f090ca3759a
         * curriculumId : 9223370509203272147188a678de9764702bcca8f6fdf3557be
         * classTime : 2018-05-11
         * lectureId : 922337050929627137854dc6e5327ec450998a31825994266d9
         * status : 0
         */

        private String number;
        private String code;
        private String classPlace;
        private String creationTime;
        private String icon;
        private String name;
        private String id;
        private String curriculumId;
        private String classTime;
        private String lectureId;
        private String status;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getClassPlace() {
            return classPlace;
        }

        public void setClassPlace(String classPlace) {
            this.classPlace = classPlace;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCurriculumId() {
            return curriculumId;
        }

        public void setCurriculumId(String curriculumId) {
            this.curriculumId = curriculumId;
        }

        public String getClassTime() {
            return classTime;
        }

        public void setClassTime(String classTime) {
            this.classTime = classTime;
        }

        public String getLectureId() {
            return lectureId;
        }

        public void setLectureId(String lectureId) {
            this.lectureId = lectureId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
