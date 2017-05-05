package com.curiosity.mycalendar.bean;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-4
 * E-mail : curiooosity.h@gmail.com
 */

public class Course {
    public enum TYPE {
        SYSTEM,
        CUSTOM;
    }

    private TYPE type; /*类型 : 1 系统 2 自定义*/
    private int grade; /*学年*/
    private int semester; /*学期*/
    private int weekNum; /*周次*/
    private int dayNum; /*星期*/
    private String clsNum; /*节次*/
    private String name; /*课程名称*/
    private String teacher; /*教师名称*/
    private String addr; /*上课地点*/
    private String fullTime; /*完整时间 yyyy-MM-dd*/
    private String other; /*其他*/

    @Override
    public String toString() {
        return "Course{" +
                "type=" + type +
                ", grade=" + grade +
                ", semester=" + semester +
                ", weekNum=" + weekNum +
                ", dayNum=" + dayNum +
                ", clsNum='" + clsNum + '\'' +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", addr='" + addr + '\'' +
                ", fullTime='" + fullTime + '\'' +
                ", other='" + other + '\'' +
                '}';
    }

    public Course() {
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public String getClsNum() {
        return clsNum;
    }

    public void setClsNum(String clsNum) {
        this.clsNum = clsNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
