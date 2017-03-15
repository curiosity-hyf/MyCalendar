package com.curiosity.mycalendar.utils;


import android.widget.EditText;

import com.curiosity.mycalendar.bean.CourseInfo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Curiosity on 2016-9-13.
 * 该工具类用于 文本的处理
 */
public class TextUtils {

    public static String getText(EditText editText) {
        return editText.getText().toString();
    }
    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

    public static byte[] convertStreamToBytes(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        byte[] cstb = null;
        int rc = 0;
        try {
            while ((rc = is.read(buff, 0, 1024)) != -1) {
                baos.write(buff, 0, rc);
            }
            cstb = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            baos = null;
        }
        return cstb;
    }

    public static String convertStreamToString(InputStream is, String encode) throws IOException {
        BufferedReader reader = null;
        StringBuilder sb = null;
        reader = new BufferedReader(new InputStreamReader(is, encode));
        sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }

        is.close();
        return sb == null ? "" : sb.toString();
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static String getName(String s) {
        return s;
    }

    private static String weekNum2weekString(int num) {
        switch (num) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期日";
            default:
                return "空";
        }
    }

    private static String getDayOfWeek(String s) {
        char w = s.charAt(1);
        switch (w) {
            case '一':
                return "星期一";
            case '二':
                return "星期二";
            case '三':
                return "星期三";
            case '四':
                return "星期四";
            case '五':
                return "星期五";
            case '六':
                return "星期六";
            case '日':
                return "星期日";
            default:
                return "空";
        }
    }

    private static int getTimeFrom(String s) {
//        System.out.println(s);
        String ss = s.substring(3, s.indexOf("节"));
        String[] sss = ss.split(",");
        return Integer.valueOf(sss[0]);
    }

    private static int getTimeTo(String s) {
        String ss = s.substring(3, s.indexOf("节"));
        String[] sss = ss.split(",");
        return Integer.valueOf(sss[sss.length - 1]);
    }

    private static int getWeekFrom(String s) {
        return Integer.valueOf(s.substring(s.lastIndexOf("第") + 1, s.indexOf("-")));
    }

    private static int getWeekTo(String s) {
        return Integer.valueOf(s.substring(s.indexOf("-") + 1, s.indexOf("周", s.indexOf("-"))));
    }

    private static String getTechName(String s) {
        return s;
    }

    private static String getClassNum(String s) {
        return s;
    }

    private static int getWeekFlag(String s) {
        String[] ss = s.split("\\|");
        if (ss.length == 1) {
            return 0;
        } else if (ss[1].contains("单")) {
            return 1;
        } else if (ss[1].contains("双")) {
            return 2;
        } else {
            return 0;
        }
    }

    private static List<String> getChangeType(String s) {
        return new ArrayList<>(Arrays.asList(s.split("、")));
    }

//    /**
//     * 获取周几某节的所有课程 e.g. 周三第一节
//     *
//     * @param s     未处理的 周几某节的所有课程信息
//     * @param dow   周几
//     * @param begin 某节起始
//     * @return 处理完毕的 周几某节的所有课程信息
//     */
//    private static List<CourseInfo> getCourse(String s, int dow, int begin) {
//        List<CourseInfo> list = new ArrayList<>();
//        String str[] = s.split("\\$\\$\\$"); //以$$$分隔
//
//        for (int i = 0; i < str.length; ) {
////            System.out.println("~~i = " +  i + " " + str[i]);
//            int weekFrom = getWeek(str[i + 1]); //设置 起始周
//            int weekTo = getWeekTo(str[i + 1]); //设置 终止周
//            boolean a = false, b = false;
//            for(int j = weekFrom, k = i; j <= weekTo; ++j, k = i) {
//                CourseInfo info = new CourseInfo();
//
//                info.setDayOfWeek(dow); //指定默认 周几
//                info.setTimeFrom(begin); //指定默认 某节起始
//                info.setTimeTo(begin + 1); //指定默认 某节终止
//
//                info.setCourseName(getName(str[k])); //设置课程名
//                //排除 诸如 {第1-9周} 而非 周一第1,2节{第7-9周} 的情况
//                if (k + 1 < str.length && !"".equals(str[k + 1]) && '{' != str[k + 1].charAt(0)) {
//                    info.setDayOfWeek(getDayOfWeek(str[k + 1])); //设置 周几
//                    info.setTimeFrom(getTimeFrom(str[k + 1])); //设置 起始时间
//                    info.setTimeTo(getTimeTo(str[k + 1])); //设置 终止时间
//                }
//                info.setWeek(j); //设置 第几周
//                info.setWeekFlag(getWeekFlag(str[k + 1])); //设置 单双周标志位
//                info.setTeacherName(getTechName(str[k + 2])); //设置 教师名称
//                info.setClassRoom(getClassNum(str[k + 3])); //设置 课时
//                if (k + 4 < str.length && !"".equals(str[k + 4])) {
//                    if (Character.isDigit(str[i + 4].charAt(0)) || '第' == str[k + 4].charAt(0)) { //排除垃圾信息
//                        k += 2;
//                        a = true;
//                    }
//                    if (k + 4 < str.length && !"".equals(str[k + 4]) && '(' == str[k + 4].charAt(0)) { //考虑是否有课程变化情况
//                        info.setChangeFlag(1); //设置变化标志位
//                        info.setChangeType(getChangeType(str[k + 4])); //设置 变化情况
//                        k += 1;
//                        b = true;
//                    }
//                }
//
//                list.add(info);
//            }
//            if(a) i += 2;
//            if(b) i += 1;
//            i += 4;
//        }
//        return list;
//    }
}
