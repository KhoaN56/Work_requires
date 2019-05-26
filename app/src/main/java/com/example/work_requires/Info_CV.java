package com.example.work_requires;

import java.io.Serializable;

public class Info_CV implements Serializable {
    String jobPos;
    String degree;
    Integer experience;
    String date_of_birth;
    String country;
    String sex;
    String school;
    String major;
    String classify;
    String detail_experience;

    public Info_CV(String jobPos, String degree, Integer experience, String date_of_birth, String country, String sex, String school, String major, String classify, String detail_experience) {
        this.jobPos = jobPos;
        this.degree = degree;
        this.experience = experience;
        this.date_of_birth = date_of_birth;
        this.country = country;
        this.sex = sex;
        this.school = school;
        this.major = major;
        this.classify = classify;
        this.detail_experience = detail_experience;
    }

    public String getJobPos() {
        return jobPos;
    }

    public String getDegree() {
        return degree;
    }

    public Integer getExperience() {
        return experience;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getCountry() {
        return country;
    }

    public String getSex() {
        return sex;
    }

    public String getSchool() {
        return school;
    }

    public String getMajor() {
        return major;
    }

    public String getClassify() {
        return classify;
    }

    public String getDetail_experience() {
        return detail_experience;
    }

    public void setJobPos(String jobPos) {
        this.jobPos = jobPos;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public void setDetail_experience(String detail_experience) {
        this.detail_experience = detail_experience;
    }
}
