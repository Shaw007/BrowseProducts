package com.srmstudios.browseproducts.ui.customer.view_products;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class ViewProductsActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new ViewProductsFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
