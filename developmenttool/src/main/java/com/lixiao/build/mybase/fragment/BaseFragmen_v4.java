package com.lixiao.build.mybase.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.ToastUtil;
import com.lixiao.build.mybase.activity.util.ActivityUtil;

public abstract class BaseFragmen_v4 extends Fragment {
    public View view;

    protected abstract int initViewLayout();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initControl();

    protected abstract void viewIsShow();

    protected abstract void viewIsPause();

    protected abstract void close();

    protected abstract void changeConfig();

    @Override
    public void onAttach(Activity activity) {

        LG.d(getClass().toString(), "onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LG.d(getClass().toString(), "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        LG.d(getClass().toString(), "onCreateView()");
        view = inflater.inflate(initViewLayout(), container, false);
        initView();
        initData();
        initControl();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        LG.d(getClass().toString(), "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {

        LG.d(getClass().toString(), "onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {

        LG.d(getClass().toString(), "onResume()");
        super.onResume();
        viewIsShow();
    }

    @Override
    public void onPause() {

        LG.d(getClass().toString(), "onPause()");
        super.onPause();
        viewIsPause();
    }

    @Override
    public void onStop() {
        LG.d(getClass().toString(), "onStop()");
        super.onStop();
    }



    @Override
    public void onDestroyView() {

        LG.d(getClass().toString(), "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LG.d(getClass().toString(), "onDestroy()");
        super.onDestroy();
        close();
    }

    @Override
    public void onDetach() {
        LG.d(getClass().toString(), "onDetach()");
        super.onDetach();
    }

    public void toActivity(Class toclass) {
        ActivityUtil.toActivity(getContext(),toclass);
    }

    public void toActivity(Class toclass, String Gsonstring) {
        ActivityUtil.toActivity(getContext(),toclass,Gsonstring);
    }

    public void showToast(String str) {
        ToastUtil.showToast(str);
    }

    public String getIntentString() {
        return ActivityUtil.getIntentString(getActivity().getIntent());
    }

    public String getRsString(int strRs){
        return getResources().getString(strRs);
    }

    public int getRsColor(int rsColor){
        return getResources().getColor(rsColor);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeConfig();
    }
}
