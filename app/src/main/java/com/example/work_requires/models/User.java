package com.example.work_requires.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
//import android.widget.Toast;

//import com.example.work_requires.Login;
import com.example.work_requires.Login;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**Class người tìm việc làm
 *
 *
 */
public class User implements Serializable {

    private String userId;
    private String name;
    private String email;
    private String phone;
    private String city;
    private String district;
    private CVInterview cv;

    //Danh sách các công việc được lưu
    private List<String>savedId;
    //Danh sách các công việc đã đăng ký
    private List<String>appliedId;

    public User() {
        this.savedId = new ArrayList<>();
        this.appliedId = new ArrayList<>();
    }

    public User(@NotNull String name,@NotNull String email){
        this.name = name;
        this.email = email;
        this.savedId = new ArrayList<>();
        this.appliedId = new ArrayList<>();
    }

    //Khởi tạo thông tin cơ bản
    public User(@NotNull String userId, String name,@NotNull String email, String phone, String city, String district) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.savedId = new ArrayList<>();
        this.appliedId = new ArrayList<>();
    }


    /**
     * Use this function to add new user's basic information to firebase
     * userId require @NotNull
     */
    public void signUpUser(){
        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference();
        database.child("Users").child("Detail").child(this.userId).setValue(this);
        database.child("Users").child("User List").child(this.userId).child("name").setValue(this.name);
        database.child("Users").child("User List").child(this.userId).child("cv").child("experience").setValue(this.cv.getExperience());
        database.child("Users").child("User List").child(this.userId).child("userId").setValue(this.userId);
//        database.child("Users").child("Basic info").child(userId).child("email").setValue(this.email);
//        database.child("Users").child("Basic info").child(userId).child("phone").setValue(this.phone);
//        database.child("Users").push().child("Basic info").child(userId).child("city").setValue(this.city);
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

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public CVInterview getCv() {
        return cv;
    }

    public void setCv(CVInterview cv) {
        this.cv = cv;
    }

    public int getNumberOfSavedId() {
        return savedId.size();
    }

    public void addSavedId(@NotNull String jobId) {
        this.savedId.add(jobId);
    }

    public void loadSavedIdList(){
        final DatabaseReference database = FirebaseDatabase.getInstance()
                .getReference("/Users/Saved/"+this.userId);
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    String id = dataSnapshot.getKey();
                    savedId.add(id);
//                    loadAppliedId();
                }catch (Exception ex){
                    Log.d("Test query", ex.toString());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        do {
//            Log.d("Notification", "undone");
//        }while (getNumberOfSavedId()==0);
    }

    public void removeSavedId(String jobId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("/Users/Saved/"+this.userId+"/"+jobId);
        reference.removeValue();
        this.savedId.remove(jobId);
    }

    public boolean isSaved(String jobId){
        return this.savedId.contains(jobId);
    }

    public int getNumberOfAppliedId() {
        return appliedId.size();
    }

    public void addAppliedId(@NotNull String jobId) {
        this.appliedId.add(jobId);
    }

    public boolean isApplied(String jobId){
        return this.appliedId.contains(jobId);
    }

    public void loadAppliedId(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("/Users/Applied/"+this.userId);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    String id = dataSnapshot.getKey();
                    appliedId.add(id);
                }catch (Exception ex){
                    Log.d("Applied ID", dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        do {
//            Log.d("Notification", "undone");
//        }while (getNumberOfAppliedId()==0);
    }
}
