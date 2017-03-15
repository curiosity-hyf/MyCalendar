package com.curiosity.mycalendar.utils;

import com.curiosity.mycalendar.bean.CourseInfo;
import com.curiosity.mycalendar.bean.RestgetCurriculum;
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
public class DomUtils {

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
            System.out.println(e.text());
            list.add(e.text());
        }
        return list;
    }

    /**
     * 获取所有课程信息
     *
     * @param
     * @return
     */
    public static List<CourseInfo> getAllCurriInfo(RestgetCurriculum curriculum) {
        String year = curriculum.getYear();
        String semester = curriculum.getSemester();
        Document doc = curriculum.getDocument();

        List<CourseInfo> courseInfos = new ArrayList<>();

        if (doc.body().toString().contains("您本学期课所选学分小于 1.50分")) {
            System.out.println("false!!!");
            return courseInfos;
        }
        doc = Jsoup.parse(doc.html().replace("<br><br>", "<br>").replace("<br>", "$$$")); //将每个子项用 $$$ 分隔，便于后续处理

        Elements td = doc.select("table#Table1 > tbody > tr > td"); //获取待处理部分
        boolean skip = true;
        List<String> list = null;
        for (int i = 0; i < td.size(); ++i) {
            String s = td.get(i).text();
            if ("上午".equals(s) || "下午".equals(s) || "晚上".equals(s)) { //略过部分关键字
                skip = false;
                continue;
            } else if (skip) {
                continue;
            }
            if ('第' == s.charAt(0)) { //新的一节
                list = new ArrayList<>();
            }
            list.add(s);
            if (i + 1 < td.size() && '第' == td.get(i + 1).text().charAt(0)) { //结束一节标志
                List<CourseInfo> tmpList = TextUtils.getCourse(list);
                for (CourseInfo info : tmpList) {
                    info.setYear(year);
                    info.setSemester(semester);
                }
                courseInfos.addAll(tmpList); //处理当前节
            }
        }
//        for (CourseInfo courseInfo : courseInfos) {
//            System.out.println(courseInfo);
//        }

        return courseInfos;
    }

}