package com.example.work_requires.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class WorkRequirement implements Serializable {

    private String id;
    private String jobName;
    private String idCompany;
    private String companyName;
    private String major;
    private String city;
    private String district;
    private long salary;
    private String degree;
    private String workPos;
    private long experience;
    private String requirement;
    private String benefit;
    private String description;
    private String endDate;
    //Number of person who applied for the job
    private int applied;
    //Maximum of available position
    private int amount;

    private List<String>userID;     //List of users who applied for this job

    public WorkRequirement() {
    }

    public WorkRequirement(String jobName, String companyName, String major, String endDate, int applied) {
        this.jobName = jobName;
        this.companyName = companyName;
        this.major = major;
        this.endDate = endDate;
        this.applied = applied;
    }

    public WorkRequirement(String jobName, String major, String city, String district, long salary, String degree,
                           String workPos, int experience, int amount, String description, String requirement,
                           String benefit, String endDate, String companyName, String compID){//, int salary) {
        this.jobName = jobName;
        this.companyName = companyName;
        this.major = major;
        this.city = city;
        this.district = district;
        this.salary = salary;
        this.degree = degree;
        this.workPos = workPos;
        this.experience = experience;
        this.endDate = endDate;
        this.requirement = requirement;
        this.benefit = benefit;
        this.description = description;
        this.amount = amount;
        this.idCompany = compID;
//        this.salary = salary;
    }

    public void postRequirement(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        this.id = database.child("Jobs").child("Detail").push().getKey();
        database.child("Jobs").child("Detail").child(this.id).setValue(this);
        HashMap<String, Object>childUpdate = this.toJobItem();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/Jobs/Job List/"+this.id);
        reference.updateChildren(childUpdate);
    }

    public HashMap<String, Object> toJobItem(){
        HashMap<String, Object>jobItem = new HashMap<>();
        jobItem.put("id", this.id);
        jobItem.put("idCompany", this.idCompany);
        jobItem.put("jobName", this.jobName);
        jobItem.put("companyName", this.companyName);
        jobItem.put("major", this.major);
        jobItem.put("endDate", this.endDate);
        jobItem.put("salary", this.salary);
        jobItem.put("applied", this.applied);
        return jobItem;
    }

    public int getApplied() {
        return applied;
    }

    public void setApplied(int applied) {
        this.applied = applied;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public Long getExperience() {
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
