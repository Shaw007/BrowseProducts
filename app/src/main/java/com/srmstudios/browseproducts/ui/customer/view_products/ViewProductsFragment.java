package com.srmstudios.browseproducts.ui.customer.view_products;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.ui.product_detail.ProductDetailActivity;
import com.srmstudios.browseproducts.util.DialogUtils;
import com.srmstudios.browseproducts.util.GridSpacingItemDecoration;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProductsFragment extends Fragment implements ViewProductsMVP.View {
    @BindView(R.id.recyclerViewProducts)
    protected RecyclerView recyclerViewProducts;

    private Unbinder unbinder;
    private ViewProductsPresenter presenter;

    public ViewProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ViewProductsPresenter(this,
                new ViewProductsModel(BrowseProductsDatabase.getInstance(getContext()).getAppDatabase()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_products, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        recyclerViewProducts.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewProducts.addItemDecoration(new GridSpacingItemDecoration(2, 16, true));

        presenter.getAllProducts();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showDialogMessage(int resourceId) {
        DialogUtils.showSingleButtonDialog(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                Utils.getStringFromResourceId(getContext(),resourceId));
    }

    @Override
    public void showDialogMessage(String message) {
        DialogUtils.showSingleButtonDialog(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                message);
    }

    @Override
    public void setRecyclerViewViewProductsAdapter(ViewProductsAdapter adapter) {
        recyclerViewProducts.setAdapter(adapter);
    }

    @Override
    public void openProductDetail(int productId) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.KEY_PRODUCT_ID,productId);
        startActivity(intent);
    }
}
