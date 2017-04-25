package com.curiosity.mycalendar.bean;

import java.util.List;

/**
 * Description :
 * Author : Curiosity
 * Date : 2016-9-13
 * E-mail : curiooosity.h@gmail.com
 */

public class CourseInfo {

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
        private String kcmc;
        private String teaxms;
        private String xq;
        private String jcdm;
        private String jxcdmc;
        private String zc;
        private String pkrq;

        public String getKcmc() {
            return kcmc;
        }

        public void setKcmc(String kcmc) {
            this.kcmc = kcmc;
        }

        public String getTeaxms() {
            return teaxms;
        }

        public void setTeaxms(String teaxms) {
            this.teaxms = teaxms;
        }

        public String getXq() {
            return xq;
        }

        public void setXq(String xq) {
            this.xq = xq;
        }

        public String getJcdm() {
            return jcdm;
        }

        public void setJcdm(String jcdm) {
            this.jcdm = jcdm;
        }

        public String getJxcdmc() {
            return jxcdmc;
        }

        public void setJxcdmc(String jxcdmc) {
            this.jxcdmc = jxcdmc;
        }

        public String getZc() {
            return zc;
        }

        public void setZc(String zc) {
            this.zc = zc;
        }

        public String getPkrq() {
            return pkrq;
        }

        public void setPkrq(String pkrq) {
            this.pkrq = pkrq;
        }

        @Override
        public String toString() {
            return "RowsBean{" +
                    "kcmc='" + kcmc + '\'' +
                    ", teaxms='" + teaxms + '\'' +
                    ", xq='" + xq + '\'' +
                    ", jcdm='" + jcdm + '\'' +
                    ", jxcdmc='" + jxcdmc + '\'' +
                    ", zc='" + zc + '\'' +
                    ", pkrq='" + pkrq + '\'' +
                    '}';
        }
    }
}