package com.srmstudios.browseproducts.ui.vendor.view_products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

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
