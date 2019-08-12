package com.example.work_requires;

//import android.content.DialogInterface;
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//import android.view.View;
//import android.widget.Toast;

//import com.example.work_requires.adapters.CustomAdapter2;
import com.example.work_requires.adapters.PagerAdapter;
import com.example.work_requires.fragments.JobPostedFragment;
//import com.example.work_requires.models.WorkRequirement;
import com.example.work_requires.fragments.MenuFragment;
import com.example.work_requires.fragments.Notification;
import com.example.work_requires.models.Company;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
//import java.util.List;

public class MainActivityCompany extends AppCompatActivity implements Serializable {

    //Database


    //Recycle View

    //Fragments
    JobPostedFragment frag1;
    Notification frag2;
    Notification frag3;
    MenuFragment frag4;

    //Pager
    ViewPager mainViewPager;

    //Tab layout
    TabLayout tabLayout;

    //User
    Company user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_company);
//        database = FirebaseDatabase.getInstance().getReference();
    }

    //Để đảm bảo khi quay lại activity này thì dữ liệu được cập nhật
    @Override
    protected void onResume() {
        initialize();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                switch (tabLayout.getSelectedTabPosition()){
                    case 0:
                        frag1.getAdapter().getFilter().filter(s);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                return false;
            }
        });
        return true;
    }

    private void initialize() {
        user = (Company) getIntent().getSerializableExtra("user");
        mainViewPager = findViewById(R.id.mainPager);
        tabLayout = findViewById(R.id.tabMenu);
        frag1 = new JobPostedFragment();
        frag2 = new Notification();
        frag3 = new Notification();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("user", user);
        frag1.setArguments(bundle1);
        frag4 = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("company user", user);
        frag1.setArguments(bundle);
        frag2.setArguments(bundle);
        frag3.setArguments(bundle);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(frag1);
        pagerAdapter.addFragment(frag2);
        pagerAdapter.addFragment(frag3);
        pagerAdapter.addFragment(frag4);
        mainViewPager.setAdapter(pagerAdapter);
        mainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        setUpTabLayout();
    }

    private void setUpTabLayout() {
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#0044CE"), PorterDuff.Mode.SRC_IN);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("Tab position", String.valueOf(tab.getPosition()));
                mainViewPager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(Color.parseColor("#0044CE"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#CE000000"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}
