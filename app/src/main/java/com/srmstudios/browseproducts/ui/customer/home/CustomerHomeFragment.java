package com.srmstudios.browseproducts.ui.customer.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.ui.customer.cart.CartActivity;
import com.srmstudios.browseproducts.ui.customer.view_products.ViewProductsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerHomeFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.btnBrowseProducts)
    protected Button btnBrowseProducts;
    @BindView(R.id.btnMyCart)
    protected Button btnMyCart;

    private Unbinder unbinder;

    public CustomerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_customer_home, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        btnBrowseProducts.setOnClickListener(this);
        btnMyCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBrowseProducts:{
                Intent intent = new Intent(getContext(), ViewProductsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnMyCart:{
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
                break;
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
