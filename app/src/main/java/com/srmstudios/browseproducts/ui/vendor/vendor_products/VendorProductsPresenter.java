package com.srmstudios.browseproducts.ui.vendor.vendor_products;

import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.List;

public class VendorProductsPresenter implements VendorProductsMVP.Presenter, VendorProductsAdapter.IVendorProductsAdapter {
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

    @Override
    public void updateProductDiscount(int productId, int newDiscount) {
        model.updateProductDiscount(productId, newDiscount, new IDatabaseOps() {
            @Override
            public void onSuccess(Object response) {
                view.showDialogMessage(response.toString());
                if(response.toString().contains("successfully")){
                    adapter.updateProductDiscount(productId,newDiscount);
                }
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }

    private void setupVendorProductsAdapter(List<Product> products){
        adapter = new VendorProductsAdapter(products,this);
        view.setRecyclerViewVendorProductsAdapter(adapter);
    }

    @Override
    public void onItemClick(int productId,int currentDiscount) {
        view.showEditDiscountDialog(productId,currentDiscount);
    }
}







