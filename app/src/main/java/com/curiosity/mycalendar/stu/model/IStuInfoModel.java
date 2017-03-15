package com.curiosity.mycalendar.stu.model;

/**
 * Description : 学生信息模型
 * Author : Curiosity
 * Date : 2017-3-12
 * E-mail : 1184581135qq@gmail.com
 */

public interface IStuInfoModel {
    void loadStuInfo(String url, OnLoadStuInfoListener listener);
}
