package com.example.work_requires;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //Buttons
    ImageButton menuButton, searchButton;

    //List View
    ListView requireListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        menuButton = findViewById(R.id.menuButton);
        searchButton = findViewById(R.id.searchButton);
        requireListView = findViewById(R.id.requireListView);
    }
}
