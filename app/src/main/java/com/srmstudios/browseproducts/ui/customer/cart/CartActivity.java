package com.srmstudios.browseproducts.ui.customer.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.ui.BaseActivity;

public class CartActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new CartFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if(fragment instanceof CartFragment){
            CartFragment cartFragment = (CartFragment) fragment;
            cartFragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
