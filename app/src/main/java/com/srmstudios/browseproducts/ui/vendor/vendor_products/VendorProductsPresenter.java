package com.srmstudios.browseproducts.ui.vendor.vendor_products;

import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

public class VendorProductsPresenter implements VendorProductsMVP.Presenter {
    private VendorProductsMVP.View view;
    private VendorProductsMVP.Model model;
    private VendorProductsAdapter adapter;

    public VendorProductsPresenter(VendorProductsMVP.View view, VendorProductsMVP.Model model) {
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
                view.showDialogMessage(message);
            }
        });
    }

    private void setupVendorProductsAdapter(List<Product> products){
        adapter = new VendorProductsAdapter(products);
        view.setRecyclerViewVendorProductsAdapter(adapter);
    }
}







