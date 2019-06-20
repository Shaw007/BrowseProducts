package com.srmstudios.browseproducts.ui.vendor.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.ui.customer.home.HomeAdapter;
import com.srmstudios.browseproducts.ui.useful_resources.UsefulResourcesActivity;
import com.srmstudios.browseproducts.ui.vendor.add_product.AddProductActivity;
import com.srmstudios.browseproducts.ui.vendor.dispatch_orders.DispatchOrdersActivity;
import com.srmstudios.browseproducts.ui.vendor.sales.SalesActivity;
import com.srmstudios.browseproducts.ui.vendor.vendor_products.VendorProductsActivity;
import com.srmstudios.browseproducts.util.GridSpacingItemDecoration;
import com.srmstudios.browseproducts.util.HomeItem;
import com.srmstudios.browseproducts.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorHomeFragment extends Fragment {
    @BindView(R.id.recyclerViewHome)
    protected RecyclerView recyclerViewHome;

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

        recyclerViewHome.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewHome.addItemDecoration(new GridSpacingItemDecoration(2, 16, true));

        List<HomeItem> homeItems = new ArrayList<>();
        homeItems.add(new HomeItem(R.drawable.add_products, Utils.getStringFromResourceId(getContext(),R.string.add_products)));
        homeItems.add(new HomeItem(R.drawable.products, Utils.getStringFromResourceId(getContext(),R.string.my_products)));
        homeItems.add(new HomeItem(R.drawable.dispatch_orders, Utils.getStringFromResourceId(getContext(),R.string.dispatch_orders)));
        homeItems.add(new HomeItem(R.drawable.sales, Utils.getStringFromResourceId(getContext(),R.string.sales)));
        homeItems.add(new HomeItem(R.drawable.resources, Utils.getStringFromResourceId(getContext(),R.string.useful_resource)));
        recyclerViewHome.setAdapter(new HomeAdapter(homeItems, new HomeAdapter.IHomeAdapter() {
            @Override
            public void onItemClick(HomeItem homeItem) {
                Intent intent = null;
                if(homeItem.getName().equals(Utils.getStringFromResourceId(getContext(),R.string.add_products))){
                    intent = new Intent(getContext(), AddProductActivity.class);
                }else if(homeItem.getName().equals(Utils.getStringFromResourceId(getContext(),R.string.my_products))){
                    intent = new Intent(getContext(), VendorProductsActivity.class);
                }else if(homeItem.getName().equals(Utils.getStringFromResourceId(getContext(),R.string.dispatch_orders))){
                    intent = new Intent(getContext(), DispatchOrdersActivity.class);
                }else if(homeItem.getName().equals(Utils.getStringFromResourceId(getContext(),R.string.sales))){
                    intent = new Intent(getContext(), SalesActivity.class);
                }else if(homeItem.getName().equals(Utils.getStringFromResourceId(getContext(),R.string.useful_resource))){
                    intent = new Intent(getContext(), UsefulResourcesActivity.class);
                }
                if(intent != null) {
                    startActivity(intent);
                }
            }
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
