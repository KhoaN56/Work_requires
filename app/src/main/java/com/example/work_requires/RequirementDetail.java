package com.example.work_requires;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.work_requires.models.Noti;
import com.example.work_requires.models.WorkRequirement;
import com.example.work_requires.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequirementDetail extends AppCompatActivity {

    //State
    final boolean REGISTERED = true;
    final boolean UNREGISTERED = false;
    final boolean SAVED = true;
    final boolean UNSAVED = false;
    boolean registerState;
    boolean saveState;

    //Requirement
    WorkRequirement requirement;

    //User
    User user;

    //Component
    TextView majorTV, cityTV, salaryTV, degreeTV, workPosTV, expTV, descriptionTV, requireTV, benefitTV,
    endDateTV, jobName, compName, districtTV;
    Button subscribe, saveBtn;

    //Database
//    SQLiteManagement database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_detail);
        Intent info = getIntent();
        user = (User) info.getSerializableExtra("user");
        requirement = (WorkRequirement) info.getSerializableExtra("requirement");
//        user.loadAppliedId();
        DatabaseReference getJob = FirebaseDatabase.getInstance().getReference("/Jobs/Detail/"+requirement.getId());
        getJob.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requirement = dataSnapshot.getValue(WorkRequirement.class);
                initialize();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initialize() {
        saveBtn = findViewById(R.id.saveBtn);
        subscribe = findViewById(R.id.subscribe);
//        final DatabaseReference readRef = FirebaseDatabase.getInstance().getReference("/Users/Saved/"+user.getUserId());
//        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot p: dataSnapshot.getChildren()){
//                    if(user.isSaved(p.getValue(String.class))){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        if(user.isApplied(requirement.getId())){
            registerState = REGISTERED;
            subscribe.setTextColor(Color.parseColor("#8B8B8B"));
            subscribe.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
            registerState = UNREGISTERED;

        if(user.isSaved(requirement.getId())){
            saveState = SAVED;
            saveBtn.setTextColor(Color.parseColor("#8B8B8B"));
        }
        else
            saveState = UNSAVED;
        jobName = findViewById(R.id.jobName);
        jobName.setText(requirement.getJobName());
        compName = findViewById(R.id.compName);
        compName.setText(requirement.getCompanyName());
        majorTV = findViewById(R.id.majorTV);
        majorTV.setText(requirement.getMajor());
        cityTV = findViewById(R.id.cityTV);
        cityTV.setText(requirement.getCity());
        districtTV=findViewById(R.id.districtTV);
        districtTV.setText(requirement.getDistrict());
        salaryTV = findViewById(R.id.salaryTV);
        salaryTV.setText(String.valueOf(requirement.getSalary()));
        degreeTV = findViewById(R.id.degreeTV);
        degreeTV.setText(requirement.getDegree());
        workPosTV = findViewById(R.id.workPosTV);
        workPosTV.setText(requirement.getWorkPos());
        expTV = findViewById(R.id.experienceTV);
        expTV.setText(requirement.getExperience()==0?"Chưa có kinh nghiệm" :requirement.getExperience()+ " năm");
        descriptionTV = findViewById(R.id.descriptionTV);
        descriptionTV.setText(requirement.getDescription());
        requireTV = findViewById(R.id.requirementTV);
        requireTV.setText(requirement.getRequirement());
        benefitTV = findViewById(R.id.benefitTV);
        benefitTV.setText(requirement.getBenefit());
        endDateTV = findViewById(R.id.endDateTV);
        endDateTV.setText(requirement.getEndDate());
        saveBtn.setOnClickListener(saveJob);
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registerState == REGISTERED)
                    Toast.makeText(RequirementDetail.this, "Bạn đã đăng ký công việc này rồi!!",
                            Toast.LENGTH_SHORT).show();
                else {
                    user.addAppliedId(requirement.getId());
                    requirement.setApplied(requirement.getApplied() + 1);
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/Users/Applied/"+user.getUserId()+"/"+requirement.getId()+"/jobId", requirement.getId());
                    childUpdates.put("/Jobs/Job List/"+requirement.getId()+"/applied", requirement.getApplied());
                    childUpdates.put("/Jobs/Detail/"+requirement.getId()+"/applied", requirement.getApplied());
                    childUpdates.put("/Jobs/Applied/"+requirement.getId()+"/"+user.getUserId()+"/userId", user.getUserId());
                    DatabaseReference write = FirebaseDatabase.getInstance().getReference();
                    write.updateChildren(childUpdates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RequirementDetail.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RequirementDetail.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                }
                            });
                    Date m = new Date(System.currentTimeMillis());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                    Noti noti = new Noti(user.getUserId(), user.getName(), 2, dateFormat.format(m),false, requirement.getId());
                    noti.send("/Company/Notifications/"+requirement.getIdCompany());
                    registerState = REGISTERED;
                    subscribe.setTextColor(Color.parseColor("#8B8B8B"));
                    subscribe.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
//        cursor.close();
    }

    private View.OnClickListener saveJob = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(saveState == SAVED)
                Toast.makeText(RequirementDetail.this, "Bạn đã lưu công việc này rồi!!",
                        Toast.LENGTH_SHORT).show();
            else {
                user.addSavedId(requirement.getId());
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/Users/Saved/"+user.getUserId()+"/"+requirement.getId()+"/jobId", requirement.getId());
                DatabaseReference write = FirebaseDatabase.getInstance().getReference();
                write.updateChildren(childUpdates);
                Toast.makeText(RequirementDetail.this, "Đã lưu vào danh sách", Toast.LENGTH_SHORT).show();
                //Thêm công việc vào danh sách đã lưu
                saveState = SAVED;
//                saveBtn.setBackgroundColor(Color.parseColor("#8B8B8B"));
                saveBtn.setTextColor(Color.parseColor("#8B8B8B"));
            }
        }
    };
}
