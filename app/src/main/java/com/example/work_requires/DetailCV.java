package com.example.work_requires;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

//import com.example.work_requires.models.WorkRequirement;
import com.example.work_requires.models.CVInterview;
import com.example.work_requires.models.User;

public class DetailCV extends AppCompatActivity {

    //Requirement
//    WorkRequirement requirement;
//    SQLiteManagement managementDatabse;

    //User
    User user;

    //Component
    TextView date_of_birth, sex, email, phone, jobPos, degree, school, classify, experience,
            detail_experience, name, major, city, district;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cv);
        initialize();
    }

    private void initialize() {
        Intent info = getIntent();
        user = (User) info.getSerializableExtra("user");
//        managementDatabse= new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);
//        Cursor cursor = managementDatabse.getDatasql("select * from user where str_email='"+user.getUsername()+"'");
//        if(cursor.moveToNext())
        CVInterview cv = user.getCv();
        int exp = cv.getExperience();
        name = findViewById(R.id.name);
        name.setText(user.getName());
        major = findViewById(R.id.majorTV);
        major.setText(cv.getMajor());
        date_of_birth = findViewById(R.id.date_of_birthTV);
        date_of_birth.setText(cv.getBirthDay());
        city = findViewById(R.id.city);
        city.setText(user.getCity());
        sex = findViewById(R.id.sexTV);
        sex.setText(cv.getSex());
        email = findViewById(R.id.emailTV);
        email.setText(user.getEmail());
        phone = findViewById(R.id.phoneTV);
        phone.setText(user.getPhone());
        district = findViewById(R.id.districtTV);
        district.setText(user.getDistrict());
        jobPos = findViewById(R.id.jobPosTV);
        jobPos.setText(cv.getJobPos());
        degree = findViewById(R.id.degreeTV);
        degree.setText(cv.getDegree());
        school = findViewById(R.id.schoolTV);
        school.setText(cv.getSchool());
        classify = findViewById(R.id.classifyTV);
        classify.setText(cv.getClassify());
        experience = findViewById(R.id.experienceTV);
        experience.setText(exp == 0?"Chưa có kinh nghiệm":exp+" năm");
        detail_experience = findViewById(R.id.detail_experienceTV);
        detail_experience.setText(cv.getDetail_experience());

    }
}
