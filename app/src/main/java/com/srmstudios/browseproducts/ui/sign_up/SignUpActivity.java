package com.srmstudios.browseproducts.ui.sign_up;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class SignUpActivity extends BaseActivity {
    public static final String KEY_IS_CUSTOMER = "com.srmstudios.browseproducts.ui.sign_in.KEY_IS_CUSTOMER";

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
