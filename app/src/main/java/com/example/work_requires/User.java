package com.example.work_requires;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    String username;
    String password;
    String type;
    String name;
    String email;
    String phone;
    String fax;
    String address;
    String area;

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

    public User(String username, String password, String type, String name, String email, String phone, String fax, String address, String area) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
        this.address = address;
        this.area = area;
    }

    public User(String username, String password, String type, String name, String email, String phone, String fax, String address, String area, String major) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
        this.address = address;
        this.area = area;
        this.major = major;
    }

    public User(String jobPos, String degree, Integer experience, String date_of_birth, String country,
                String sex, String school, String major, String classify, String detail_experience) {
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

    public  void setUser(String jobPos, String degree, Integer experience, String date_of_birth,
                         String country, String sex, String school, String major, String classify, String detail_experience){
        this.jobPos= jobPos;
        this.degree= degree;
        this.experience= experience;
        this.date_of_birth= date_of_birth;
        this.country= country;
        this.sex= sex;
        this.school= school;
        this.major= major;
        this.classify= classify;
        this.detail_experience= detail_experience;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }

    public String getMajor() {
        return major;
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

    public String getClassify() {
        return classify;
    }

    public String getDetail_experience() {
        return detail_experience;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setMajor(String major) { this.major = major; }

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

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public void setDetail_experience(String detail_experience) {
        this.detail_experience = detail_experience;
    }
}
