package com.example.work_requires.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Noti{
    private String idTarget;
    private String name;
    /**
     * Hành động thông báo:
     * 1 - Xem hồ sơ
     * 2 - Đăng ký
     * 2 - Gọi điện thoại
     * 3 - Gửi mail
     * */
    private int action;
    private String dayTime;
    private boolean state;
    private String key;
    private String from;    //source id of the subject which cause this noti

    public Noti() {
    }

    public Noti(String idTarget, String name, int action, String dayTime, boolean state) {
        this.idTarget = idTarget;
        this.name = name;
        this.action = action;
        this.dayTime = dayTime;
        this.state = state;
    }

    public Noti(String idTarget, String name, int action, String dayTime, boolean state, String from) {
        this.idTarget = idTarget;
        this.name = name;
        this.action = action;
        this.dayTime = dayTime;
        this.state = state;
        this.from = from;
    }

    public String getKey() {
        return key;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public String getName() {
        return name;
    }

    public int getAction() {
        return action;
    }

    public String getDayTime() {
        return dayTime;
    }

    public boolean getState() {
        return state;
    }

    public String getFrom() {
        return from;
    }

    public void updateState(String path){
        this.state = true;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
        reference.setValue(true);
    }

    public void send(String path) {
        DatabaseReference send = FirebaseDatabase.getInstance().getReference(path);
        this.key = send.push().getKey();
        send.child(this.key).setValue(this);
    }
}
