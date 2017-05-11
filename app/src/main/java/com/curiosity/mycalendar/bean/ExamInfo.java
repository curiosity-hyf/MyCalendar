package com.curiosity.mycalendar.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-12
 * E-mail : curiooosity.h@gmail.com
 */

public class ExamInfo {

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * xnxqmc : 2016春季
         * wpjbz :
         * ksxzdm : 0
         * rwdm :
         * bz :
         * xnxqdm : 201502
         * kcdm : 00931
         * kcdlmc : 公共基础课
         * rownum_ : 1
         * cjfsmc : 百分制
         * isactive : 1
         * cjbzmc :
         * zxs : 64
         * xf : 4
         * xdfsmc : 必修
         * kcflmc :
         * xsbh : 3114006092
         * xsdm : 3114006092
         * cjjd : 3.7
         * ksxzmc : 正常考试
         * xsxm : 黄一帆
         * kcbh : TMP0931
         * cjdm : 0-3411744
         * wzcbz :
         * zcj : 87
         * wzc : 0
         * kcmc : 大学英语(4)
         * wpj : 0
         */

        @SerializedName("xnxqdm")
        private String semesterCode;
        @SerializedName("kcdlmc")
        private String type;
        @SerializedName("xf")
        private String courseWeight;
        @SerializedName("xdfsmc")
        private String courseRequired;
        @SerializedName("cjjd")
        private String scorePoint;
        @SerializedName("zcj")
        private String score;
        @SerializedName("kcmc")
        private String courseName;

        public String getSemesterCode() {
            return semesterCode;
        }

        public void setSemesterCode(String semesterCode) {
            this.semesterCode = semesterCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCourseWeight() {
            return courseWeight;
        }

        public void setCourseWeight(String courseWeight) {
            this.courseWeight = courseWeight;
        }

        public String getCourseRequired() {
            return courseRequired;
        }

        public void setCourseRequired(String courseRequired) {
            this.courseRequired = courseRequired;
        }

        public String getScorePoint() {
            return scorePoint;
        }

        public void setScorePoint(String scorePoint) {
            this.scorePoint = scorePoint;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
    }
}
