package com.srmstudios.browseproducts.ui.useful_resources;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class UsefulResourcesActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new UsefulResourcesFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
