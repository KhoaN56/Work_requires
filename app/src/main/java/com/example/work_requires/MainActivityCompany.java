package com.example.work_requires;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivityCompany extends AppCompatActivity {

    //Database
    SQLiteManagement database;

    //Recycle View
    RecyclerView postedRecycleView;
    CustomAdapter adapter;

    //List
    List<WorkRequirement> workRequirementList;
    List<User> candidateList;

    //Buttons
    FloatingActionButton btn_insertReq;

    //User
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_company);

        initialize();
    }

    private void initialize() {
        user = (User)getIntent().getSerializableExtra("user");
        database = new SQLiteManagement(MainActivityCompany.this, "Work_Requirement.sqlite", null, 1);
        database.queryData("CREATE TABLE IF NOT EXISTS Recruitment(Id_Recruitment INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, Username CHAR(20), JobName CHAR(100), Major NCHAR(50), Area NCHAR(20)," +
                "Salary INTEGER, Degree CHAR(15), Position NCHAR(20), Experience INTEGER, Amount INTEGER," +
                "Description VARCHAR, Requirement NVARCHAR, Benefit NVARCHAR, End_Date CHAR(10))");
        database.queryData("CREATE TABLE IF NOT EXISTS DETAIL (ID_DETAIL INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, Username CHAR(20), Id_Recruitment INTEGER)");
        Cursor cursor = database.getDatasql("SELECT * FROM Recruitment WHERE Username = '"+user.getUsername()+"'");
        workRequirementList = new ArrayList<>();
        candidateList = new ArrayList<>();
        while (cursor.moveToNext())
            workRequirementList.add(new WorkRequirement(cursor.getInt(0),
                    cursor.getString(2),cursor.getString(3),
                    cursor.getString(4), cursor.getLong(5),
                    cursor.getString(6), cursor.getString(7),
                    cursor.getInt(8), cursor.getInt(9),
                    cursor.getString(10), cursor.getString( 11),
                    cursor.getString(12), cursor.getString(13),user.getName()));
        cursor.close();
        for(WorkRequirement requirement:workRequirementList){
            cursor = database.getDatasql("SELECT COUNT (*) FROM DETAIL WHERE Id_Recruitment = "+requirement.getId());
            cursor.moveToNext();

        }
        postedRecycleView = findViewById(R.id.postedRecycleView);
        adapter = new CustomAdapter(MainActivityCompany.this, R.layout.posted_item, workRequirementList);
        postedRecycleView.setAdapter(adapter);
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
}
