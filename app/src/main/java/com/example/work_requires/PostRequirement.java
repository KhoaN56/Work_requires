package com.example.work_requires;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
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

@SuppressLint("Registered")
public class PostRequirement extends AppCompatActivity {
    EditText jobName;
    EditText salary;
    EditText experience;
    Spinner major, area, degree, workPos;
    EditText endDate;
    EditText requirement, description, benefit;
    Button postButton;
    String salaryTxt="", expTxt="", areaTxt ="", majorTxt ="", degreeTxt ="", workPosTxt ="",
            endDateTxt="", jobNameTxt="",requirementTxt="",descriptionTxt="",benefitTxt="";
    SQLiteManagement workRequireDatabase;
    User user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_requirement);
        salary =findViewById(R.id.editText1);
        experience =findViewById(R.id.editText4);
        endDate = findViewById(R.id.editText6);
        major =findViewById(R.id.spn_major);
        area =findViewById(R.id.spn_area);
        degree =findViewById(R.id.spinner3);
        workPos =findViewById(R.id.spinner4);
        postButton = findViewById(R.id.Dangtin);
        requirement = findViewById(R.id.requirement);
        description = findViewById(R.id.description);
        benefit = findViewById(R.id.benefit);
        jobName = findViewById(R.id.txt_title);
        workRequireDatabase = new SQLiteManagement(PostRequirement.this, "Work_Requirement.sqlite", null, 1);
        workRequireDatabase.queryData("CREATE TABLE IF NOT EXISTS Recruitment(Id_Recruitment INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, Username CHAR(20), JobName CHAR(100), Major NCHAR(50), Area NCHAR(20)," +
                "Salary INTEGER, Degree CHAR(15), Position NCHAR(20), Experience INTEGER, Description VARCHAR, Requirement NVARCHAR, " +
                "Benefit NVARCHAR, End_Date CHAR(10))");
        Cursor cursor = workRequireDatabase.getDatasql("SELECT Id_Recruitment FROM Recruitment");
        final int id = cursor.getCount() + 1;
        user = (User)getIntent().getSerializableExtra("user");

        final List<String> listArea = new ArrayList<>();
        listArea.add("Hồ Chí Minh");
        listArea.add("Hà Nội");

        final List<String> listDegree = new ArrayList<>();
        listDegree.add("Trung cấp");
        listDegree.add("Đại học");

        final List <String> listMajor = new ArrayList<>();
        listMajor.add("Thực phẩm");
        listMajor.add("Xây dựng");
        listMajor.add("Công nghệ thông tin");
        listMajor.add("Bán hàng");

        final List <String> listWorkPos = new ArrayList<>();
        listWorkPos.add("Thực tập và dự án");
        listWorkPos.add("Nhân viên chính thức");
        listWorkPos.add("Nhân viên thời vụ");
        listWorkPos.add("Làm thêm ngoài giờ");
        listWorkPos.add("Bán thời gian");


        ArrayAdapter<String> adapterArea= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listArea);
        adapterArea.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        area.setAdapter(adapterArea);
        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaTxt = listArea.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                areaTxt ="";
            }
        });

        ArrayAdapter<String> adapterDegree= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listDegree);
        adapterDegree.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        degree.setAdapter(adapterDegree);
        degree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                degreeTxt = listDegree.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                degreeTxt ="";
            }
        });

        ArrayAdapter<String> adapterMajor= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listMajor);
        adapterMajor.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        major.setAdapter(adapterMajor);
        major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majorTxt = listMajor.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                majorTxt ="";
            }
        });

        ArrayAdapter<String> adapterWorkPos= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listWorkPos);
        adapterWorkPos.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        workPos.setAdapter(adapterWorkPos);
        workPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workPosTxt = listWorkPos.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                workPosTxt ="";
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkNull())
                {
                    Toast.makeText(PostRequirement.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
                WorkRequirement requirement;
                requirement = new WorkRequirement(id,
                        jobNameTxt, majorTxt, areaTxt, Long.parseLong(salaryTxt), degreeTxt, workPosTxt,
                        Integer.parseInt(expTxt), descriptionTxt, requirementTxt, benefitTxt,endDateTxt, user.getName());
                workRequireDatabase.insert(requirement, user);
                confirmation();
            }
        });
    }

    private void confirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PostRequirement.this);
        builder.setMessage("Đăng tin thành công! Bạn có muốn tiếp tục không?");
        builder.setTitle("Xác nhận");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onRestart();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent toMain = new Intent(PostRequirement.this, MainActivityCompany.class);
                toMain.putExtra("user", user);
                startActivity(toMain);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean checkNull() {
        jobNameTxt = jobName.getText().toString().trim();
        salaryTxt = salary.getText().toString().trim();
        expTxt = experience.getText().toString().trim();
        endDateTxt = endDate.getText().toString().trim();
        requirementTxt = requirement.getText().toString().trim();
        descriptionTxt = description.getText().toString().trim();
        benefitTxt = benefit.getText().toString().trim();
        return (!(jobNameTxt.equals("")||salaryTxt.equals("")||expTxt.equals("")||
                endDateTxt.equals("")||requirementTxt.equals("")||descriptionTxt.equals("")||benefitTxt.equals("")));
    }

}
