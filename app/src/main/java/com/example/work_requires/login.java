package com.example.work_requires;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class login extends AppCompatActivity {

    Button btnSignin;
    SQLiteManagement userDatabse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignin = findViewById(R.id.btnSignin);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeDialog();
            }
        });
    }
    public void typeDialog()
    {
        final Dialog typeDialog= new Dialog(this);
        typeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        typeDialog.setContentView(R.layout.type_dialog);

        final RadioGroup radioGroup = typeDialog.findViewById(R.id.radioGroup);
        final RadioButton checkedButton;

        Button btnNext = typeDialog.findViewById(R.id.btnNext);
        Button btnCancel = typeDialog.findViewById(R.id.btnCancel);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radioComp:
                        Intent intent = new Intent(login.this, signin_comp.class);
                        startActivity(intent);
                         break;
                    case R.id.radioCan:
                        Intent intent2 = new Intent(login.this, signin_can.class);
                        startActivity(intent2);
                        break;
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeDialog.dismiss();
            }
        });
        typeDialog.show();
    }

    public void initialize(){
        userDatabse= new SQLiteManagement(this, "user.sqlite", null, 1);
        userDatabse.queryData("CREATE TABLE IF NOT EXISTS USER (");
    }
}
