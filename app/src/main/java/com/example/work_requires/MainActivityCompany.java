package com.example.work_requires;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityCompany extends AppCompatActivity {

    Button btn_insertReq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_company);

        btn_insertReq= findViewById(R.id.btn_insertReq);
        btn_insertReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityCompany.this, PostRequirement.class);
                intent.putExtra("user", (User)getIntent().getSerializableExtra("user"));
                startActivity(intent);
            }
        });
    }
}
