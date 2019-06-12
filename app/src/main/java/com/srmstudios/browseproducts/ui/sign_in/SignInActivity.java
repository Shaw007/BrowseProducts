package com.srmstudios.browseproducts.ui.sign_in;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class SignInActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new SignInFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setVisibility(View.GONE);
    }
}
