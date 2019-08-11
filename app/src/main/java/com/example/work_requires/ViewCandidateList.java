package com.example.work_requires;

//import android.content.DialogInterface;
import android.content.Intent;
//import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
//import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
//import android.widget.Toast;

import com.example.work_requires.adapters.CustomAdapter3;
import com.example.work_requires.models.Company;
import com.example.work_requires.models.WorkRequirement;
import com.example.work_requires.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewCandidateList extends AppCompatActivity {

    //Title
    TextView title;

    //Database
//    SQLiteManagement database;

    //User
    Company user;
//    User dummyUser;
    List<User> userList;
    List<String> userIds;

    //Requirement
    WorkRequirement requirement;

    //Recycle View
    RecyclerView userRecycleView;
    CustomAdapter3 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_candidate_list);
        initialize();
    }

    private void initialize() {
        user = (Company)getIntent().getSerializableExtra("user");
        requirement = (WorkRequirement)getIntent().getSerializableExtra("requirement");
        title = findViewById(R.id.title);
        title.setText(requirement.getJobName());
//        database = new SQLiteManagement(ViewCandidateList.this, "Work_Requirement.sqlite",null, 1);
        userList = new ArrayList<>();
//        Cursor cursor = database.getDatasql("SELECT USER.* FROM USER, DETAIL WHERE DETAIL.ID_RECRUITMENT = " +
//                "'"+requirement.getId()+"' AND USER.USERNAME = DETAIL.USERNAME");
        setUpRecyclerView();
        userIds = new ArrayList<>();
        DatabaseReference readUID = FirebaseDatabase.getInstance().getReference("/Jobs/Applied/"+requirement.getId());
        readUID.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                userIds.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                userIds.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference readUserList = FirebaseDatabase.getInstance().getReference("/Users/User List");
        readUserList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(userIds.contains(dataSnapshot.getKey())){
                    userList.add(dataSnapshot.getValue(User.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(userIds.contains(dataSnapshot.getKey())){
                    userList.set(userIds.indexOf(dataSnapshot.getKey()),dataSnapshot.getValue(User.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if(userIds.contains(dataSnapshot.getKey())){
                    userList.remove(dataSnapshot.getValue(User.class));
                    userIds.remove(dataSnapshot.getKey());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpRecyclerView() {
        userRecycleView = findViewById(R.id.userRecycleView);
        userRecycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewCandidateList.this);
        adapter = new CustomAdapter3(userList, ViewCandidateList.this);
        userRecycleView.setLayoutManager(layoutManager);
        userRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CustomAdapter3.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                DatabaseReference getDetail = FirebaseDatabase.getInstance()
                        .getReference("/Users/Detail/"+userList.get(position).getUserId());
                getDetail.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Intent toCandidateDetail = new Intent(ViewCandidateList.this, DetailCV.class);
                        toCandidateDetail.putExtra("user", dataSnapshot.getValue(User.class));
                        startActivity(toCandidateDetail);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        adapter.notifyDataSetChanged();
    }
}