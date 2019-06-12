package com.srmstudios.browseproducts.ui.vendor.sign_up;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class SignUpActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new SignUpFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setVisibility(View.GONE);
    }

}
