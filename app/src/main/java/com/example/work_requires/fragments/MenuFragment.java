package com.example.work_requires.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.work_requires.Login;
import com.example.work_requires.R;
import com.example.work_requires.SeeProfileCandidate;
import com.example.work_requires.SeeProfileCompany;
import com.example.work_requires.ViewPersonalCV;
import com.example.work_requires.adapters.CustomMenuAdapter;
import com.example.work_requires.models.Company;
import com.example.work_requires.models.DataHolder;
import com.example.work_requires.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class MenuFragment extends Fragment {

    View view;
//    CardView logOut, info;
    User normalUser;
    Company compUser;

    ListView menuListView;
    CustomMenuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.menu_view, container, false);
        menuListView = view.findViewById(R.id.menuListView);
        adapter = new CustomMenuAdapter(R.layout.menu_item, getContext());
        menuListView.setAdapter(adapter);
//        logOut = view.findViewById(R.id.logout);
//        info = view.findViewById(R.id.info);
//        Bundle bundle = getArguments();
//        assert bundle != null;
        normalUser = DataHolder.getNormalUser();
        compUser = DataHolder.getCompUser();
//        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(normalUser != null)
            if(compUser!=null){
                DataHolder.clearNormalUser();
                normalUser = null;
            }
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(normalUser != null){
                            Intent toPersonalInfo = new Intent(getContext(), SeeProfileCandidate.class);
//                    toPersonalInfo.putExtra("user", normalUser);
                            startActivity(toPersonalInfo);
                        }
                        else {
                            Intent toPersonalInfo = new Intent(getContext(), SeeProfileCompany.class);
//                    toPersonalInfo.putExtra("user", compUser);
                            startActivity(toPersonalInfo);
                        }
                        break;
                    case 1:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có chắc muốn thoát ứng dụng không?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                DataHolder.clearAll();
                                auth.signOut();
                                Intent toLogIn = new Intent(getContext(), Login.class);
                                startActivity(toLogIn);
                                getActivity().finish();
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
                        break;
                }
            }
        });
//        info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(normalUser != null){
//                    Intent toPersonalInfo = new Intent(getContext(), SeeProfileCandidate.class);
////                    toPersonalInfo.putExtra("user", normalUser);
//                    startActivity(toPersonalInfo);
//                }
//                else {
//                    Intent toPersonalInfo = new Intent(getContext(), SeeProfileCompany.class);
////                    toPersonalInfo.putExtra("user", compUser);
//                    startActivity(toPersonalInfo);
//                }
//            }
//        });
//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Thông báo");
//                builder.setMessage("Bạn có chắc muốn thoát ứng dụng không?");
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        FirebaseAuth auth = FirebaseAuth.getInstance();
//                        DataHolder.clearAll();
//                        auth.signOut();
//                        Intent toLogIn = new Intent(getContext(), Login.class);
//                        startActivity(toLogIn);
//                        getActivity().finish();
//                    }
//                });
//                builder.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        onResume();
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });
        return view;
    }
}
