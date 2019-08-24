package com.example.work_requires;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.example.work_requires.models.DataHolder;
import com.example.work_requires.models.WorkRequirement;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateRequirement extends AppCompatActivity {

    EditText title, salary, amount, requirement, experience, description, benefit, end_date;
    Spinner major, city, degree, workPos;
    WorkRequirement workRequirement;
    String salaryTxt="", expTxt="", cityTxt ="", majorTxt ="", degreeTxt ="", workPosTxt ="", endDateTxt="",
            jobNameTxt="",requirementTxt="",descriptionTxt="",benefitTxt="", amountTxt="";
    
    Button btn_save;
    Date today;
    final String pattern = "dd/MM/yyyy";
    SimpleDateFormat dateFormat;

    //List of data
    List<String>cityList;
    List<String>degreeList;
    List<String>majorList;
    List<String>workPosList;

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
//        Intent info = getIntent();
        workRequirement = DataHolder.getJob();

        today = new Date(System.currentTimeMillis());
        dateFormat = new SimpleDateFormat(pattern);


        salary= findViewById(R.id.salary);
        amount= findViewById(R.id.amount);
        requirement= findViewById(R.id.requirement);
        experience= findViewById(R.id.experience);
        description= findViewById(R.id.description);
        benefit = findViewById(R.id.benefit);
        end_date= findViewById(R.id.enddate);
        title = findViewById(R.id.title);
        major = findViewById(R.id.spn_major);
        city = findViewById(R.id.spn_city);
        degree = findViewById(R.id.spn_degree);
        workPos = findViewById(R.id.spn_jobPos);
        btn_save = findViewById(R.id.btn_save);

        cityList = getLists("/Areas/Cities", city, workRequirement.getCity());

        degreeList = getLists("/Degree",degree, workRequirement.getDegree());

        majorList = getLists("/Major", major, workRequirement.getMajor());

        workPosList = getLists("/Position", workPos, workRequirement.getWorkPos());

        city.setSelection(cityList.indexOf(cityTxt));
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityTxt = cityList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cityTxt = workRequirement.getCity();
            }
        });

        degree.setSelection(degreeList.indexOf(workRequirement.getDegree()));
        degree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                degreeTxt = degreeList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                degreeTxt = workRequirement.getDegree();
            }
        });

        workPos.setSelection(workPosList.indexOf(workRequirement.getWorkPos()));
        workPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workPosTxt = workPosList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                workPosTxt = workRequirement.getWorkPos();
            }
        });

        major.setSelection(majorList.indexOf(workRequirement.getMajor()));
        major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majorTxt = majorList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                majorTxt = workRequirement.getMajor();
            }
        });

        title.setText(workRequirement.getJobName());
        salary.setText(String.valueOf(workRequirement.getSalary()));
        amount.setText(String.valueOf(workRequirement.getAmount()));
        requirement.setText(workRequirement.getRequirement());
        experience.setText(String.valueOf(workRequirement.getExperience()));
        description.setText(workRequirement.getDescription());
        benefit.setText(workRequirement.getBenefit());
        end_date.setText(workRequirement.getEndDate());
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkNull()){
                    Toast.makeText(UpdateRequirement.this, "Bạn có thông tin chưa nhập", Toast.LENGTH_SHORT).show();
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
                    end_date.requestFocus();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateRequirement.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn lưu thay đổi không?");
                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        workRequirement.setJobName(jobNameTxt);
                        workRequirement.setMajor(majorTxt);
                        workRequirement.setCity(cityTxt);
                        workRequirement.setSalary(Long.parseLong(salaryTxt));
                        workRequirement.setDegree(degreeTxt);
                        workRequirement.setWorkPos(workPosTxt);
                        workRequirement.setAmount(Integer.parseInt(amountTxt));
                        workRequirement.setRequirement(requirementTxt);
                        workRequirement.setExperience(Integer.parseInt(expTxt));
                        workRequirement.setDescription(descriptionTxt);
                        workRequirement.setBenefit(benefitTxt);
                        workRequirement.setEndDate(endDateTxt);
                        Map<String, Object>jobItem = workRequirement.toJobItem();
                        Map<String, Object> childUpdate = new HashMap<>();
                        childUpdate.put("/Jobs/Job List/"+workRequirement.getId(),jobItem);
                        childUpdate.put("/Jobs/Detail/"+workRequirement.getId(), workRequirement);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        reference.updateChildren(childUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateRequirement.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateRequirement.this, "Xảy ra lỗi trong quá trình xử lí", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Toast.makeText(UpdateRequirement.this, "Sửa tin thành công!!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onResume();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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

    private List<String> getLists(String path, final Spinner spinner, final String object){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference(path);
        final List<String>list = new ArrayList<>();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot p : dataSnapshot.getChildren())
                    list.add(p.getValue(String.class));
                ArrayAdapter<String>adapter = new ArrayAdapter<>(UpdateRequirement.this,
                        R.layout.support_simple_spinner_dropdown_item, list);
                spinner.setAdapter(adapter);
                spinner.setSelection(list.indexOf(object));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    private List<String> getKeyLists(){
        final List<String>keyList = new ArrayList<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Areas").child("Cities").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                keyList.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return keyList;
    }

    private boolean checkNull() {
        jobNameTxt = title.getText().toString().trim();
        salaryTxt = salary.getText().toString().trim();
        expTxt = experience.getText().toString().trim();
        endDateTxt = end_date.getText().toString().trim();
        requirementTxt = requirement.getText().toString().trim();
        descriptionTxt = description.getText().toString().trim();
        benefitTxt = benefit.getText().toString().trim();
        amountTxt = amount.getText().toString().trim();
        return (!(jobNameTxt.equals("")||salaryTxt.equals("")||expTxt.equals("")|| amountTxt.equals("") ||
                endDateTxt.equals("")||requirementTxt.equals("")||descriptionTxt.equals("")||benefitTxt.equals("")));
    }
}
