package com.example.work_requires.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Company implements Serializable{

    private String userId;
    private String name;        //Tên công ty
    private String email;
    private String phone;
    private String fax;
    private String address;
    private String city;
    private String district;

    private List<String>jobPosted;

    public Company() {
        jobPosted = new ArrayList<>();
    }

    public Company(String userId, String name, String email, String phone, String fax, String address, String city, String district) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
        this.address = address;
        this.city = city;
        this.district = district;
        jobPosted = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<String> getJobPosted() {
        return jobPosted;
    }

    public int getIndexOfPostedJob (String jobId){
        return this.jobPosted.indexOf(jobId);
    }

    public boolean isPosted (String jobId){
        return this.jobPosted.contains(jobId);
    }

    public void removePostedJob(String jobId){
        this.jobPosted.remove(jobId);
    }

    public void addJob(String jobID){
        this.jobPosted.add(jobID);
    }

    public void signUpUser(String userId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Company").child(userId).setValue(this);
    }
}