package com.example.work_requires;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OnlineCV extends AppCompatActivity {

    EditText txt_jobPos, txt_experience, txt_date_of_birth, txt_school , txt_detial_experience;
    Spinner spn_degree, spn_country, spn_sex, spn_classify, spn_major;
    Button btnDone;
    String jobPos,date_of_birth, school, major, detail_experience, degree, country, sex, classify;
    int experience;
    User user;
    SQLiteManagement sqLiteManagement;
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

        txt_jobPos = findViewById(R.id.txt_jobPos);
        txt_detial_experience = findViewById(R.id.txt_detail_experience);
        txt_school = findViewById(R.id.txt_school);
        txt_date_of_birth = findViewById(R.id.txt_date_of_birth);
        txt_experience = findViewById(R.id.txt_experience);
        spn_country = findViewById(R.id.spn_country);
        spn_classify = findViewById(R.id.spn_classify);
        spn_degree = findViewById(R.id.spn_degree);
        spn_sex = findViewById(R.id.spn_sex);
        spn_major = findViewById(R.id.spn_major);


        final List<String> listDegree = new ArrayList<>();
        listDegree.add("Đại học");
        listDegree.add("Cao đẳng");
        listDegree.add("Trung cấp");
        listDegree.add("Cao học");
        listDegree.add("Trung học");
        listDegree.add("Chứng chỉ");
        listDegree.add("Lao động phổ thông");

        ArrayAdapter<String> adapterDegree= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listDegree);
        adapterDegree.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_degree.setAdapter(adapterDegree);
        spn_degree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener
                () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                degree= listDegree.get(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                degree="";
            }
        });

        final List<String> listCountry = new ArrayList<>();
        listCountry.add("Việt Nam");
        listCountry.add("Hàn Quốc");
        listCountry.add("Nhật bản");

        ArrayAdapter<String> adapterCountry= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listCountry);
        adapterCountry.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_country.setAdapter(adapterCountry);
        spn_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener
                () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country= listCountry.get(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                country="";
            }
        });

        final List <String> listSex = new ArrayList<>();
        listSex.add("Nam");
        listSex.add("Nữ");
        listSex.add("Khác");

        ArrayAdapter<String> adapterSex= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listSex);
        adapterSex.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_sex.setAdapter(adapterSex);
        spn_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex= listSex.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sex="";
            }
        });

        final List <String> listMajor = new ArrayList<>();
        listMajor.add("Thực phẩm");
        listMajor.add("Xây dựng");
        listMajor.add("Công nghệ thông tin");
        listMajor.add("Bán hàng");

        ArrayAdapter<String> adapterMajor= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listMajor);
        adapterMajor.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_major.setAdapter(adapterMajor);
        spn_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                major= listMajor.get(position).toString();
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

        ArrayAdapter<String> adapterClassify= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listClassify);
        adapterClassify.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_classify.setAdapter(adapterClassify);
        spn_classify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classify= listClassify.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                classify="";
            }
        });

    }
    private void alert(String error) {
        Toast.makeText(OnlineCV.this, error, Toast.LENGTH_LONG).show();
    }
    private boolean checked() {
        int count = 0;
        jobPos = txt_jobPos.getText().toString();
        date_of_birth= txt_date_of_birth.getText().toString();
        school= txt_school.getText().toString();
        detail_experience= txt_detial_experience.getText().toString();
        experience= Integer.parseInt(txt_experience.getText().toString());
        if (jobPos.equals("") || date_of_birth.equals("") || school.equals("") || String.valueOf(experience).equals("") ||
                major.equals("") || detail_experience.equals("") || degree.equals("") || country.equals("") ||
                sex.equals("") || classify.equals(""))
        {
            alert("Bạn chưa nhập đủ thông tin!!");
            return false;
        }
        return true;
    }
    public void updateInfo()
    {
        if(checked())
        {
            sqLiteManagement = new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);
            sqLiteManagement.update(jobPos, degree,experience, date_of_birth, country, sex, school, major, classify, detail_experience, user.getUsername());
            alert("Đăng ký thành công!!");

            user.setUser(jobPos, degree, experience, date_of_birth, country, sex, school, major, classify, detail_experience);
            Intent intent2 = new Intent(OnlineCV.this, MainActivity.class);
            intent2.putExtra("user", user);
            startActivity(intent2);
        }
    }
}
