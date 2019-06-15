package com.srmstudios.browseproducts.ui.vendor.sales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class SalesActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new SalesFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
