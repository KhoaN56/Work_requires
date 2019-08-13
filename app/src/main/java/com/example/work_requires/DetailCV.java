package com.example.work_requires;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//import com.example.work_requires.models.WorkRequirement;
import com.example.work_requires.models.CVInterview;
import com.example.work_requires.models.Company;
import com.example.work_requires.models.Noti;
import com.example.work_requires.models.User;
import com.example.work_requires.models.WorkRequirement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        Intent info = getIntent();
        user = (User) info.getSerializableExtra("user");
        DatabaseReference getDetail = FirebaseDatabase.getInstance()
                .getReference("/Users/Detail/"+user.getUserId());
        getDetail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                initialize();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        Intent intent = getIntent();
        Company company = (Company) intent.getSerializableExtra("company");
        String jobId = intent.getStringExtra("requirement");
        if(!intent.getBooleanExtra("getState", false)){
            Date m = new Date(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
            Noti notification = new Noti(jobId, company.getName(), 1, dateFormat.format(m), false);
            notification.send("/Users/Notifications/"+user.getUserId());
        }
        super.onStart();
    }

    private View.OnClickListener sendMail = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_SEND);
//            intent.setData(Uri.parse("mailto:"));
//            intent.setType("text/plain");
//            intent.putExtra("",);
            Intent mailClient = new Intent(Intent.ACTION_VIEW);
            mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
            startActivity(mailClient);
        }
    };

    private void initialize() {
//        Intent info = getIntent();
//        user = (User) info.getSerializableExtra("user");
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
        email.setOnClickListener(sendMail);
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
