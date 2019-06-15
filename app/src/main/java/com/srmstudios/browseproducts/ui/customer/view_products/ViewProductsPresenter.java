package com.srmstudios.browseproducts.ui.customer.view_products;

import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

public class ViewProductsPresenter implements ViewProductsMVP.Presenter, ViewProductsAdapter.IViewProductsAdapter {
    private ViewProductsMVP.View view;
    private ViewProductsMVP.Model model;
    private ViewProductsAdapter adapter;

    public ViewProductsPresenter(ViewProductsMVP.View view, ViewProductsMVP.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getAllProducts() {
        model.getAllProducts(new IDatabaseListOps() {
            @Override
            public void onSuccess(List response) {
                List<Product> products = response;
                setupViewProductsAdapter(products);
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }

    private void setupViewProductsAdapter(List<Product> products){
        adapter = new ViewProductsAdapter(products,this);
        view.setRecyclerViewViewProductsAdapter(adapter);
    }

    @Override
    public void onItemClick(int productId,String vendorEmail) {
        view.openProductDetail(productId,vendorEmail);
    }
}
