package com.example.administrator.mychart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PluginFragment extends BaseFragment {

    private View rootView;

    @Override
    public View setContentUI(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.child, container, false);
        init();
        return rootView;
    }

    @Override
    public void init() {

    }

    @Override
    public void control() {

    }
}
