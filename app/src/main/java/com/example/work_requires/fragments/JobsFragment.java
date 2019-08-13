package com.example.work_requires.fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.work_requires.MainActivity;
import com.example.work_requires.R;
import com.example.work_requires.RequirementDetail;
import com.example.work_requires.adapters.CustomAdapter;
import com.example.work_requires.models.WorkRequirement;
import com.example.work_requires.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobsFragment extends Fragment {

    //Context
    MainActivity context;

    //List items
    List<WorkRequirement> requirementList;

    //RecycleView
    RecyclerView requireRecycleView;
    CustomAdapter adapter;

    //Database
//    SQLiteManagement workRequireDatabase;

    //User
    User user;

    //Format
    SimpleDateFormat dateFormat;

    //Variables
    String pattern = "dd/MM/yyyy";
    String major;

    public void setContext(MainActivity context) {
        this.context = context;
    }

    public CustomAdapter getAdapter() {
        return adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_main_candidate, container,false);
        this.requireRecycleView = view.findViewById(R.id.requireRecycleView);
        init();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    public void init() {
        Bundle bundle = getArguments();
        assert bundle != null;
        user = (User) bundle.getSerializable("user");
        assert user != null;
        major = user.getCv().getMajor();
        final Date today = new Date(System.currentTimeMillis());
        dateFormat = new SimpleDateFormat(pattern);
        requirementList = new ArrayList<>();

        setUpRecyclerView();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("/Jobs/Job List");
            dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                WorkRequirement dummy = dataSnapshot.getValue(WorkRequirement.class);
                assert dummy != null;
                if(dummy.getMajor().equals(major) && isStillValid(today, dummy.getEndDate())){
                    requirementList.add(dummy);
                    adapter.updateList();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                WorkRequirement dummy;
                dummy = dataSnapshot.getValue(WorkRequirement.class);
                assert dummy != null;
                if(dummy.getMajor().equals(major) && isStillValid(today, dummy.getEndDate()))
                    for (WorkRequirement p: requirementList){
                        if(p.getId().equals(dataSnapshot.getKey())){
                            requirementList.set(requirementList.indexOf(p),dummy);
                            adapter.updateList();
                            return;
                        }
                    }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                WorkRequirement dummy;
                dummy = dataSnapshot.getValue(WorkRequirement.class);
                assert dummy != null;
                requirementList.remove(dummy);
                adapter.updateList();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        adapter.notifyDataSetChanged();
//        cursor.close();

    }

    private void setUpRecyclerView() {
        requireRecycleView.setHasFixedSize(true);
        LayoutManager layoutManager = new LinearLayoutManager(context);
        adapter = new CustomAdapter(requirementList);
        requireRecycleView.setLayoutManager(layoutManager);
        requireRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
            Intent toDetail = new Intent(getContext(), RequirementDetail.class);
            toDetail.putExtra("requirement", requirementList.get(position));
            toDetail.putExtra("user", user);
            startActivity(toDetail);
        }
        });
    }

    private boolean isStillValid(Date today, String endDate){
        try {
            Date end = dateFormat.parse(endDate);
            int check = today.compareTo(end);
            return (check <=0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}
