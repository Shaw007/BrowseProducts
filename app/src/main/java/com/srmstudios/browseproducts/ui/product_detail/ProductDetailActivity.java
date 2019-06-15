package com.srmstudios.browseproducts.ui.product_detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class ProductDetailActivity extends BaseActivity {
    public static final String KEY_PRODUCT_ID = "com.srmstudios.browseproducts.ui.product_detail.KEY_PRODUCT_ID";
    public static final String KEY_VENDOR_EMAIL = "com.srmstudios.browseproducts.ui.product_detail.KEY_VENDOR_EMAIL";

    @Override
    public Fragment getFragment() {
        return new ProductDetailFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
