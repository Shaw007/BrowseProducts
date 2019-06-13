package com.srmstudios.browseproducts.ui.vendor.dispatch_orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class DispatchOrdersActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new DispatchOrdersFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
