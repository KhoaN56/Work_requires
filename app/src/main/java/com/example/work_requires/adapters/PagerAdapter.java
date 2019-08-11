package com.example.work_requires.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.work_requires.fragments.JobsFragment;
import com.example.work_requires.models.User;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    // Danh sách tab trong TabLayout
    private List<Fragment>tab;

    // Người dùng
//    private User user;

    // Các fragment sử dụng
    JobsFragment fracment1;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
//        fracment1 = new JobsFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("cv", cv);
//        fracment1.setContext(context);
//        fracment1.setArguments(bundle);
//        tab.add(fracment1);
        tab = new ArrayList<>();
//        this.user = user;
    }

    public void addFragment(Fragment frag){
        tab.add(frag);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return tab.get(position);
            case 1:
                return tab.get(position);
            case 2:
                return tab.get(position);
            case 3:
                return tab.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return tab.size();
    }
}
