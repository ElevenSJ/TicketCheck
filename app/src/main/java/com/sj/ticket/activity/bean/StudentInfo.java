package com.sj.ticket.activity.bean;

/**
 * Created by Sunj on 2018/8/13.
 */

public class StudentInfo {

    /**
     * studentId : YH1530422122123PvAmWX
     * classId : 92233705045833702098024221490104597af05717d6ec67936
     * creationTime : 2018-08-09 22:55:02
     * id : 9223370503028273711119d7cd67b4e4f3384559042ec9af41c
     * studentJSON : {"area":"","registrationTime":"2018-08-09 22:55:02","salespersonId":"","phone":"15870805822","cuspoint":"0","openid":"oPjfT1K6b1TjK1PjvEhQKTJka_GA","sex":"","cusname":"e","id":"YH1530422122123PvAmWX","codepic":"http://public.app-storage-node.com/FtNjfcga2VdFlkGqy6O8MkesQM11","cusicon":"http://thirdwx.qlogo.cn/mmopen/vi_32/dGwcEbY405zQocmVffvgttk8BHjgCibKUewNBibWyajylTHKEWkL1pZBkYIYyTvvVa0icianxvOEhDpmmSlnnTTA2A/132"}
     */

    private String studentId;
    private String classId;
    private String creationTime;
    private String id;
    private StudentJSONBean studentJSON;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudentJSONBean getStudentJSON() {
        return studentJSON;
    }

    public void setStudentJSON(StudentJSONBean studentJSON) {
        this.studentJSON = studentJSON;
    }

    public static class StudentJSONBean {
        /**
         * area :
         * registrationTime : 2018-08-09 22:55:02
         * salespersonId :
         * phone : 15870805822
         * cuspoint : 0
         * openid : oPjfT1K6b1TjK1PjvEhQKTJka_GA
         * sex :
         * cusname : e
         * id : YH1530422122123PvAmWX
         * codepic : http://public.app-storage-node.com/FtNjfcga2VdFlkGqy6O8MkesQM11
         * cusicon : http://thirdwx.qlogo.cn/mmopen/vi_32/dGwcEbY405zQocmVffvgttk8BHjgCibKUewNBibWyajylTHKEWkL1pZBkYIYyTvvVa0icianxvOEhDpmmSlnnTTA2A/132
         */

        private String area;
        private String registrationTime;
        private String salespersonId;
        private String phone;
        private String cuspoint;
        private String openid;
        private String sex;
        private String cusname;
        private String id;
        private String codepic;
        private String cusicon;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getRegistrationTime() {
            return registrationTime;
        }

        public void setRegistrationTime(String registrationTime) {
            this.registrationTime = registrationTime;
        }

        public String getSalespersonId() {
            return salespersonId;
        }

        public void setSalespersonId(String salespersonId) {
            this.salespersonId = salespersonId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCuspoint() {
            return cuspoint;
        }

        public void setCuspoint(String cuspoint) {
            this.cuspoint = cuspoint;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCusname() {
            return cusname;
        }

        public void setCusname(String cusname) {
            this.cusname = cusname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCodepic() {
            return codepic;
        }

        public void setCodepic(String codepic) {
            this.codepic = codepic;
        }

        public String getCusicon() {
            return cusicon;
        }

        public void setCusicon(String cusicon) {
            this.cusicon = cusicon;
        }
    }
}
