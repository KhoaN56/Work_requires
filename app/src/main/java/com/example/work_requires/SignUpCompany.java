package com.example.work_requires;

import android.content.Intent;
//import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.work_requires.models.Company;
//import com.example.work_requires.models.User;
import com.example.work_requires.models.DataHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignUpCompany extends AppCompatActivity {

    EditText txt_pass, txt_pass_2, txt_name, txt_address, txt_email, txt_phone, txt_fax;
    Spinner spn_city;
    String pass="", pass2="", name="", address="", email="", phone="", city="", fax= "";
    Button btnSignin;

    List<String>cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_comp);

        initialize();
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void initialize(){
        txt_pass = findViewById(R.id.txt_pass);
        txt_pass_2 = findViewById(R.id.txt_pass_2);
        txt_name = findViewById(R.id.txt_name);
        txt_address = findViewById(R.id.txt_addr);
        spn_city = findViewById(R.id.spn_city);
        txt_email = findViewById(R.id.txt_mail);
        txt_phone = findViewById(R.id.txt_phone);
        txt_fax = findViewById(R.id.txt_fax);
        btnSignin = findViewById(R.id.btnSignUp);

        cityList = getLists("/Areas/Cities", spn_city);
        spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = cityList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city ="";
            }
        });

    }

    private List<String> getLists(String path, final Spinner spinner){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference(path);
        final List<String>list = new ArrayList<>();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot p : dataSnapshot.getChildren())
                    list.add(p.getValue(String.class));
                ArrayAdapter<String>adapter = new ArrayAdapter<>(SignUpCompany.this,
                        R.layout.support_simple_spinner_dropdown_item, list);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public void signUp(){
        if(checked()){
//            sqLiteManagement = new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);

//            sqLiteManagement.insert(user);
            errorAlert("Đăng ký thành công!!");
            final FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("FIREBASE_TAG", "createUserWithEmail:success");
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                assert firebaseUser != null;
                                String userId = firebaseUser.getUid();
                                Company user = new Company(userId, name, email, phone, fax, address, city);
                                //update user info
                                user.signUpUser(userId);
                                Intent intent2 = new Intent(SignUpCompany.this, MainActivityCompany.class);
//                                intent2.putExtra("user", user);
                                DataHolder.setCompUser(user);
                                startActivity(intent2);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("FIREBASE_TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpCompany.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }

    private void errorAlert(String error) {
        Toast.makeText(SignUpCompany.this, error, Toast.LENGTH_LONG).show();
    }

    private boolean checked() {
        pass = txt_pass.getText().toString();
        pass2 = txt_pass_2.getText().toString();
        if (!pass.equals(pass2)) {
            errorAlert("Mật khẩu không trùng khớp, xin kiểm tra lại");
            return false;
        }
        name = txt_name.getText().toString();
        address = txt_address.getText().toString();
        email = txt_email.getText().toString();
        phone = txt_phone.getText().toString();
        fax = txt_phone.getText().toString();
        if (city.equals("") || pass.equals("") || pass2.equals("") || name.equals("") || fax.equals("") ||
                address.equals("") || email.equals("") || phone.equals(""))
        {
            errorAlert("Bạn chưa nhập đủ thông tin!!");
            return false;
        }
        return true;
    }
}
