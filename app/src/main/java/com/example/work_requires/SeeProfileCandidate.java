package com.example.work_requires;

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

import com.example.work_requires.models.DataHolder;
import com.example.work_requires.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeeProfileCandidate extends AppCompatActivity {

    EditText txt_name, txt_email, txt_phone;
    Spinner spn_city;
    String name="", email="", phone="", city ="";
    Button btnUpdate;
    List<String> cityList;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_profile_candidate);
        initialize();
    }

    public void initialize(){
        user = DataHolder.getNormalUser();
        txt_name = findViewById(R.id.txt_name);
        txt_name.setText(user.getName());
        spn_city = findViewById(R.id.spn_city);
        txt_email = findViewById(R.id.txt_mail);
        txt_email.setText(user.getEmail());
        txt_phone = findViewById(R.id.txt_phone);
        txt_phone.setText(user.getPhone());
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checked()){
                    user.setCity(city);
                    user.setEmail(email);
                    user.setName(name);
                    user.setPhone(phone);
                    user.updateProfile();
                    DataHolder.setNormalUser(user);
                    errorAlert("Lưu thành công");
                }
            }
        });
        cityList = new ArrayList<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("/Areas/Cities");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot p : dataSnapshot.getChildren())
                    cityList.add(p.getValue(String.class));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SeeProfileCandidate.this,
                        R.layout.support_simple_spinner_dropdown_item, cityList);
                spn_city.setAdapter(arrayAdapter);
                spn_city.setSelection(cityList.indexOf(user.getCity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

//    private List<String> getLists(String path, final Spinner spinner){
//        final List<String>list = new ArrayList<>();
//        final DatabaseReference database = FirebaseDatabase.getInstance().getReference(path);
//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot p : dataSnapshot.getChildren())
//                    list.add(p.getValue(String.class));
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SeeProfileCandidate.this,
//                        R.layout.support_simple_spinner_dropdown_item, list);
//                spinner.setAdapter(arrayAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        return list;
//    }

    private void errorAlert(String error) {
        Toast.makeText(SeeProfileCandidate.this, error, Toast.LENGTH_LONG).show();
    }

    private boolean checked(){
        name = txt_name.getText().toString();
        email = txt_email.getText().toString();
        phone = txt_phone.getText().toString();
        if (name.equals("") || email.equals("") || phone.equals("") || city.equals(""))
        {
            errorAlert("Bạn chưa nhập đủ thông tin!!");
            return false;
        }
        return true;
    }
}
