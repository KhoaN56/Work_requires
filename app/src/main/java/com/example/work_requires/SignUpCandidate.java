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

import com.example.work_requires.models.User;
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

public class SignUpCandidate extends AppCompatActivity {

    EditText txt_pass, txt_pass_2, txt_name, txt_email, txt_phone;
    Spinner spn_city, spn_district;
    String username="", pass="", pass2="", name="", district="", email="", phone="", city ="";
//    SQLiteManagement sqLiteManagement;
    Button btnSignUp;
    FirebaseAuth auth;
//    DatabaseReference database;
    List<String> listDistrict;
    List<String> cityList;
    ArrayAdapter<String> adapterDistrict;
//    int cityPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_can);
        initialize();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }
    public void initialize(){
        auth = FirebaseAuth.getInstance();
        txt_pass = findViewById(R.id.txt_pass);
        txt_pass_2 = findViewById(R.id.txt_pass_2);
        txt_name = findViewById(R.id.txt_name);
        spn_city = findViewById(R.id.spn_city);
        spn_district = findViewById(R.id.spn_district);
        txt_email = findViewById(R.id.txt_mail);
        txt_phone = findViewById(R.id.txt_phone);
        btnSignUp = findViewById(R.id.btnSignUp);
        final List<String> cityKeyList = getKeyLists();
        cityList = getLists("/Areas/Cities", spn_city);
        listDistrict = new ArrayList<>();
        spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = cityList.get(position);
                listDistrict.clear();
                listDistrict = getLists("/Areas/District/" + cityKeyList.get(position), spn_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city ="";
            }
        });

        spn_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                district = listDistrict.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                district = "";
            }
        });

    }

    private List<String> getLists(String path, final Spinner spinner){
        final List<String>list = new ArrayList<>();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference(path);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot p : dataSnapshot.getChildren())
                    list.add(p.getValue(String.class));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SignUpCandidate.this,
                        R.layout.support_simple_spinner_dropdown_item, list);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    private List<String> getKeyLists(){
        final List<String>keyList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/Areas/Cities");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    keyList.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return keyList;
    }

    public void signUp(){
        if(checked()){
//            sqLiteManagement = new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);
//            sqLiteManagement.insert(user);
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
                                User user = new User(userId ,name, email, phone, city, district);
                                //update user info
//                                user.signUpUser();
                                Intent intent2 = new Intent(SignUpCandidate.this, OnlineCV.class);
                                intent2.putExtra("user", user);
                                startActivity(intent2);
                                //prevent move back...
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("FIREBASE_TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpCandidate.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }

    private void errorAlert(String error) {
        Toast.makeText(SignUpCandidate.this, error, Toast.LENGTH_LONG).show();
    }

    private boolean checked() {
//        int count = 0;
//        username = txt_username.getText().toString();
//        sqLiteManagement = new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);
//        Cursor cursor = sqLiteManagement.getDatasql("select count(*) from cv where str_email='" + username + "'");
//        if (cursor.moveToNext()) {
//            count = cursor.getInt(0);
//            if (count != 0) {
//                errorAlert("Tên đăng nhập đã tồn tại, xin kiểm tra lại");
//                return false;
//            }
//        }
        pass = txt_pass.getText().toString();
        pass2 = txt_pass_2.getText().toString();
        if (!pass.equals(pass2)) {
            errorAlert("Mật khẩu không trùng khớp, xin kiểm tra lại");
            return false;
        }
        name = txt_name.getText().toString();
        email = txt_email.getText().toString();
        phone = txt_phone.getText().toString();
        if (pass.equals("") || pass2.equals("") || name.equals("") ||
                email.equals("") || phone.equals("") || city.equals("") || district.equals(""))
        {
            errorAlert("Bạn chưa nhập đủ thông tin!!");
            Log.d("City", city);
            Log.d("District", district);
            return false;
        }
//        cursor.close();
        return true;
    }
}
