package com.srmstudios.browseproducts.ui.splash;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.User;
import com.srmstudios.browseproducts.ui.account_selection.AccountSelectionActivity;
import com.srmstudios.browseproducts.ui.customer.home.CustomerHomeActivity;
import com.srmstudios.browseproducts.ui.sign_in.SignInActivity;
import com.srmstudios.browseproducts.ui.vendor.VendorHomeActivity;
import com.srmstudios.browseproducts.util.singleton.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_splash, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = SessionManager.getInstance(getContext()).getUser();
                if(user == null) {
                    Intent intent = new Intent(getContext(), SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    if(user.isCustomer()){
                        Intent intent = new Intent(getContext(), CustomerHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getContext(), VendorHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        },500);
    }
}













