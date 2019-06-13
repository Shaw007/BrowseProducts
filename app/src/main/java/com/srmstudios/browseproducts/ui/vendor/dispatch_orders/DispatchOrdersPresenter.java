package com.srmstudios.browseproducts.ui.vendor.dispatch_orders;

import com.srmstudios.browseproducts.data.room.model.VendorOrder;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.List;

public class DispatchOrdersPresenter implements DispatchOrdersMVP.Presenter, DispatchOrdersAdapter.IDispatchOrdersAdapter {
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

    @Override
    public void dispatchOrder(String orderId) {
        model.dispatchOrder(orderId, new IDatabaseOps() {
            @Override
            public void onSuccess(Object response) {
                view.showDialogMessage(response.toString());
                if(response.toString().contains("successfully")){
                    adapter.dispatchOrder(orderId);
                }
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }

    private void setupRecyclerViewVendorOrdersAdapter(List<VendorOrder> vendorOrders){
        adapter = new DispatchOrdersAdapter(vendorOrders,this);
        view.setRecyclerViewVendorOrdersAdapter(adapter);
    }

    @Override
    public void onItemClick(String orderId) {
        view.showDispatchingCofirmationDialog(orderId);
    }
}














