package com.curiosity.mycalendar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-3
 * E-mail : curiooosity.h@gmail.com
 */

public class WeekCourses {
    private List<Courses> weekcourses;
    private int count;

    public WeekCourses() {
        this(new ArrayList<Courses>());
    }
    public WeekCourses(List<Courses> list) {
        this(list, list.size());
    }
    public WeekCourses(List<Courses> list, int count) {
        this.weekcourses = list;
        this.count = count;
    }
    public void add(Courses courses) {
        weekcourses.add(courses);
        count++;
    }

    @Override
    public String toString() {
        return "WeekCourses{" +
                "weekcourses=" + weekcourses +
                ", count=" + count +
                '}';
    }
}
