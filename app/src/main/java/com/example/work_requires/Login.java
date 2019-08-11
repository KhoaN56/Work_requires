package com.example.work_requires;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.work_requires.models.Company;
import com.example.work_requires.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

//import java.util.Objects;

public class Login extends AppCompatActivity {

    Button btnSignUp, btnLogin;
//    SQLiteManagement managementDatabse;
    EditText email, text_pass;
    String str_email ="", pass="";
    FirebaseAuth auth;
    DatabaseReference database;
    User user;
    Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null)
            getUser(currentUser);
        setContentView(R.layout.activity_login);
        initialize();

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    public void typeDialog()
    {
        final Dialog typeDialog= new Dialog(this);
        typeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        typeDialog.setContentView(R.layout.type_dialog);

        final RadioGroup radioGroup = typeDialog.findViewById(R.id.radioGroup);

        Button btnNext = typeDialog.findViewById(R.id.btnNext);
        Button btnCancel = typeDialog.findViewById(R.id.btnCancel);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radioComp:
                        Intent intent = new Intent(Login.this, SignUpCompany.class);
                        startActivity(intent);
                         break;
                    case R.id.radioCan:
                        Intent intent2 = new Intent(Login.this, SignUpCandidate.class);
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
        email = findViewById(R.id.email);
        text_pass = findViewById(R.id.password);
//        managementDatabse= new SQLiteManagement(this, "Work_Requirement.sqlite", null, 1);
    }

    private void getUser(@NotNull FirebaseUser currentUser){
        final String userId = currentUser.getUid();
        database = FirebaseDatabase.getInstance().getReference("/Users/User List");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot p: dataSnapshot.getChildren())
                    if(userId.equals(p.getKey())){
                        DatabaseReference getUser = FirebaseDatabase.getInstance().getReference("/Users/Detail/"+userId);
                        getUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                user = dataSnapshot.getValue(User.class);
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        return;
                    }
                //Logging in acount is not user => Company
                DatabaseReference getCompany = FirebaseDatabase.getInstance().getReference("/Company/"+userId);
                getCompany.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        company = dataSnapshot.getValue(Company.class);
                        Intent intent = new Intent(Login.this, MainActivityCompany.class);
                        intent.putExtra("user", company);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        if(userId.equals(database.child("Users").child("User List").child(userId).getKey())){
//            database.child("Users").child("Detail").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    user = dataSnapshot.getValue(User.class);
//                    Intent intent = new Intent(Login.this, MainActivity.class);
//                    intent.putExtra("user", user);
//                    startActivity(intent);
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                }
//            });
//        }else {
//            database.child("Companies").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    company = dataSnapshot.getValue(Company.class);
//                    Intent intent = new Intent(Login.this, MainActivityCompany.class);
//                    intent.putExtra("user", company);
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }
    }

    public void checkLogin()
    {
        str_email = email.getText().toString().trim();
        pass= text_pass.getText().toString().trim();
        if(str_email.equals("") || pass.equals("")){
            Toast.makeText(Login.this, "Bạn chưa nhập email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(str_email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Firebase_Log", "signInWithEmail:success");
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            if(firebaseUser != null)
                            //Run getUser(FirebaseUser) will get user's info and save directly into
                            //user global variable.
                                getUser(firebaseUser);
                            //updateUI(user);
                            //Move to main screen of user
//                            if(user != null){
//                                Intent intent = new Intent(Login.this, MainActivity.class);
//                                intent.putExtra("user", user);
//                                startActivity(intent);
//                            }else {
//                                Intent intent = new Intent(Login.this, MainActivityCompany.class);
//                                intent.putExtra("company", company);
//                                startActivity(intent);
//                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Firebase_Log", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

//        String type;
//        Cursor cursor = managementDatabse.getDatasql("select * from cv where str_email='"+ str_email +"'");
//        if(cursor.moveToNext())
//        {
//            pass_true= cursor.getString(2);
//            type = cursor.getString(1);
//            if(pass.equals(pass_true))
//            {
//                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
////                cv= new User(cursor.getString(0),cursor.getString(2), cursor.getString(1),
////                        cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6),
////                        cursor.getString(7),cursor.getString(8),cursor.getString(16));
//                if(type.equals("1"))
//                {
//                    //chuyển màn hình chính của nhà tuyển dụng
//                    Intent intent = new Intent(Login.this, MainActivityCompany.class);
//                    intent.putExtra("user", user);
//                    startActivity(intent);
//                }
//                else
//                {
//                    //chuyển màn hình chính của người tìm việc
//                    Intent intent = new Intent(Login.this, MainActivity.class);
//                    intent.putExtra("user", user);
//                    startActivity(intent);
//                }
//            }
//            else
//            {
//                Toast.makeText(this, "Mật khẩu không chính xác. Xin kiểm tra lại", Toast.LENGTH_SHORT).show();
//            }
//        }
//        else
//        {
//            Toast.makeText(this, "Thông tin đăng nhập không tồn tại. Xin kiểm tra lại", Toast.LENGTH_SHORT).show();
//        }
//        cursor.close();
    }
}
