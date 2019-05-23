package com.example.work_requires;

import java.util.Date;

public class WorkRequirement {
    String companyName;
    String major;
    String area;
    String salary;
    String degree;
    String workPos;
    int experience;
    Date startDate;
    Date endDate;

    public WorkRequirement(String companyName, String major, String area, String salary, String degree,
                           String workPos, int experience, Date startDate, Date endDate) {
        this.companyName = companyName;
        this.major = major;
        this.area = area;
        this.salary = salary;
        this.degree = degree;
        this.workPos = workPos;
        this.experience = experience;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
