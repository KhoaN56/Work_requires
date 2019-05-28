package com.example.work_requires;

import android.content.DialogInterface;
import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateRequirement extends AppCompatActivity {

    EditText title, salary, amount, requirement, experience, description, benifit, end_date;
    Spinner major, area, degree,workPos ;
    WorkRequirement workRequirement;
    String salaryTxt="", expTxt="", areaTxt ="", majorTxt ="", degreeTxt ="", workPosTxt ="",
            endDateTxt="", jobNameTxt="",requirementTxt="",descriptionTxt="",benefitTxt="", amountTxt="";
    
    Button btn_save;
    Date today;
    final String pattern = "dd/MM/yyyy";
    SimpleDateFormat dateFormat;

    SQLiteManagement management;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_requirement);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initialize();
    }

    public void initialize() {
        Intent info = getIntent();
        workRequirement = (WorkRequirement) info.getSerializableExtra("work");

        today = new Date(System.currentTimeMillis());
        dateFormat = new SimpleDateFormat(pattern);


        salary= findViewById(R.id.salary);
        amount= findViewById(R.id.amount);
        requirement= findViewById(R.id.requirement);
        experience= findViewById(R.id.experience);
        description= findViewById(R.id.description);
        benifit= findViewById(R.id.benefit);
        end_date= findViewById(R.id.enddate);

        title = findViewById(R.id.title);
        major = findViewById(R.id.spn_major);
        area = findViewById(R.id.spn_area);
        degree = findViewById(R.id.spn_degree);
        workPos = findViewById(R.id.spn_jobPos);
        btn_save = findViewById(R.id.btn_save);

        final List<String> listArea = new ArrayList<>();
        listArea.add("Hồ Chí Minh");
        listArea.add("Hà Nội");

        final List<String> listDegree = new ArrayList<>();
        listDegree.add("Trung cấp");
        listDegree.add("Đại học");

        final List<String> listMajor = new ArrayList<>();
        listMajor.add("Thực phẩm");
        listMajor.add("Xây dựng");
        listMajor.add("Công nghệ thông tin");
        listMajor.add("Bán hàng");

        final List<String> listWorkPos = new ArrayList<>();
        listWorkPos.add("Thực tập và dự án");
        listWorkPos.add("Nhân viên chính thức");
        listWorkPos.add("Nhân viên thời vụ");
        listWorkPos.add("Làm thêm ngoài giờ");
        listWorkPos.add("Bán thời gian");


        ArrayAdapter<String> adapterArea = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listArea);
        adapterArea.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        area.setAdapter(adapterArea);
        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaTxt = listArea.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                areaTxt = "";
            }
        });
        area.setSelection(listArea.indexOf(workRequirement.getArea()));

        ArrayAdapter<String> adapterDegree = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listDegree);
        adapterDegree.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        degree.setAdapter(adapterDegree);
        degree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                degreeTxt = listDegree.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                degreeTxt = "";
            }
        });
        degree.setSelection(listDegree.indexOf(workRequirement.getDegree()));

        ArrayAdapter<String> adapterWorkPos = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listWorkPos);
        adapterWorkPos.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        workPos.setAdapter(adapterWorkPos);
        workPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workPosTxt = listWorkPos.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                workPosTxt = "";
            }
        });
        workPos.setSelection(listWorkPos.indexOf(workRequirement.getWorkPos()));

        ArrayAdapter<String> adapterMajor = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listMajor);
        adapterMajor.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        major.setAdapter(adapterMajor);
        major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majorTxt = listMajor.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                majorTxt = "";
            }
        });
        major.setSelection(listMajor.indexOf(workRequirement.getMajor()));

        title.setText(workRequirement.getJobName());
        salary.setText(workRequirement.getSalary()+"");
        amount.setText(workRequirement.getAmount()+"");
        requirement.setText(workRequirement.getRequirement());
        experience.setText(workRequirement.getExperience()+"");
        description.setText(workRequirement.getDescription());
        benifit.setText(workRequirement.getBenefit());
        end_date.setText(workRequirement.getEndDate());
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNull()) {
                    Toast.makeText(UpdateRequirement.this, "Bạn chưa nhập thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkDate(end_date.getText().toString().trim())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateRequirement.this);
                    builder.setTitle("Nhắc nhở");
                    builder.setMessage("Ngày kết thúc phải lớn hơn ngày hiện tại");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UpdateRequirement.this.onResume();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return;
                }
                workRequirement.setJobName(jobNameTxt);
                workRequirement.setMajor(majorTxt);
                workRequirement.setArea(areaTxt);
                workRequirement.setSalary(Integer.valueOf(salaryTxt));
                workRequirement.setDegree(degreeTxt);
                workRequirement.setWorkPos(workPosTxt);
                workRequirement.setAmount(Integer.valueOf(amountTxt));
                workRequirement.setRequirement(requirementTxt);
                workRequirement.setExperience(Integer.valueOf(expTxt));
                workRequirement.setDescription(descriptionTxt);
                workRequirement.setBenefit(benefitTxt);
                workRequirement.setEndDate(endDateTxt);

                management= new SQLiteManagement(UpdateRequirement.this, "Work_Requirement.sqlite", null, 1);
                management.update(workRequirement);
                Toast.makeText(UpdateRequirement.this, "Sửa tin thành công!!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
    private boolean checkDate(String inputDate) {
        try{
            Date iDate = dateFormat.parse(inputDate);
            return (today.compareTo(iDate) < 0);    // < 0 la han dang ky sau ngay hien tai
        }catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }
    private boolean checkNull() {
        jobNameTxt = title.getText().toString().trim();
        salaryTxt = salary.getText().toString().trim();
        expTxt = experience.getText().toString().trim();
        endDateTxt = end_date.getText().toString().trim();
        requirementTxt = requirement.getText().toString().trim();
        descriptionTxt = description.getText().toString().trim();
        benefitTxt = benifit.getText().toString().trim();
        amountTxt = amount.getText().toString().trim();
        return (!(jobNameTxt.equals("")||salaryTxt.equals("")||expTxt.equals("")|| amountTxt.equals("") ||
                endDateTxt.equals("")||requirementTxt.equals("")||descriptionTxt.equals("")||benefitTxt.equals("")));
    }
}
