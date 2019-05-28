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
    SQLiteManagement managementDatabse;

    //User
    User user;

    //Component
    TextView date_of_birth, country, sex, email, phone, address, jobPos, degree, school, classify, experience, detail_experience;
    TextView name, major;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cv);
        initialize();
    }

    private void initialize() {
        Intent info = getIntent();
        user = (User) info.getSerializableExtra("user");
        managementDatabse= new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);
        Cursor cursor = managementDatabse.getDatasql("select * from user where username='"+user.getUsername()+"'");
        if(cursor.moveToNext())
        {
            name = findViewById(R.id.name);
            name.setText(cursor.getString(3));
            major = findViewById(R.id.majorTV);
            major.setText(cursor.getString(16));

            date_of_birth = findViewById(R.id.date_of_birthTV);
            date_of_birth.setText(cursor.getString(12));
            country = findViewById(R.id.countryTV);
            country.setText(cursor.getString(13));
            sex = findViewById(R.id.sexTV);
            sex.setText(cursor.getString(14));
            email = findViewById(R.id.emailTV);
            email.setText(cursor.getString(4));

            phone = findViewById(R.id.phoneTV);
            phone.setText(cursor.getString(5));
            address = findViewById(R.id.addressTV);
            address.setText(cursor.getString(7));
            jobPos = findViewById(R.id.jobPosTV);
            jobPos.setText(cursor.getString(9));
            degree = findViewById(R.id.degreeTV);
            degree.setText(cursor.getString(10));

            school = findViewById(R.id.schoolTV);
            school.setText(cursor.getString(15));
            classify = findViewById(R.id.classifyTV);
            classify.setText(cursor.getString(17));

            experience = findViewById(R.id.experienceTV);
            if(cursor.getDouble(11)==0)
                experience.setText("Chưa có kinh nghiệm");
            else
                experience.setText(cursor.getInt(11)+" năm");

            detail_experience = findViewById(R.id.detail_experienceTV);
            detail_experience.setText(cursor.getString(18));
        }

    }
}
