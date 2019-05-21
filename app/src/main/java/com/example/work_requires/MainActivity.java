package com.example.work_requires;

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
        adapter = new CustomAdapter(MainActivity.this, R.layout.requires_item, requirementList);
        while()
        if(requirementList.get(0).getEndDate().equals(currentDate)){

        }
    }
}
