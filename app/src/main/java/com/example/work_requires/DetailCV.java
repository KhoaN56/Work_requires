package com.example.work_requires;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailCV extends AppCompatActivity {

    //Requirement
    WorkRequirement requirement;

    //User
    User user;

    //Component
    TextView date_of_birth, country, sex, email, phone, address, jobPos, degree, school, classify, experience, detail_experience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cv);
        initialize();
    }

    private void initialize() {
        Intent info = getIntent();
        user = (User) info.getSerializableExtra("user");
        date_of_birth = findViewById(R.id.date_of_birthTV);
        date_of_birth.setText(user.getDate_of_birth());
        country = findViewById(R.id.countryTV);
        country.setText(user.getCountry());
        sex = findViewById(R.id.sexTV);
        sex.setText(user.getSex());
        email = findViewById(R.id.emailTV);
        email.setText(user.getEmail());

        phone = findViewById(R.id.phoneTV);
        phone.setText(user.getPhone());
        address = findViewById(R.id.addressTV);
        address.setText(user.getAddress());
        jobPos = findViewById(R.id.jobPosTV);
        jobPos.setText(user.getJobPos());
        degree = findViewById(R.id.degreeTV);
        degree.setText(user.getDegree());

        school = findViewById(R.id.schoolTV);
        school.setText(user.getSchool());
        classify = findViewById(R.id.classifyTV);
        classify.setText(user.getClassify());

        experience = findViewById(R.id.experienceTV);
        if(user.getExperience().equals("0"))
            experience.setText("Chưa có kinh nghiệm");
        else
            experience.setText(user.getExperience()+"năm");

        detail_experience = findViewById(R.id.detail_experienceTV);
        detail_experience.setText(user.getDetail_experience());
    }
}
