package com.example.work_requires;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.work_requires.R.id;
import com.example.work_requires.R.layout;
import com.example.work_requires.adapters.CustomAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //List items
    List<WorkRequirement> requirementList;

    //RecycleView
    RecyclerView requireRecycleView;
    CustomAdapter adapter;

    //Database
    SQLiteManagement workRequireDatabase;

    //User
    User user;

    //Variables
    WorkRequirement dummyRequire;
    String partten = "dd/MM/yyyy";

    //Format
    SimpleDateFormat dateFormat;

    //View Pager
    ViewPager mainViewPager;

    //Tab Layout
    TabLayout tabLayout;

    JobsFragment frag1;
    JobsSavedFrag frag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main_can);
        initialize();
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                frag1.getAdapter().getFilter().filter(s);
                frag2.getAdapter().getFilter().filter(s);
                return false;
            }
        });
        MenuItem signOut = menu.findItem(id.signOut);

        return true;
    }

    private void initialize() {
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        mainViewPager = findViewById(id.mainPager);
        tabLayout = (TabLayout) findViewById(id.tabMenu);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),user);
        frag1 = new JobsFragment();
        frag1.setContext(MainActivity.this);
        frag2 = new JobsSavedFrag();
        frag2.setContext(MainActivity.this);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        frag1.setArguments(bundle);
        frag2.setArguments(bundle);
        pagerAdapter.addFragment(frag1);
        pagerAdapter.addFragment(frag2);
        mainViewPager.setAdapter(pagerAdapter);
        mainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setupWithViewPager(mainViewPager);
        setUpTabLayout();

    }

    private void setUpTabLayout() {
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#0044CE"), PorterDuff.Mode.SRC_IN);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
}
