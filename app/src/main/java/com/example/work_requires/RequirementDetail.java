package com.example.work_requires;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RequirementDetail extends AppCompatActivity {

    //Requirement
    WorkRequirement requirement;

    //Component
    TextView majorTV, areaTV, salaryTV, degreeTV, workPosTV, expTV, descriptionTV, requireTV, benefitTV,
    endDateTV;
    Button subscribe;

    //Database
    SQLiteManagement database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_detail);
        initialize();
    }

    private void initialize() {
        database = new SQLiteManagement(RequirementDetail.this, "Work_Requirement.sqlite", null, 1);
        database.queryData("CREATE TABLE IF NOT EXISTS DETAIL (ID_DETAIL INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, Username CHAR(20), Id_Requirement INTEGER)");
        Intent info = getIntent();
        requirement = (WorkRequirement) info.getSerializableExtra("requirement");
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
        expTV.setText(requirement.getExperience());
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

            }
        });
    }
}
