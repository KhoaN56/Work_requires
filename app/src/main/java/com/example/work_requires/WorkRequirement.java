package com.example.work_requires;

public class WorkRequirement {
    String jobName;
    String companyName;
    String major;
    String area;
    long salary;
    String degree;
    String workPos;
    int experience;
    String endDate;

    public WorkRequirement(String jobName, String major, String area, long salary, String degree,
                           String workPos, int experience, String endDate, String companyName) {
        this.jobName = jobName;
        this.companyName = companyName;
        this.major = major;
        this.area = area;
        this.salary = salary;
        this.degree = degree;
        this.workPos = workPos;
        this.experience = experience;
        this.endDate = endDate;
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
