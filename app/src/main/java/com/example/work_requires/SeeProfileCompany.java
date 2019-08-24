package com.example.work_requires;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.work_requires.models.Company;
import com.example.work_requires.models.DataHolder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeeProfileCompany extends AppCompatActivity {

    EditText txt_name, txt_address, txt_email, txt_phone, txt_fax;
    Spinner spn_city;
    String name="", address="", email="", phone="", city="", fax= "";
    Button btnUpdate;
    List<String>cityList;
    Company user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_profile_company);
        initialize();
    }

    public void initialize(){
        user = DataHolder.getCompUser();
        txt_name = findViewById(R.id.txt_name);
        txt_name.setText(user.getName());
        txt_address = findViewById(R.id.txt_addr);
        txt_address.setText(user.getAddress());
        spn_city = findViewById(R.id.spn_city);
        txt_email = findViewById(R.id.txt_mail);
        txt_email.setText(user.getEmail());
        txt_phone = findViewById(R.id.txt_phone);
        txt_phone.setText(user.getPhone());
        txt_fax = findViewById(R.id.txt_fax);
        txt_fax.setText(user.getFax());
        cityList = new ArrayList<>();
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SeeProfileCompany.this);
                    builder.setCancelable(true);
                    builder.setTitle("Xác nhận");
                    builder.setMessage("Bạn có chắc chắn muốn lưu thay đổi vừa rồi không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            user.setCity(city);
                            user.setName(name);
                            user.setPhone(phone);
                            user.setEmail(email);
                            user.setFax(fax);
                            user.setAddress(address);
                            user.signUpUser(user.getUserId());
                            DataHolder.clearAll();
                            DataHolder.setCompUser(user);
                        }
                    });
                    builder.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onResume();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SeeProfileCompany.this,
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
//        final DatabaseReference database = FirebaseDatabase.getInstance().getReference(path);
//        final List<String>list = new ArrayList<>();
//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot p : dataSnapshot.getChildren())
//                    list.add(p.getValue(String.class));
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(SeeProfileCompany.this,
//                        R.layout.support_simple_spinner_dropdown_item, list);
//                spinner.setAdapter(adapter);
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
        Toast.makeText(SeeProfileCompany.this, error, Toast.LENGTH_LONG).show();
    }

    private boolean checked(){
        name = txt_name.getText().toString();
        address = txt_address.getText().toString();
        email = txt_email.getText().toString();
        phone = txt_phone.getText().toString();
        fax = txt_fax.getText().toString();
        if (city.equals("") || name.equals("") || fax.equals("") || address.equals("") || email.equals("") || phone.equals(""))
        {
            errorAlert("Bạn chưa nhập đủ thông tin!!");
            return false;
        }
        return true;
    }
}
