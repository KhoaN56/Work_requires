package com.example.work_requires.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

//import java.text.ParseException;
////import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

public class JobsSavedFrag extends Fragment {

    //Context
    MainActivity context;

    //List items
//    List<WorkRequirement> dummylist;
    List<WorkRequirement> savedList;

    //RecycleView
    RecyclerView requireRecycleView;
    CustomAdapter adapter;

    //Database
//    SQLiteManagement workRequireDatabase;

    //User
    User user;

    //Variables

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
        Bundle bundle = getArguments();
        assert bundle != null;
        user = (User) bundle.getSerializable("user");
        assert user != null;
//        user.loadSavedIdList();
    }

    public void init() {
        savedList = new ArrayList<>();
        setUpRecyclerView();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Jobs").child("Job List").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(user.isSaved(dataSnapshot.getKey())){
                    savedList.add(dataSnapshot.getValue(WorkRequirement.class));
                    adapter.updateList();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                WorkRequirement dummy = dataSnapshot.getValue(WorkRequirement.class);
                DatabaseReference removeSavedJob = FirebaseDatabase.getInstance()
                        .getReference("/Users/Saved/"+user.getUserId()+dummy.getId());
                removeSavedJob.removeValue();
                savedList.remove(dummy);
                user.removeSavedId(dummy.getId());
                adapter.updateList();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpRecyclerView() {
        requireRecycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        requireRecycleView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(savedList);
        requireRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent toDetail = new Intent(getContext(), RequirementDetail.class);
                toDetail.putExtra("requirement", savedList.get(position));
                toDetail.putExtra("user", user);
                startActivity(toDetail);
            }
        });
    }
}