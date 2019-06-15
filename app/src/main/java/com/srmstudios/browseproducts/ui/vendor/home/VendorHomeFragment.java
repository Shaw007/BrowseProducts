package com.srmstudios.browseproducts.ui.vendor.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.ui.vendor.add_product.AddProductActivity;
import com.srmstudios.browseproducts.ui.vendor.dispatch_orders.DispatchOrdersActivity;
import com.srmstudios.browseproducts.ui.vendor.sales.SalesActivity;
import com.srmstudios.browseproducts.ui.vendor.vendor_products.VendorProductsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorHomeFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.btnAddProduct)
    protected Button btnAddProduct;
    @BindView(R.id.btnViewProducts)
    protected Button btnViewProducts;
    @BindView(R.id.btnDispatchOrders)
    protected Button btnDispatchOrders;
    @BindView(R.id.btnSales)
    protected Button btnSales;

    private Unbinder unbinder;

    public VendorHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_vendor_home, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        btnAddProduct.setOnClickListener(this);
        btnViewProducts.setOnClickListener(this);
        btnDispatchOrders.setOnClickListener(this);
        btnSales.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddProduct:{
                Intent intent = new Intent(getContext(), AddProductActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnViewProducts:{
                Intent intent = new Intent(getContext(), VendorProductsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnDispatchOrders:{
                Intent intent = new Intent(getContext(), DispatchOrdersActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnSales:{
                Intent intent = new Intent(getContext(), SalesActivity.class);
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
