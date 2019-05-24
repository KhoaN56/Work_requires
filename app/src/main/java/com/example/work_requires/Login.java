package com.example.work_requires;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button btnSignin, btnLogin;
    SQLiteManagement managementDatabse;
    EditText text_username, text_pass;
    String username="", pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();

        btnSignin = findViewById(R.id.btnSignin);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeDialog();
            }
        });
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
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
                        Intent intent = new Intent(Login.this, SigninCompany.class);
                        startActivity(intent);
                         break;
                    case R.id.radioCan:
                        Intent intent2 = new Intent(Login.this, SigninCandidate.class);
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
        text_username = findViewById(R.id.txt_username);
        text_pass = findViewById(R.id.password);
        managementDatabse= new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);
        managementDatabse.queryData("CREATE TABLE IF NOT EXISTS USER (Username VARCHAR(20) PRIMARY KEY," +
                "Type INTERGER(2), Password VARCHAR(20), Name NVARCHAR(50), Email VARCHAR(50)," +
                "Phone VARCHAR(10), Fax VARCHAR(20), Address NVARCHAR(100), Area NVARCHAR(20), Major NVARCHAR(50))");

    }
    public void checkLogin()
    {
        username= text_username.getText().toString();
        pass= text_pass.getText().toString();
        String pass_true="";
        User user;
        String type;
        Cursor cursor = managementDatabse.getDatasql("select * from user where username='"+username+"'");
        if(cursor.moveToNext())
        {
            pass_true= cursor.getString(2);
            type= cursor.getString(1);
            Log.d("user", "user:" + username+ ", pass: "+ pass+ ", pass_true:"+pass_true+", type:" +type);
            if(pass.equals(pass_true))
            {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                user= new User(cursor.getString(0),cursor.getString(1), cursor.getString(2),
                        cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6),
                        cursor.getString(7),cursor.getString(8),cursor.getString(9));
                if(type.equals("1"))
                {
                    //chuyển màn hình chính của nhà tuyển dụng
                }
                else
                {
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    //chuyển màn hình chính của người tìm việc
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",user);
                    Intent intent = new Intent();
                    intent.putExtra("user",bundle);
                    startActivity(intent);
                }
            }
            else
            {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng. Xin kiểm tra lại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
