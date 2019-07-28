package com.example.work_requires;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    // Danh sách tab trong TabLayout
    List<Fragment>tab;

    // Người dùng
    User user; //0 là nhà tuyển dụng --- 1 là người tìm việc

    // Các fragment sử dụng
    JobsFragment fracment1;

    public PagerAdapter(FragmentManager fm, User user) {
        super(fm);
//        fracment1 = new JobsFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("user", user);
//        fracment1.setContext(context);
//        fracment1.setArguments(bundle);
//        tab.add(fracment1);
        tab = new ArrayList<>();
        this.user = user;
    }

    public void addFragment(Fragment frag){
        tab.add(frag);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if(user.getType().equals("2"))
                    return tab.get(0);
                else
                    break;
            case 1:
                return tab.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return tab.size();
    }
}
