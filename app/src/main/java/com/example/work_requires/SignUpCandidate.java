package com.example.work_requires;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignUpCandidate extends AppCompatActivity {

    EditText txt_username, txt_pass, txt_pass_2, txt_name, txt_address, txt_email, txt_phone;
    Spinner spn_area;
    String username="", pass="", pass2="", name="", address="", email="", phone="", area="", fax= "";
    SQLiteManagement sqLiteManagement;
    Button btnSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_can);

        initialize();
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    public void initialize(){
        txt_username = findViewById(R.id.txt_username);
        txt_pass = findViewById(R.id.txt_pass);
        txt_pass_2 = findViewById(R.id.txt_pass_2);
        txt_name = findViewById(R.id.txt_name);
        txt_address = findViewById(R.id.txt_addr);
        spn_area = findViewById(R.id.spn_area);
        txt_email = findViewById(R.id.txt_mail);
        txt_phone = findViewById(R.id.txt_phone);
        btnSignin = findViewById(R.id.btnSignin);

        final List <String> listArea = new ArrayList<>();
        listArea.add("Hồ Chí Minh");
        listArea.add("Hà Nội");

        ArrayAdapter<String> adapterArea= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listArea);
        adapterArea.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_area.setAdapter(adapterArea);
        spn_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area= listArea.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                area="";
            }
        });


    }
    public void signIn(){
        if(checked()){
            sqLiteManagement = new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);
            User user = new User(username, pass, "2",name, email, phone, fax, address, area );
            sqLiteManagement.insert(user);

            Intent intent2 = new Intent(SignUpCandidate.this, OnlineCV.class);
            intent2.putExtra("user", user);
            startActivity(intent2);

        }
    }

    private void errorAlert(String error) {
        Toast.makeText(SignUpCandidate.this, error, Toast.LENGTH_LONG).show();
    }

    private boolean checked() {
        int count = 0;
        username = txt_username.getText().toString();
        sqLiteManagement = new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);
        Cursor cursor = sqLiteManagement.getDatasql("select count(*) from user where username='" + username + "'");
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
            if (count != 0) {
                errorAlert("Tên đăng nhập đã tồn tại, xin kiểm tra lại");
                return false;
            }
        }
        pass = txt_pass.getText().toString();
        pass2 = txt_pass_2.getText().toString();
        if (pass.equals(pass2) == false) {
            errorAlert("Mật khẩu không trùng khớp, xin kiểm tra lại");
            return false;
        }
        name = txt_name.getText().toString();
        address = txt_address.getText().toString();
        email = txt_email.getText().toString();
        phone = txt_phone.getText().toString();
        if (username.equals("") || pass.equals("") || pass2.equals("") || name.equals("") ||
                address.equals("") || email.equals("") || phone.equals("") || area.equals(""))
        {
            errorAlert("Bạn chưa nhập đủ thông tin!!");
            return false;
        }
        cursor.close();
        return true;
    }
}
