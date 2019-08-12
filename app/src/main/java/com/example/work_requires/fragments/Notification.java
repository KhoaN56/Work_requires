package com.example.work_requires.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.work_requires.DetailCV;
import com.example.work_requires.R;
import com.example.work_requires.RequirementDetail;
import com.example.work_requires.adapters.CustomAdapterNoti;
import com.example.work_requires.models.Company;
import com.example.work_requires.models.Noti;
import com.example.work_requires.models.User;
import com.example.work_requires.models.WorkRequirement;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Notification extends Fragment{

    //Views
    View view;
    ListView notiListView;

    //Users
    Company companyUser;
    User normalUser;

    //Lists
    List<Noti>notification;
    CustomAdapterNoti adapterNoti;

    public Notification() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.view_notification, container, false);
        notiListView = view.findViewById(R.id.notiListView);
        initialize();
        return view;
    }

    private void initialize() {
        Bundle bundle = getArguments();
        assert bundle != null;
        companyUser = (Company) bundle.getSerializable("company user");
        normalUser = (User) bundle.getSerializable("user");
        notification = new ArrayList<>();
        adapterNoti = new CustomAdapterNoti(notification, R.layout.noti_item, getContext());
        notiListView.setAdapter(adapterNoti);
        notiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Noti noti = notification.get(position);
                switch (noti.getAction()){
                    case 1:     //Công ty đã xem hồ sơ của người dùng
                        WorkRequirement requirement = new WorkRequirement();
                        requirement.setId(noti.getIdTarget());
                        Intent toDetailRequirement = new Intent(getContext(), RequirementDetail.class);
                        toDetailRequirement.putExtra("requirement", requirement);
                        toDetailRequirement.putExtra("user", normalUser);
                        startActivity(toDetailRequirement);
                        noti.updateState("/Users/Notifications/"+normalUser.getUserId()+"/"+noti.getKey()+"/state");
                        break;
                    case 2:     //Người dùng vừa đăng ký công việc
                        User target = new User();
                        target.setUserId(noti.getIdTarget());
                        Intent toDetailCV = new Intent(getContext(), DetailCV.class);
                        toDetailCV.putExtra("user", target);
                        toDetailCV.putExtra("company",companyUser);
                        toDetailCV.putExtra("getState", noti.getState());
                        toDetailCV.putExtra("requirement", noti.getFrom());
                        startActivity(toDetailCV);
                        noti.updateState("/Company/Notifications/"+companyUser.getUserId()+"/"+noti.getKey()+"/state");
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
        DatabaseReference data;
        if(normalUser != null)
            data = FirebaseDatabase.getInstance().getReference("/Users/Notifications/" + normalUser.getUserId());
        else
            data = FirebaseDatabase.getInstance().getReference("/Company/Notifications/" + companyUser.getUserId());
        data.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                notification.add(dataSnapshot.getValue(Noti.class));
                adapterNoti.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Noti q = dataSnapshot.getValue(Noti.class);
                for(Noti p: notification){
                    assert q != null;
                    if(q.getKey().equals(p.getKey()))
                        notification.set(notification.indexOf(p), q);
                }
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
    }
}
