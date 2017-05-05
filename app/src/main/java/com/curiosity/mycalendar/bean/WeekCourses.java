package com.curiosity.mycalendar.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-3
 * E-mail : curiooosity.h@gmail.com
 */

public class WeekCourses {
    private int weekNum;
    private List<Course> courses;
    private int count;

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public WeekCourses(int weekNum) {
        this.weekNum = weekNum;
        this.courses = new ArrayList<>();
        this.count = courses.size();
    }

    public void add(Course course) {
        courses.add(course);
        count++;
    }

    public void add(Course course, int position) {
        courses.add(position, course);
        count++;
    }

    public void remove(int position) {
        courses.remove(position);
        count--;
    }

    public Course getCoursesAt(int index) {
        if(index >= count) {
            return null;
        }
        return courses.get(index);
    }

    @Override
    public String toString() {
        return "WeekCourses{" +
                "weekCourses=" + courses +
                ", count=" + count +
                '}';
    }
}
