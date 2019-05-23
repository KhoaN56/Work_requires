package com.example.work_requires;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Buttons
    ImageButton menuButton, searchButton;

    //List items
    List<WorkRequirement> requirementList;

    //List View
    ListView requireListView;
    CustomAdapter adapter;

    //Database
    SQLiteManagement workRequireDatabase;

    //User
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String currentDate = dateFormat.format(today.getTime());
        menuButton = findViewById(R.id.menuButton);
        searchButton = findViewById(R.id.searchButton);
        requireListView = findViewById(R.id.requireListView);
        workRequireDatabase = new SQLiteManagement(MainActivity.this, "Work_Requirement.sqlite", null, 1);
        workRequireDatabase.queryData("CREATE TABLE IF NOT EXISTS Requirements(Id_Requirement INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, Id_Company CHAR(20), Major NCHAR(50), Area NCHAR(20)," +
                "Salary INTEGER, Degree CHAR(15), Position NCHAR(20), Experience INTEGER, Start_Date DATE, End_Date DATE");
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("user");
        User user = (User)bundle.getSerializable("user");
        Cursor cursor =workRequireDatabase.getDatasql("SELECT * FROM Requirements WHERE " +
                "Major ='"+user.getMajor()+"'");
        while(cursor.moveToNext()){
            requirementList.add(new WorkRequirement(cursor.getString()))
        }
        adapter = new CustomAdapter(MainActivity.this, R.layout.requires_item, requirementList);
        if(requirementList.get(0).getEndDate().equals(currentDate)){

        }
    }
}
