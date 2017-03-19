package com.curiosity.mycalendar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description :
 * Author : Curiosity
 * Date : 2016-9-11
 * E-mail : 1184581135qq@gmail.com
 */

public class StudentInfo implements Parcelable {
    private String admission;
    private String stuNum;
    private String name;
    private String institute;
    private String major;
    private String clas;

    public StudentInfo() {
    }


    @Override
    public String toString() {
        return "StudentInfo{" +
                "admission='" + admission + '\'' +
                ", stuNum='" + stuNum + '\'' +
                ", name='" + name + '\'' +
                ", institute='" + institute + '\'' +
                ", major='" + major + '\'' +
                ", clas='" + clas + '\'' +
                '}';
    }

    public String getAdmission() {
        return admission;
    }

    public void setAdmission(String admission) {
        this.admission = admission;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.admission);
        dest.writeString(this.stuNum);
        dest.writeString(this.name);
        dest.writeString(this.institute);
        dest.writeString(this.major);
        dest.writeString(this.clas);
    }

    protected StudentInfo(Parcel in) {
        this.admission = in.readString();
        this.stuNum = in.readString();
        this.name = in.readString();
        this.institute = in.readString();
        this.major = in.readString();
        this.clas = in.readString();
    }

    public static final Parcelable.Creator<StudentInfo> CREATOR = new Parcelable.Creator<StudentInfo>() {
        @Override
        public StudentInfo createFromParcel(Parcel source) {
            return new StudentInfo(source);
        }

        @Override
        public StudentInfo[] newArray(int size) {
            return new StudentInfo[size];
        }
    };
}
