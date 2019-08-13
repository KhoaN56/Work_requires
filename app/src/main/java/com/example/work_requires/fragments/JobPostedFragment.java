package com.example.work_requires.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.work_requires.PostRequirement;
import com.example.work_requires.R;
import com.example.work_requires.UpdateRequirement;
import com.example.work_requires.ViewCandidateList;
import com.example.work_requires.adapters.CustomAdapter2;
import com.example.work_requires.models.Company;
import com.example.work_requires.models.WorkRequirement;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class JobPostedFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    private CustomAdapter2 adapter;

    //Button
    FloatingActionButton btn_insertReq;

    //User
    Company user;

    //List
    List<WorkRequirement> jobList;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main_comp, container, false);
        this.view = view;
        recyclerView = view.findViewById(R.id.postedRecyclerView);
        return view;
    }

    @Override
    public void onResume() {
//        if(user.getJobPosted().size()==0)
        Bundle bundle = getArguments();
        assert bundle != null;
        user = (Company)bundle.getSerializable("company user");
            user.updateJobPosted();
        initialize();
        super.onResume();
    }

    private void initialize() {
        jobList = new ArrayList<>();
//        setUpRecyclerView();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("/Jobs/Job List");
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot p: dataSnapshot.getChildren()){
//                    WorkRequirement dummy = p.getValue(WorkRequirement.class);
//                    assert dummy != null;
//                    if(user.isPosted(dummy.getId())){
//                        jobList.add(dummy);
//                    }
//                }
//                setUpRecyclerView();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        setUpRecyclerView();
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                WorkRequirement dummy;
                dummy = dataSnapshot.getValue(WorkRequirement.class);
                assert dummy != null;
                if(user.isPosted(dummy.getId())){
                    jobList.add(dummy);
                    adapter.updateList();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                WorkRequirement dummy;
                dummy = dataSnapshot.getValue(WorkRequirement.class);
                assert dummy != null;
                if(user.isPosted(dummy.getId())){
                    jobList.set(user.getIndexOfPostedJob(dummy.getId()), dummy);
                    adapter.updateList();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                WorkRequirement dummy;
                dummy = dataSnapshot.getValue(WorkRequirement.class);
                assert dummy != null;
                if(user.isPosted(dummy.getId())){
                    jobList.remove(user.getIndexOfPostedJob(dummy.getId()));
                    adapter.updateList();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_insertReq= view.findViewById(R.id.btn_insertReq);
        btn_insertReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PostRequirement.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new CustomAdapter2(jobList, JobPostedFragment.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CustomAdapter2.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent toViewCandidateList = new Intent(getContext(), ViewCandidateList.class);
                toViewCandidateList.putExtra("requirement", jobList.get(position));
                toViewCandidateList.putExtra("user", user);
                startActivity(toViewCandidateList);
            }
        });
        adapter.updateList();
    }

    public void deleteRequirement(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc muốn xóa tin này?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WorkRequirement jobToRemove = jobList.get(position);
                jobList.remove(position);
                user.removePostedJob(jobToRemove.getId());
                HashMap<String, Object> childUpdate = new HashMap<>();
                childUpdate.put("/Jobs/Job List/"+jobToRemove.getId(), null);
                childUpdate.put("/Jobs/Detail/"+jobToRemove.getId(), null);
                childUpdate.put("/Jobs/Applied/"+jobToRemove.getId(), null);
                childUpdate.put("/Company/"+user.getUserId()+"/jobPosted", user.getJobPosted());
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.updateChildren(childUpdate);
                adapter.updateList();
                Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
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

    public void editRequirement(final int position) {

        DatabaseReference getJobDetail = FirebaseDatabase
                .getInstance().getReference("/Jobs/Detail/"+jobList.get(position).getId());
        getJobDetail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent edit = new Intent(getContext(), UpdateRequirement.class);
                edit.putExtra("work", dataSnapshot.getValue(WorkRequirement.class));
                startActivity(edit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public CustomAdapter2 getAdapter() {
        return adapter;
    }
}
