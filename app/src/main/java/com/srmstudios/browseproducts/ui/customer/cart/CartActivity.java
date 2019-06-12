package com.srmstudios.browseproducts.ui.customer.cart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class CartActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new CartFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
