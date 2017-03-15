package com.curiosity.mycalendar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Curiosity on 2016-9-11.
 */
public class StudentInfo implements Parcelable{
    private String admission;
    private String stuNum;
    private String name;
    private String institute;
    private String major;
    private String clas;

    public StudentInfo() {
    }

    public StudentInfo(String admission, String stuNum, String name, String institute, String major, String clas) {
        this.admission = admission;
        this.stuNum = stuNum;
        this.name = name;
        this.institute = institute;
        this.major = major;
        this.clas = clas;
    }

    protected StudentInfo(Parcel in) {
        admission = in.readString();
        stuNum = in.readString();
        name = in.readString();
        institute = in.readString();
        major = in.readString();
        clas = in.readString();
    }

    public static final Creator<StudentInfo> CREATOR = new Creator<StudentInfo>() {
        @Override
        public StudentInfo createFromParcel(Parcel in) {
            return new StudentInfo(in);
        }

        @Override
        public StudentInfo[] newArray(int size) {
            return new StudentInfo[size];
        }
    };

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
        dest.writeString(admission);
        dest.writeString(stuNum);
        dest.writeString(name);
        dest.writeString(institute);
        dest.writeString(major);
        dest.writeString(clas);
    }
}
