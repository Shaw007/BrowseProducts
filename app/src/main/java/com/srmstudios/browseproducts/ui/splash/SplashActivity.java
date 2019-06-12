package com.srmstudios.browseproducts.ui.splash;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new SplashFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setVisibility(View.GONE);
    }
}
