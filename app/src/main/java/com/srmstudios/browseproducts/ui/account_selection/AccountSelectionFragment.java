package com.srmstudios.browseproducts.ui.account_selection;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.ui.sign_in.SignInActivity;
import com.srmstudios.browseproducts.ui.sign_up.SignUpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountSelectionFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.btnVendor)
    protected Button btnVendor;
    @BindView(R.id.btnCustomer)
    protected Button btnCustomer;

    private Unbinder unbinder;

    public AccountSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account_selection, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        btnVendor.setOnClickListener(this);
        btnCustomer.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnVendor:{
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                intent.putExtra(SignUpActivity.KEY_IS_CUSTOMER,false);
                startActivity(intent);
                break;
            }
            case R.id.btnCustomer:{
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                intent.putExtra(SignUpActivity.KEY_IS_CUSTOMER,true);
                startActivity(intent);
                break;
            }
        }
    }
}










