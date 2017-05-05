package com.curiosity.mycalendar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-5
 * E-mail : curiooosity.h@gmail.com
 */

public class Curriculum {
    private int grade;
    private int semester;
    private int maxWeekNum;
    private List<WeekCourses> curriculum;
    private int count;

    public WeekCourses getWeekCoursesAt(int index) {
        if(index >= count) {
            return null;
        } else {
            return curriculum.get(index);
        }
    }

    public Curriculum(int grade, int semester) {
        this.grade = grade;
        this.semester = semester;
        this.maxWeekNum = 0;
        this.curriculum = new ArrayList<>();
        this.count = 0;
        this.count = curriculum.size();
    }

    public void add(WeekCourses weekCourses) {
        curriculum.add(weekCourses);

        maxWeekNum = Math.max(maxWeekNum, weekCourses.getWeekNum());

        count++;
    }

    @Override
    public String toString() {
        return "Curriculum{" +
                "grade=" + grade +
                ", semester=" + semester +
                ", maxWeekNum=" + maxWeekNum +
                ", curriculum=" + curriculum +
                ", count=" + count +
                '}';
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

    public int getMaxWeekNum() {
        return maxWeekNum;
    }

    public void setMaxWeekNum(int maxWeekNum) {
        this.maxWeekNum = maxWeekNum;
    }

    public List<WeekCourses> getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(List<WeekCourses> curriculum) {
        this.curriculum = curriculum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
