package com.example.work_requires;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.work_requires.models.CVInterview;
import com.example.work_requires.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPersonalCV extends AppCompatActivity {

    private DatabaseReference database;

    EditText txt_experience, txt_date_of_birth, txt_school , txt_detail_experience;
    Spinner spn_degree, spn_sex, spn_classify, spn_major, spn_jobPos;
    Button btnDone;
    String jobPos,date_of_birth, school, major, detail_experience, degree, sex, classify;
    int experience;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_cv);
        initialize();
        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });
    }

    public void initialize(){
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        CVInterview cv = user.getCv();
        txt_detail_experience = findViewById(R.id.txt_detail_experience);
        txt_detail_experience.setText(cv.getDetail_experience());
        txt_school = findViewById(R.id.txt_school);
        txt_school.setText(cv.getSchool());
        txt_date_of_birth = findViewById(R.id.txt_date_of_birth);
        txt_date_of_birth.setText(cv.getBirthDay());
        txt_experience = findViewById(R.id.txt_experience);
        txt_experience.setText(cv.getExperience());
        spn_jobPos = findViewById(R.id.spn_jobPos);
        spn_classify = findViewById(R.id.spn_classify);
        spn_degree = findViewById(R.id.spn_degree);
        spn_sex = findViewById(R.id.spn_sex);
        spn_major = findViewById(R.id.spn_major);

        database = FirebaseDatabase.getInstance().getReference();
        final List<String> listDegree = getLists("Degree", spn_degree);
        spn_degree.setSelection(listDegree.indexOf(cv.getDegree()));
        spn_degree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                degree= listDegree.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                degree="";
            }
        });

        final List<String> jobPosList = getLists("Position", spn_jobPos);
        spn_jobPos.setSelection(jobPosList.indexOf(cv.getJobPos()));
        spn_jobPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jobPos = jobPosList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                degree="";
            }
        });


        final List <String> listSex = new ArrayList<>();
        listSex.add("Nam");
        listSex.add("Nữ");
        listSex.add("Khác");
        spn_sex.setSelection(listSex.indexOf(cv.getSex()));
        ArrayAdapter<String> adapterSex= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listSex);
        adapterSex.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_sex.setAdapter(adapterSex);
        spn_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex= listSex.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sex="";
            }
        });

        final List <String> listMajor = getLists("Major", spn_major);
        spn_major.setSelection(listMajor.indexOf(cv.getMajor()));
        spn_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                major= listMajor.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                major="";
            }
        });

        final List <String> listClassify = new ArrayList<>();
        listClassify.add("Xuất sắc");
        listClassify.add("Giỏi");
        listClassify.add("Khá");
        listClassify.add("Trung bình");
        listClassify.add("Yếu");
        ArrayAdapter<String> adapterClassify= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listClassify);
        adapterClassify.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_classify.setAdapter(adapterClassify);
        spn_classify.setSelection(listClassify.indexOf(cv.getClassify()));
        spn_classify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classify= listClassify.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                classify="";
            }
        });

    }
    private void alert(String error) {
        Toast.makeText(ViewPersonalCV.this, error, Toast.LENGTH_LONG).show();
    }
    private boolean checked() {
        date_of_birth= txt_date_of_birth.getText().toString();
        school= txt_school.getText().toString();
        detail_experience= txt_detail_experience.getText().toString();
        experience= Integer.parseInt(txt_experience.getText().toString());
        if (jobPos.equals("") || date_of_birth.equals("") || school.equals("") || String.valueOf(experience).equals("") ||
                major.equals("") || detail_experience.equals("") || degree.equals("") || sex.equals("") || classify.equals(""))
        {
            alert("Bạn chưa nhập đủ thông tin!!");
            return false;
        }
        return true;
    }
    public void updateInfo()
    {
        if(checked()){
            alert("Đăng ký thành công!!");
            user.setCv(new CVInterview(jobPos, degree, experience, date_of_birth, sex, school, major,
                    classify, detail_experience));
            user.upDateCV();
            Intent intent2 = new Intent(ViewPersonalCV.this, MainActivity.class);
            intent2.putExtra("user", user);
            startActivity(intent2);
        }
    }

    private List<String> getLists(String child, final Spinner spinner){
        final List<String>list = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference();
        database.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot p : dataSnapshot.getChildren())
                    list.add(p.getValue(String.class));
                ArrayAdapter<String>adapter = new ArrayAdapter<>(ViewPersonalCV.this, R.layout.support_simple_spinner_dropdown_item, list);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
}
