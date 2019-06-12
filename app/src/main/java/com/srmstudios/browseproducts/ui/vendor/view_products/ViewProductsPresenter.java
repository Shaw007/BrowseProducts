package com.srmstudios.browseproducts.ui.vendor.view_products;

import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

public class ViewProductsPresenter implements ViewProductsMVP.Presenter {
    private ViewProductsMVP.View view;
    private ViewProductsMVP.Model model;
    private VendorProductsAdapter adapter;

    public ViewProductsPresenter(ViewProductsMVP.View view, ViewProductsMVP.Model model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void getVendorProducts(String vendorName) {
        model.getVendorProducts(vendorName, new IDatabaseListOps() {
            @Override
            public void onSuccess(List response) {
                List<Product> products = response;
                setupVendorProductsAdapter(products);
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message + ": " + throwable.getMessage());
            }
        });
    }

    private void setupVendorProductsAdapter(List<Product> products){
        adapter = new VendorProductsAdapter(products);
        view.setRecyclerViewProductsAdapter(adapter);
    }
}







