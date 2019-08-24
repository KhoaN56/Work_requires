package com.example.work_requires;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
//import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.work_requires.R.id;
import com.example.work_requires.R.layout;
//import com.example.work_requires.adapters.CustomAdapter;
import com.example.work_requires.adapters.PagerAdapter;
import com.example.work_requires.fragments.JobsFragment;
import com.example.work_requires.fragments.JobsSavedFrag;
import com.example.work_requires.fragments.MenuFragment;
import com.example.work_requires.fragments.Notification;
import com.example.work_requires.models.DataHolder;
import com.example.work_requires.models.User;

import java.util.ArrayList;
import java.util.List;

//import java.text.SimpleDateFormat;
//import java.util.List;

public class MainActivity extends AppCompatActivity {
    //List items
//    List<WorkRequirement> requirementList;

    //RecycleView
//    RecyclerView requireRecycleView;
//    CustomAdapter adapter;

    //Database
//    SQLiteManagement workRequireDatabase;

    //User
    User user;

    //Variables
//    WorkRequirement dummyRequire;
//    String pattern = "dd/MM/yyyy";

    //Format
//    SimpleDateFormat dateFormat;

    //View Pager
    ViewPager mainViewPager;

    //Tab Layout
    TabLayout tabLayout;

    JobsFragment frag1;
    JobsSavedFrag frag2;
    Notification frag3;
    MenuFragment frag4;

    private List<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main_can);
        initialize();
    }

    @Override
    protected void onResume() {
        setUpTabLayout();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                switch (tabLayout.getSelectedTabPosition()){
                    case 0:
                        frag1.getAdapter().getFilter().filter(s);
                        break;
                    case 1:
                        frag2.getAdapter().getFilter().filter(s);
                        break;
                }
                return false;
            }
        });
//        MenuItem signOut = menu.findItem(id.signOut);

        return true;
    }

    private void initialize() {
//        Intent intent = getIntent();
        user = DataHolder.getNormalUser();
        user.loadAppliedId();
        user.loadSavedIdList();
        name =  new ArrayList<>();
        name.add("Thông tin tuyển dụng");
        name.add("Công việc đã lưu");
        name.add("Thông báo");
        name.add("Menu");
        mainViewPager = findViewById(id.mainPager);
        tabLayout = findViewById(id.tabMenu);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        frag1 = new JobsFragment();
        frag1.setContext(MainActivity.this);
        frag2 = new JobsSavedFrag();
        frag2.setContext(MainActivity.this);
        frag4 = new MenuFragment();
        frag3 = new Notification();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        frag1.setArguments(bundle);
        frag2.setArguments(bundle);
        frag3.setArguments(bundle);
        frag4.setArguments(bundle);
        pagerAdapter.addFragment(frag1);
        pagerAdapter.addFragment(frag2);
        pagerAdapter.addFragment(frag3);
        pagerAdapter.addFragment(frag4);
        mainViewPager.setAdapter(pagerAdapter);
        mainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setupWithViewPager(mainViewPager);
        setUpTabLayout();
        DataHolder.setNormalUser(user);
    }

    private void setUpTabLayout() {

        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getIcon().setColorFilter(Color.parseColor("#0044CE"), PorterDuff.Mode.SRC_IN);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("Tab position", String.valueOf(tab.getPosition()));
                mainViewPager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(Color.parseColor("#0044CE"), PorterDuff.Mode.SRC_IN);
                getSupportActionBar().setTitle(name.get(tab.getPosition()));
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
}
