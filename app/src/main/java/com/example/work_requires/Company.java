package com.example.work_requires;

public class Company {
    String name;
    String email;
    String phone;
    String address;
    String area;
    String fax;

    public Company(String name, String email, String phone, String address, String area, String fax) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.area = area;
        this.fax = fax;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
