package com.example.work_requires.models;

import java.io.Serializable;

public class CVInterview implements Serializable{

    private String jobPos;
    private String degree;
    private int experience;
    private String birthDay;
    private String sex;
    private String school;
    private String major;
    private String classify;
    private String detail_experience;

    public CVInterview() {
    }

    public CVInterview(String jobPos, String degree, int experience, String birthDay, String sex,
                       String school, String major, String classify, String detail_experience) {
        this.jobPos = jobPos;
        this.degree = degree;
        this.experience = experience;
        this.birthDay = birthDay;
        this.sex = sex;
        this.school = school;
        this.major = major;
        this.classify = classify;
        this.detail_experience = detail_experience;
    }

    public String getJobPos() {
        return jobPos;
    }

    public void setJobPos(String jobPos) {
        this.jobPos = jobPos;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getDetail_experience() {
        return detail_experience;
    }

    public void setDetail_experience(String detail_experience) {
        this.detail_experience = detail_experience;
    }
}
