package com.example.work_requires;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public  void inputTypeSignUp ()
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
                         break;
                    case R.id.radioCan:
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
    }
}
