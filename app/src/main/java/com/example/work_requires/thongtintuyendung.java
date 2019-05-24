package com.example.work_requires;

import android.app.Notification;
import android.content.Intent;
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

public class thongtintuyendung extends AppCompatActivity {
    EditText tenCongty;
    EditText tenCongviec;
    EditText luong;
    EditText kinhNghiem;
    Spinner nganh, khuvuc,bangcap, vitri;
    EditText ngayKt;
    Button Dangtin;
    String txt_congty="", txt_luong="", txt_kinhnghiem="",txt_khuvuc="", txt_nganh="", txt_bangcap="", txt_vitri="", txt_ngaybd, txt_ngaykt="", txt_congviec;
    SQLiteManagement database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtintuyendung);
        tenCongty=findViewById(R.id.editText);
        luong=findViewById(R.id.editText1);
        kinhNghiem =findViewById(R.id.editText4);
        ngayKt = findViewById(R.id.editText6);
        nganh=findViewById(R.id.spinner);
        khuvuc =findViewById(R.id.spinner2);
        bangcap=findViewById(R.id.spinner3);
        vitri =findViewById(R.id.spinner4);
        Dangtin= findViewById(R.id.Dangtin);
        tenCongviec= findViewById(R.id.editText7);
        database = new SQLiteManagement(thongtintuyendung.this, "Work_Requirement.sqlite", null, 1);


        final List<String> listArea = new ArrayList<>();
        listArea.add("Hồ Chí Minh");
        listArea.add("Hà Nội");

        final List<String> listDegree = new ArrayList<>();
        listDegree.add("Trung cấp");
        listDegree.add("Đại học");

        final List <String> listMajor = new ArrayList<>();
        listMajor.add("Thực phẩm");
        listMajor.add("Xây dựng");
        listMajor.add("Công nghệ thông tin");
        listMajor.add("Bán hàng");

        final List <String> listWorkpos = new ArrayList<>();
        listWorkpos.add("Thực tập và dự án");
        listWorkpos.add("Nhân viên chính thức");
        listWorkpos.add("Nhân viên thời vụ");
        listWorkpos.add("Làm thêm ngoài giờ");
        listWorkpos.add("Bán thời gian");


        ArrayAdapter<String> adapterArea= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listArea);
        adapterArea.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        khuvuc.setAdapter(adapterArea);
        khuvuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_khuvuc= listArea.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt_khuvuc="";
            }
        });

        ArrayAdapter<String> adapterDegree= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listDegree);
        adapterArea.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        bangcap.setAdapter(adapterArea);
        bangcap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_bangcap= listArea.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt_bangcap="";
            }
        });

        ArrayAdapter<String> adapterMajor= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listMajor);
        adapterMajor.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        nganh.setAdapter(adapterMajor);
        nganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_nganh= listMajor.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt_nganh="";
            }
        });

        ArrayAdapter<String> adapterWorkpos= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listWorkpos);
        adapterMajor.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        vitri.setAdapter(adapterWorkpos);
        vitri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_vitri= listMajor.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt_vitri="";
            }
        });

        Dangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!kiemtra())
                {
                    Toast.makeText(thongtintuyendung.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
                WorkRequirement requirement;
                Intent intent = getIntent();
                Bundle bundle = intent.getBundleExtra("user");
                User user = (User)bundle.getSerializable("user");
                requirement = new WorkRequirement(
                        tenCongviec.getText().toString(),
                        tenCongty.getText().toString(),
                        nganh.getSelectedItem().toString(),
                        khuvuc.getSelectedItem().toString(),
                        luong.getText().toString(),
                        bangcap.getSelectedItem().toString(),
                        vitri.getSelectedItem().toString(),
                        Integer.parseInt(kinhNghiem.getText().toString()),
                        ngayKt.getText().toString());

            }
        });
    }

    private boolean kiemtra() {

        if(tenCongty.getText().toString().equals(""))
        {
            return false;
        }
        if(tenCongty.getText().toString().equals("")){
            return false;
        }
        if(tenCongviec.getText().toString().equals("")){
            return false;
        }
        if(luong.getText().toString().equals("")){
            return false;
        }
        if(kinhNghiem.getText().toString().equals(""))
        {
            return false;
        }
        if(ngayKt.getText().toString().equals("")){
            return false;
        }
        return true;
    }

}
