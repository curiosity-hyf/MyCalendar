package com.curiosity.mycalendar.utils;

import com.curiosity.mycalendar.bean.CourseInfo;
import com.curiosity.mycalendar.bean.CurriculumInfo;
import com.curiosity.mycalendar.bean.StudentInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Curiosity on 2016-9-11.
 * 该工具类用于 Dom的处理
 */
public class InfoUtils {

    /**
     * 获取学生信息
     *
     * @param doc 学生信息页面
     * @return 学生信息实体
     */
    public static StudentInfo getStuInfo(Document doc) {
        StudentInfo info = new StudentInfo();
        info.setName(doc.select("span#xm").text());
        info.setStuNum(doc.select("span#xh").text());
        info.setInstitute(doc.select("span#lbl_xy").text());
        info.setMajor(doc.select("span#lbl_zymc").text());
        info.setClas(doc.select("span#lbl_xzb").text());
        info.setAdmission(doc.select("span#lbl_rxrq").text().substring(0, 4));
        System.out.println(info.toString());
        return info;
    }

    /**
     * 获取 可用课表年份
     *
     * @param doc 课表页面
     * @return 可用课表年份列表
     */
    public static List<String> getYearsInfo(Document doc) {
        List<String> list = new ArrayList<>();
        Elements els = doc.select("select#xnd > option");
        for (Element e : els) {
            list.add(e.text());
        }
        return list;
    }


}
