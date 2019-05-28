package com.example.work_requires;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RequirementDetail extends AppCompatActivity {

    //State
    final boolean REGISTERED = true;
    final boolean UNREGISTERED = false;
    boolean state = UNREGISTERED;

    //Requirement
    WorkRequirement requirement;

    //User
    User user;

    //Component
    TextView majorTV, areaTV, salaryTV, degreeTV, workPosTV, expTV, descriptionTV, requireTV, benefitTV,
    endDateTV, jobName, compName;
    Button subscribe;

    //Database
    SQLiteManagement database;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_detail);
        initialize();
    }

    private void initialize() {
        Intent info = getIntent();
        requirement = (WorkRequirement) info.getSerializableExtra("requirement");
        user = (User) info.getSerializableExtra("user");
        database = new SQLiteManagement(RequirementDetail.this, "Work_Requirement.sqlite", null, 1);
        cursor = database.getDatasql("SELECT * FROM DETAIL WHERE USERNAME = " +
                "'"+user.getUsername()+"' AND Id_Recruitment = '"+requirement.getId()+"'");
        if(cursor.getCount() > 0)
            state = REGISTERED;
        jobName = findViewById(R.id.jobName);
        jobName.setText(requirement.getJobName());
        compName = findViewById(R.id.compName);
        compName.setText(requirement.getCompanyName());
        majorTV = findViewById(R.id.majorTV);
        majorTV.setText(requirement.getMajor());
        areaTV = findViewById(R.id.areaTV);
        areaTV.setText(requirement.getArea());
        salaryTV = findViewById(R.id.salaryTV);
        salaryTV.setText(String.valueOf(requirement.getSalary()));
        degreeTV = findViewById(R.id.degreeTV);
        degreeTV.setText(requirement.getDegree());
        workPosTV = findViewById(R.id.workPosTV);
        workPosTV.setText(requirement.getWorkPos());
        expTV = findViewById(R.id.experienceTV);
        expTV.setText(requirement.getExperience()==0?"Chưa có kinh nghiệm" :requirement.getExperience()+ " năm");
        descriptionTV = findViewById(R.id.descriptionTV);
        descriptionTV.setText(requirement.getDescription());
        requireTV = findViewById(R.id.requirementTV);
        requireTV.setText(requirement.getRequirement());
        benefitTV = findViewById(R.id.benefitTV);
        benefitTV.setText(requirement.getBenefit());
        endDateTV = findViewById(R.id.endDateTV);
        endDateTV.setText(requirement.getEndDate());
        subscribe = findViewById(R.id.subscribe);
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == REGISTERED)
                    Toast.makeText(RequirementDetail.this, "Bạn đã đăng ký công việc này rồi!!",
                            Toast.LENGTH_SHORT).show();
                else {
                    database.insert(requirement.getId(), user.getUsername());
                    Toast.makeText(RequirementDetail.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    //Thêm người đăng ký xin việc
                    requirement.setApplied(requirement.getApplied() + 1);
                    database.update(requirement.getApplied(), requirement);
                    state = REGISTERED;
                }
            }
        });
        cursor.close();
    }
}
