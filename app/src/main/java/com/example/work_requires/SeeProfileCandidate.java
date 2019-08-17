package com.example.work_requires;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

public class SeeProfileCandidate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_profile_candidate);
        initialize();
    }

    private void initialize() {
        EditText editText = findViewById(R.id.editText);
        OnTextChangeListener listener = new OnTextChangeListener();
//        editText.setOnClickListener(listener);
    }
}
