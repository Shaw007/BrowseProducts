package com.srmstudios.browseproducts.ui.vendor.vendor_products;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.util.DialogUtils;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.interfaces.DialogBoxTwoButtonInputCallback;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;
import com.srmstudios.browseproducts.util.singleton.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorProductsFragment extends Fragment implements VendorProductsMVP.View {
    @BindView(R.id.recyclerVendorProducts)
    protected RecyclerView recyclerVendorProducts;
    @BindView(R.id.txtNoDateFound)
    protected TextView txtNoDateFound;

    private Unbinder unbinder;
    private VendorProductsPresenter presenter;

    public VendorProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new VendorProductsPresenter(this,
                new VendorProductsModel(BrowseProductsDatabase.getInstance(getContext()).getAppDatabase()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_vendor_products, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v) {
        unbinder = ButterKnife.bind(this, v);

        recyclerVendorProducts.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter.getVendorProducts(SessionManager.getInstance(getContext()).getUser().getName());
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
    public void setRecyclerViewVendorProductsAdapter(VendorProductsAdapter adapter) {
        if(adapter == null){
            return;
        }
        if(adapter.getItemCount() == 0){
            txtNoDateFound.setVisibility(View.VISIBLE);
            txtNoDateFound.setText(Utils.getStringFromResourceId(getContext(),R.string.no_products_found));
        }else {
            recyclerVendorProducts.setAdapter(adapter);
        }
    }

    @Override
    public void showEditDiscountDialog(int productId, int currentDiscount) {
        DialogUtils.showTwoButonDiscountInputDialogBox(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.edit_discount),
                String.valueOf(currentDiscount),
                new DialogBoxTwoButtonInputCallback() {
                    @Override
                    public void onSuccess(String input) {
                        presenter.updateProductDiscount(productId,Integer.parseInt(input));
                    }

                    @Override
                    public void onFailure() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}











