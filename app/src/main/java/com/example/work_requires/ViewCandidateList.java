package com.example.work_requires;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ViewCandidateList extends AppCompatActivity {

    //Database
    SQLiteManagement database;

    //User
    User user;
    List<User> userList;

    //Requirement
    WorkRequirement requirement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_candidate_list);
        initialize();
    }

    private void initialize() {
        user = (User)getIntent().getSerializableExtra("user");
        requirement = (WorkRequirement)getIntent().getSerializableExtra("requirement");
        database = new SQLiteManagement(ViewCandidateList.this, "Work_Requirement.sqlite",null, 1);
        userList = new ArrayList<>();
        Cursor cursor = database.getDatasql("SELECT USER.* FROM USER, DETAIL WHERE DETAIL.ID_RECRUITMENT = " +
                "'"+requirement.getId()+"' AND USER.USERNAME = DETAIL.USERNAME");
        while(cursor.moveToNext())
            userList.add(new User(cursor.getString(0),cursor.getString(2), cursor.getString(1),
                    cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6),
                    cursor.getString(7),cursor.getString(8),cursor.getString(16)));

    }
}
