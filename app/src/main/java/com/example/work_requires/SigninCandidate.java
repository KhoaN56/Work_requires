package com.example.work_requires;

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

public class SigninCandidate extends AppCompatActivity {

    EditText txt_username, txt_pass, txt_pass_2, txt_name, txt_address, txt_email, txt_phone;
    Spinner spn_area, spn_major;
    String username="", pass="", pass2="", name="", address="", email="", phone="", area="", major="", fax= "";
    Integer type=2;
    SQLiteManagement sqLiteManagement;
    Button btnSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_can);

        initialize();
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    public void initialize(){
        txt_username = findViewById(R.id.txt_jobPos);
        txt_pass = findViewById(R.id.txt_pass);
        txt_pass_2 = findViewById(R.id.txt_exprience);
        txt_name = findViewById(R.id.txt_date_of_birth);
        txt_address = findViewById(R.id.txt_addr);
        spn_area = findViewById(R.id.sex);
        txt_email = findViewById(R.id.txt_school);
        txt_phone = findViewById(R.id.txt_major);
        spn_major = findViewById(R.id.classify);
        btnSignin = findViewById(R.id.btnSignin);

        final List <String> listArea = new ArrayList<>();
        listArea.add("Hồ Chí Minh");
        listArea.add("Hà Nội");

        final List <String> listMajor = new ArrayList<>();
        listMajor.add("Thực phẩm");
        listMajor.add("Xây dựng");
        listMajor.add("Công nghệ thông tin");
        listMajor.add("Bán hàng");

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

        ArrayAdapter<String> adapterMajor= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listMajor);
        adapterMajor.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_major.setAdapter(adapterMajor);
        spn_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                major= listMajor.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                major="";
            }
        });
    }
    public void signIn(){
        if(checked()){
            sqLiteManagement = new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);
            User user = new User(username, pass,"2",name, email,phone,"",address, area, major);
            sqLiteManagement.insert(user);
            errorAlert("Đăng ký thành công!!");
        }
    }

    private void errorAlert(String error) {
        Toast.makeText(SigninCandidate.this, error, Toast.LENGTH_LONG).show();
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
                address.equals("") || email.equals("") || phone.equals("") || area.equals("") || major.equals(""))
        {
            errorAlert("Bạn chưa nhập đủ thông tin!!");
            return false;
        }
        return true;
    }
}
