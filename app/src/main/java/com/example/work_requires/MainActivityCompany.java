package com.example.work_requires;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivityCompany extends AppCompatActivity {

    //Database
    SQLiteManagement database;

    //Recycle View
    RecyclerView postedRecycleView;
    CustomAdapter2 adapter;

    //List
    List<WorkRequirement> workRequirementList;

    //Buttons
    FloatingActionButton btn_insertReq;

    //User
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_company);
    }

    //Để đảm bảo khi quay lại activity này thì dữ liệu được cập nhật
    @Override
    protected void onResume() {
        super.onResume();
        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    private void initialize() {
        user = (User)getIntent().getSerializableExtra("user");
        database = new SQLiteManagement(MainActivityCompany.this, "Work_Requirement.sqlite", null, 1);
        Cursor cursor = database.getDatasql("SELECT * FROM Recruitment WHERE Username = '"+user.getUsername()+"'");
        workRequirementList = new ArrayList<>();
        while (cursor.moveToNext())
            workRequirementList.add(new WorkRequirement(cursor.getInt(0),
                    cursor.getString(2),cursor.getString(3),
                    cursor.getString(4), cursor.getLong(5),
                    cursor.getString(6), cursor.getString(7),
                    cursor.getInt(8), cursor.getInt(9),
                    cursor.getString(10), cursor.getString( 11),
                    cursor.getString(12), cursor.getString(13),
                    user.getName(), cursor.getInt(14)
            ));
        cursor.close();
        setUpRecyclerView();
        btn_insertReq= findViewById(R.id.btn_insertReq);
        btn_insertReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityCompany.this, PostRequirement.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    private void setUpRecyclerView() {
        postedRecycleView = findViewById(R.id.postedRecycleView);
        postedRecycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivityCompany.this);
        adapter = new CustomAdapter2(workRequirementList, MainActivityCompany.this);
        postedRecycleView.setLayoutManager(layoutManager);
        postedRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CustomAdapter2.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent toViewCandidateList = new Intent(MainActivityCompany.this, ViewCandidateList.class);
                toViewCandidateList.putExtra("requirement", workRequirementList.get(position));
                toViewCandidateList.putExtra("user", user);
                startActivity(toViewCandidateList);
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void deleteRequirement(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityCompany.this);
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.delete(workRequirementList.get(position));
                workRequirementList.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivityCompany.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
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
        Intent edit = new Intent(MainActivityCompany.this, UpdateRequirement.class);
        edit.putExtra("work", workRequirementList.get(position));
        startActivity(edit);
    }
}
