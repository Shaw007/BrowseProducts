package com.srmstudios.browseproducts.ui.vendor.vendor_products;

import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class VendorProductsActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new VendorProductsFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
