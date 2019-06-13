package com.srmstudios.browseproducts.ui.customer.order_history;

import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class OrderHistoryActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new OrderHistoryFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
