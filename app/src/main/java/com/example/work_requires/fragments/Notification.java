package com.example.work_requires.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.work_requires.R;

public class Notification extends Fragment {

    View view;
    ListView notiListView;

    public Notification() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.view_notification, container, false);
        notiListView = view.findViewById(R.id.notiListView);
        initialize();
        return view;
    }

    private void initialize() {

    }
}
