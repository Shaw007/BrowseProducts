package com.srmstudios.browseproducts.ui.customer.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.ui.customer.cart.CartActivity;
import com.srmstudios.browseproducts.ui.customer.order_history.OrderHistoryActivity;
import com.srmstudios.browseproducts.ui.customer.view_products.ViewProductsActivity;
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
public class CustomerHomeFragment extends Fragment {
    @BindView(R.id.recyclerViewHome)
    protected RecyclerView recyclerViewHome;

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

        recyclerViewHome.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewHome.addItemDecoration(new GridSpacingItemDecoration(2, 16, true));

        List<HomeItem> homeItems = new ArrayList<>();
        homeItems.add(new HomeItem(R.drawable.products, Utils.getStringFromResourceId(getContext(),R.string.browse_products)));
        homeItems.add(new HomeItem(R.drawable.cart, Utils.getStringFromResourceId(getContext(),R.string.my_cart)));
        homeItems.add(new HomeItem(R.drawable.customer_orders, Utils.getStringFromResourceId(getContext(),R.string.order_history)));
        recyclerViewHome.setAdapter(new HomeAdapter(homeItems, new HomeAdapter.IHomeAdapter() {
            @Override
            public void onItemClick(HomeItem homeItem) {
                Intent intent = null;
                if(homeItem.getName().equals(Utils.getStringFromResourceId(getContext(),R.string.browse_products))){
                    intent = new Intent(getContext(), ViewProductsActivity.class);
                }else if(homeItem.getName().equals(Utils.getStringFromResourceId(getContext(),R.string.my_cart))){
                    intent = new Intent(getContext(), CartActivity.class);
                }else if(homeItem.getName().equals(Utils.getStringFromResourceId(getContext(),R.string.order_history))){
                    intent = new Intent(getContext(), OrderHistoryActivity.class);
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
