package com.example.work_requires;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.example.work_requires.R.id;
import com.example.work_requires.R.layout;
import com.example.work_requires.R.menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //List items
    List<WorkRequirement> requirementList;

    //RecycleView
    RecyclerView requireListView;
    CustomAdapter adapter;

    //Database
    SQLiteManagement workRequireDatabase;

    //User
    User user;

    //Variables
    WorkRequirement dummyRequire;
    String partten = "dd/MM/yyyy";

    //Format
    SimpleDateFormat dateFormat = new SimpleDateFormat(partten);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main_can);
        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(id.action_search);
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
        Date today = new Date(System.currentTimeMillis());
        requirementList = new ArrayList<>();
        workRequireDatabase = new SQLiteManagement(MainActivity.this, "Work_Requirement.sqlite", null, 1);
        workRequireDatabase.queryData("CREATE TABLE IF NOT EXISTS Recruitments(Id_Requirement INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, Username CHAR(20), JobName CHAR(100), Major NCHAR(50), Area NCHAR(20)," +
                "Salary INTEGER, Degree CHAR(15), Position NCHAR(20), Experience INTEGER, Description VARCHAR, Requirement NVARCHAR, " +
                "Benefit NVARCHAR, End_Date CHAR(10))");
        Intent intent = getIntent();
        User user = (User)intent.getSerializableExtra("user");
        Cursor cursor =workRequireDatabase.getDatasql("SELECT A.*, B.Name FROM Recruitments AS A, USER AS B " +
                "WHERE A.MAJOR = '"+user.getMajor()+"' AND B.USERNAME = A.USERNAME");
        while(cursor.moveToNext()){
            //Dòng if dùng để kiểm tra hạn tuyển dụng còn hay hết.
            if(isStillValid(today, cursor.getString(12))){
                requirementList.add(new WorkRequirement(
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4), cursor.getLong(5),
                        cursor.getString(6), cursor.getString(7),
                        cursor.getInt(8), cursor.getString(9),
                        cursor.getString(10), cursor.getString(11),
                        cursor.getString(12), cursor.getString(13)
                ));
            }
        }
        setUpRecyclerView();
        adapter.notifyDataSetChanged();
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

    private void setUpRecyclerView() {
        requireListView = findViewById(id.requireListView);
        requireListView.setHasFixedSize(true);
        LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new CustomAdapter(MainActivity.this, layout.requires_item, requirementList);
        requireListView.setLayoutManager(layoutManager);
        requireListView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent toDetail = new Intent(MainActivity.this, RequirementDetail.class);
                toDetail.putExtra("requirement", requirementList.get(position));
                startActivity(toDetail);
            }
        });
    }

    @Override
    protected void onDestroy() {
        workRequireDatabase.close();
        super.onDestroy();
    }
}
