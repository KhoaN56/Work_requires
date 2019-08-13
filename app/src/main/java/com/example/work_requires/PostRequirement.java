package com.example.work_requires;

//import android.annotation.SuppressLint;
import android.content.DialogInterface;
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

import com.example.work_requires.models.Company;
import com.example.work_requires.models.WorkRequirement;
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
import java.util.List;

//@SuppressLint("Registered")
public class PostRequirement extends AppCompatActivity {
    EditText jobName;
    EditText salary;
    EditText experience, amount;
    Spinner major, city, degree, workPos, spn_district;
    EditText endDate;
    EditText requirement, description, benefit;
    Button postButton;
    String salaryTxt="", expTxt="", cityTxt ="", majorTxt ="", degreeTxt ="", workPosTxt ="", district = "",
            endDateTxt="", jobNameTxt="",requirementTxt="",descriptionTxt="",benefitTxt="", amountTxt="";
//    SQLiteManagement workRequireDatabase;
    Company user;
    Date today;
    final String pattern = "dd/MM/yyyy";
    SimpleDateFormat dateFormat;

    //List of data
    List<String>cityList;
    List<String>districtList;
    List<String>degreeList;
    List<String>majorList;
    List<String>workPosList;


//    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_requirement);
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        today = new Date(System.currentTimeMillis());
        dateFormat = new SimpleDateFormat(pattern);
        salary = findViewById(R.id.editText1);
        experience = findViewById(R.id.editText4);
        endDate = findViewById(R.id.editText6);
        major = findViewById(R.id.spn_major);
        city = findViewById(R.id.spn_city);
        spn_district = findViewById(R.id.spn_district);
        degree = findViewById(R.id.spinner3);
        workPos = findViewById(R.id.spinner4);
        postButton = findViewById(R.id.Dangtin);
        requirement = findViewById(R.id.requirement);
        description = findViewById(R.id.description);
        benefit = findViewById(R.id.benefit);
        jobName = findViewById(R.id.txt_title);
        amount = findViewById(R.id.amount);

        user = (Company) getIntent().getSerializableExtra("user");


        cityList = getLists("/Areas/Cities", city);

        degreeList = getLists("/Degree",degree);


        majorList = getLists("/Major", major);


        workPosList = getLists("/Position", workPos);


        final List<String> cityKeyList = getKeyLists();

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityTxt = cityList.get(position);
                String path = "/Areas/District/" + cityKeyList.get(position);
                districtList = getLists(path, spn_district);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cityTxt ="";
            }
        });

        spn_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                district = districtList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                district = "";
            }
        });

        degree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                degreeTxt = degreeList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                degreeTxt ="";
            }
        });

        major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majorTxt = majorList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                majorTxt ="";
            }
        });

        workPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workPosTxt = workPosList.get(position);
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
                    Toast.makeText(PostRequirement.this, "Bạn chưa đủ nhập thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!checkDate(endDate.getText().toString().trim())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostRequirement.this);
                    builder.setTitle("Nhắc nhở");
                    builder.setMessage("Ngày kết thúc phải lớn hơn ngày hiện tại");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PostRequirement.this.onResume();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return;
                }
                WorkRequirement requirement;
                requirement = new WorkRequirement(jobNameTxt, majorTxt, cityTxt, district,Long.parseLong(salaryTxt), degreeTxt, workPosTxt,
                        Integer.parseInt(expTxt), Integer.parseInt(amountTxt),descriptionTxt,
                        requirementTxt, benefitTxt, endDateTxt, user.getName(), user.getUserId());
                requirement.setApplied(0);
                requirement.postRequirement();
//                user.addJob(requirement.getId());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                        .getReference("/Company/"+user.getUserId()+"/jobPosted/"+user.getJobPosted().size());
                databaseReference.setValue(requirement.getId());
//                user.addJob();
                Toast.makeText(PostRequirement.this, "Đăng tin thành công!!", Toast.LENGTH_SHORT).show();
//                finish();
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
        jobNameTxt = jobName.getText().toString().trim();
        salaryTxt = salary.getText().toString().trim();
        expTxt = experience.getText().toString().trim();
        endDateTxt = endDate.getText().toString().trim();
        requirementTxt = requirement.getText().toString().trim();
        descriptionTxt = description.getText().toString().trim();
        benefitTxt = benefit.getText().toString().trim();
        amountTxt = amount.getText().toString().trim();
        return (!(jobNameTxt.equals("")||salaryTxt.equals("")||expTxt.equals("")|| amountTxt.equals("") ||
                endDateTxt.equals("")||requirementTxt.equals("")||descriptionTxt.equals("")||
                benefitTxt.equals("")||cityTxt.equals("")||district.equals("")));
    }

    private List<String> getLists(String path, final Spinner spinner){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference(path);
        final List<String>list = new ArrayList<>();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot p : dataSnapshot.getChildren())
                    list.add(p.getValue(String.class));
                ArrayAdapter<String>adapter = new ArrayAdapter<>(PostRequirement.this,
                        R.layout.support_simple_spinner_dropdown_item, list);
                spinner.setAdapter(adapter);
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

}
