package com.srmstudios.browseproducts.ui.vendor.add_product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class AddProductActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new AddProductFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
