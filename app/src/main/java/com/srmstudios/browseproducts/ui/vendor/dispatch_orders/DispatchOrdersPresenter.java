package com.srmstudios.browseproducts.ui.vendor.dispatch_orders;

import com.srmstudios.browseproducts.data.room.model.VendorOrder;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

public class DispatchOrdersPresenter implements DispatchOrdersMVP.Presenter {
    private DispatchOrdersMVP.View view;
    private DispatchOrdersMVP.Model model;
    private DispatchOrdersAdapter adapter;

    public DispatchOrdersPresenter(DispatchOrdersMVP.View view, DispatchOrdersMVP.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getVendorOrders(String vendorUserEmail) {
        model.getVendorOrders(vendorUserEmail, new IDatabaseListOps() {
            @Override
            public void onSuccess(List response) {
                List<VendorOrder> vendorOrders = response;
                setupRecyclerViewVendorOrdersAdapter(vendorOrders);
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }

    private void setupRecyclerViewVendorOrdersAdapter(List<VendorOrder> vendorOrders){
        adapter = new DispatchOrdersAdapter(vendorOrders);
        view.setRecyclerViewVendorOrdersAdapter(adapter);
    }
}














