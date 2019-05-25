package com.example.work_requires;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Buttons
    ImageButton menuButton, searchButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_can);
        initialize();
    }

    private void initialize() {
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String currentDate = dateFormat.format(today.getTime());
        menuButton = findViewById(R.id.menuButton);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(search);
        requireListView = findViewById(R.id.requireListView);
        workRequireDatabase = new SQLiteManagement(MainActivity.this, "Work_Requirement.sqlite", null, 1);
        workRequireDatabase.queryData("CREATE TABLE IF NOT EXISTS Requirements(Id_Requirement INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, Username CHAR(20), JobName CHAR(100), Major NCHAR(50), Area NCHAR(20)," +
                "Salary INTEGER, Degree CHAR(15), Position NCHAR(20), Experience INTEGER, End_Date CHAR(8))");
        Intent intent = getIntent();
        User user = (User)intent.getSerializableExtra("user");
        Cursor cursor =workRequireDatabase.getDatasql("SELECT A.*, B.Name FROM Requirements AS A, USER AS B " +
                "WHERE A.MAJOR = '"+user.getMajor()+"' AND B.USERNAME = A.USERNAME");
        while(cursor.moveToNext()){
            //Dòng if dùng để kiểm tra hạn tuyển dụng còn hay hết.
            if(!cursor.getString(9).equals(currentDate))
                requirementList.add(new WorkRequirement(
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4), cursor.getLong(5),
                        cursor.getString(6), cursor.getString(7),
                        cursor.getInt(8), cursor.getString(9),
                        cursor.getString(10)
                ));
        }
        adapter = new CustomAdapter(MainActivity.this, R.layout.requires_item, requirementList);
        requireListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Dialog searchDialog = new Dialog(MainActivity.this);
            searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            searchDialog.setContentView(R.layout.search_dialog);
        }
    };


}
