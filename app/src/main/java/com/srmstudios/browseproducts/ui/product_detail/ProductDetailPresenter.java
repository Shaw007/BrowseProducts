package com.srmstudios.browseproducts.ui.product_detail;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.Cart;
import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.List;

public class ProductDetailPresenter implements ProductDetailMVP.Presenter {
    private ProductDetailMVP.View view;
    private ProductDetailMVP.Model model;

    public ProductDetailPresenter(ProductDetailMVP.View view, ProductDetailMVP.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getProductDetails(int productId) {
        model.getProductDetails(productId, new IDatabaseOps() {
            @Override
            public void onSuccess(Object response) {
                if(response instanceof Product) {
                    Product product = (Product) response;
                    view.setupProductDetails(product);
                }
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message );
            }
        });
    }

    @Override
    public void getVendorAndProductStats(int productId, String vendorEmail) {
        model.getVendorAndProductStats(productId, vendorEmail, new IDatabaseOps() {
            @Override
            public void onSuccess(Object response) {
                List<Integer> vendorAndProductStats = (List<Integer>) response;
                view.setupVendorAndProductStats(vendorAndProductStats);
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message );
            }
        });
    }

    @Override
    public void addToCart(String userEmail,int productId, int quantity) {
        model.addToCart(userEmail,productId, quantity, new IDatabaseOps() {
            @Override
            public void onSuccess(Object response) {
                if(response instanceof Cart){
                    Cart cart = (Cart) response;
                    view.showDialogMessage(R.string.product_added_in_cart_successfully);
                }else {
                    view.showDialogMessage(response.toString());
                }
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }
}










