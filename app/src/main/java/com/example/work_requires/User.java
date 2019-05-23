package com.example.work_requires;

import java.util.Date;

public class User {
    String username;
    String password;
    int type;
    String name;
    String email;
    String phone;
    String fax;
    String address;
    String area;
    Date dateOfBirth;

    public User(String username, String password, int type, String name, String email, String phone, String fax, String address, String area, Date date_birth) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
        this.address = address;
        this.area = area;
        this.dateOfBirth = date_birth;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(int type) {
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

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
