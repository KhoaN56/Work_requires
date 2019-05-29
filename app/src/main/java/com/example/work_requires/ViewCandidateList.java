package com.example.work_requires;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewCandidateList extends AppCompatActivity {

    //Title
    TextView title;

    //Database
    SQLiteManagement database;

    //User
    User user;
    User dummyUser;
    List<User> userList;

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
        user = (User)getIntent().getSerializableExtra("user");
        requirement = (WorkRequirement)getIntent().getSerializableExtra("requirement");
        title = findViewById(R.id.title);
        title.setText(requirement.getJobName());
        database = new SQLiteManagement(ViewCandidateList.this, "Work_Requirement.sqlite",null, 1);
        userList = new ArrayList<>();
        Cursor cursor = database.getDatasql("SELECT USER.* FROM USER, DETAIL WHERE DETAIL.ID_RECRUITMENT = " +
                "'"+requirement.getId()+"' AND USER.USERNAME = DETAIL.USERNAME");
        while(cursor.moveToNext()){
            userList.add(new User(cursor.getString(0),cursor.getString(2), cursor.getString(1),
                    cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6),
                    cursor.getString(7),cursor.getString(8),cursor.getString(16)));
            dummyUser = userList.get(userList.size()-1);
            dummyUser.setUser(cursor.getString(10), cursor.getString(11),
                    cursor.getInt(12), cursor.getString(13),
                    cursor.getString(14),cursor.getString(15),
                    cursor.getString(16),cursor.getString(17),
                    cursor.getString(18), cursor.getString(19));
            userList.set(userList.size()-1,dummyUser);
        }
        setUpRecyclerView();
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
                Intent toCandidateDetail = new Intent(ViewCandidateList.this, DetailCV.class);
                toCandidateDetail.putExtra("user", userList.get(position));
                startActivity(toCandidateDetail);
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void deleteCandidate(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewCandidateList.this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc muốn người này?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.delete(userList.get(position), requirement.getId());
                requirement.setApplied(requirement.getApplied()-1);
                database.update(requirement.getApplied(),requirement);
                userList.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(ViewCandidateList.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}