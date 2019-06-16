package com.srmstudios.browseproducts.ui.vendor.dispatch_orders;


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
import com.srmstudios.browseproducts.util.interfaces.DialogBoxTwoButtonCallback;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;
import com.srmstudios.browseproducts.util.singleton.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DispatchOrdersFragment extends Fragment implements DispatchOrdersMVP.View {
    @BindView(R.id.recyclerViewVendorOrders)
    protected RecyclerView recyclerViewVendorOrders;
    @BindView(R.id.txtNoDateFound)
    protected TextView txtNoDateFound;

    private Unbinder unbinder;
    private DispatchOrdersPresenter presenter;

    public DispatchOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DispatchOrdersPresenter(this,
                new DispatchOrdersModel(BrowseProductsDatabase.getInstance(getContext()).getAppDatabase()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dispatch_orders, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v) {
        unbinder = ButterKnife.bind(this, v);

        recyclerViewVendorOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter.getVendorOrders(SessionManager.getInstance(getContext()).getUser().getEmail());
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
    public void setRecyclerViewVendorOrdersAdapter(DispatchOrdersAdapter adapter) {
        if(adapter == null){
            return;
        }
        if(adapter.getItemCount() == 0){
            txtNoDateFound.setVisibility(View.VISIBLE);
            txtNoDateFound.setText(Utils.getStringFromResourceId(getContext(),R.string.no_orders_found));
        }else {
            recyclerViewVendorOrders.setAdapter(adapter);
        }
    }

    @Override
    public void showDispatchingCofirmationDialog(String orderId) {
        DialogUtils.showTwoButonDialogBox(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                Utils.getStringFromResourceId(getContext(),R.string.are_you_sure_dispatch) + orderId + "?",
                new DialogBoxTwoButtonCallback() {
                    @Override
                    public void onSuccess() {
                        presenter.dispatchOrder(orderId);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
    }

    @Override
    public void showDeliveryLocationOnMap(double latitude, double longitude) {
        Utils.showLocationOnMap(getContext(),latitude,longitude);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
