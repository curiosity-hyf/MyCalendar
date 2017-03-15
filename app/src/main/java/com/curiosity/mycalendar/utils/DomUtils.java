package com.curiosity.mycalendar.utils;

import com.curiosity.mycalendar.bean.CourseInfo;
import com.curiosity.mycalendar.bean.RestgetCurriculum;
import com.curiosity.mycalendar.bean.StudentInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Curiosity on 2016-9-11.
 * 该工具类用于 Dom的处理
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
