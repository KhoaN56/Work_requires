package com.example.work_requires;

import java.io.Serializable;

public class WorkRequirement implements Serializable {
    private int id;
    private String jobName;
    private String companyName;
    private String major;
    private String area;
    private long salary;
    private String degree;
    private String workPos;
    private int experience;
    private String requirement;
    private String benefit;
    private String description;
    private String endDate;

    public WorkRequirement(int id, String jobName, String major, String area, long salary, String degree,
                           String workPos, int experience, String description,String requirement,
                           String benefit,String endDate, String companyName) {
        this.id = id;
        this.jobName = jobName;
        this.companyName = companyName;
        this.major = major;
        this.area = area;
        this.salary = salary;
        this.degree = degree;
        this.workPos = workPos;
        this.experience = experience;
        this.endDate = endDate;
        this.requirement = requirement;
        this.benefit = benefit;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWorkPos() {
        return workPos;
    }

    public void setWorkPos(String workPos) {
        this.workPos = workPos;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
