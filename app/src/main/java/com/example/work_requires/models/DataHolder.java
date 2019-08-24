package com.example.work_requires.models;

public class DataHolder {

    static private User normalUser;
    static private Company compUser;
    static private WorkRequirement job;


    public static WorkRequirement getJob() {
        return job;
    }

    public static void setJob(WorkRequirement job) {
        DataHolder.job = job;
    }

    public static DataHolder getInstance(){
        return new DataHolder();
    }

    public static User getNormalUser(){
        return normalUser;
    }

    public static void clearNormalUser(){DataHolder.normalUser = null;}

    public static Company getCompUser(){
        return compUser;
    }

    public static void setNormalUser(User user){
        normalUser = user;
    }

    public static void setCompUser(Company user){
        compUser = user;
    }

    public static void clearData(){
        DataHolder.job = null;
    }

    public static void clearAll(){
        DataHolder.compUser = null;
        DataHolder.normalUser = null;
        DataHolder.job = null;
    }
}
