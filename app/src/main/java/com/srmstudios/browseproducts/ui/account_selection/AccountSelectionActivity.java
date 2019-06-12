package com.srmstudios.browseproducts.ui.account_selection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.srmstudios.browseproducts.ui.BaseActivity;

public class AccountSelectionActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new AccountSelectionFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setVisibility(View.GONE);
    }
}
