package com.curiosity.mycalendar.utils;

import com.curiosity.mycalendar.bean.StudentInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Description : 该工具类用于 Dom的处理
 * Author : curiosity-hyf
 * Date : 2016-9-11
 * E-mail : curiooosity.h@gmail.com
 */

public class DomUtils {
    public static StudentInfo getStudentInfo(String htmlString) {
        Document doc = Jsoup.parse(htmlString);
        StudentInfo info = new StudentInfo();
        info.setName(doc.select("#p > table > tbody > tr:nth-child(1) > td:nth-child(4) > label").text());
        info.setAdmission(doc.select("#p > table > tbody > tr:nth-child(1) > td:nth-child(6) > label").text());
        info.setClas(doc.select("#p > table > tbody > tr:nth-child(3) > td:nth-child(2) > label").text());
        info.setInstitute(doc.select("#p > table > tbody > tr:nth-child(2) > td:nth-child(2) > label").text());
        info.setMajor(doc.select("#p > table > tbody > tr:nth-child(2) > td:nth-child(4) > label").text());
        info.setStuNum(doc.select("#p > table > tbody > tr:nth-child(1) > td:nth-child(2) > label").text());
        return info;
    }
}
