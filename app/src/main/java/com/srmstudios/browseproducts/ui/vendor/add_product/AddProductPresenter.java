package com.srmstudios.browseproducts.ui.vendor.add_product;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public class AddProductPresenter implements AddProductMVP.Presenter {
    private AddProductMVP.View view;
    private AddProductMVP.Model model;

    public AddProductPresenter(AddProductMVP.View view, AddProductMVP.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onClickBtnAddProduct(String productImage, String productName, String productDesc, String productPrice, String productVendor,String productVendorEmail,int productDiscount) {
        model.addProduct(productImage,
                productName,
                productDesc,
                productPrice,
                productVendor,
                productVendorEmail,
                productDiscount,
                new IDatabaseOps() {
                    @Override
                    public void onSuccess(Object response) {
                        if(response instanceof String){
                            view.showDialogMessage(response.toString());
                        }else if(response instanceof Product){
                            Product product = (Product) response;
                            view.showDialogMessage(R.string.product_added_successfully);
                            view.clearInputFields();
                        }
                    }

                    @Override
                    public void onError(String message, Throwable throwable) {
                        view.showDialogMessage(message);
                    }
                });
    }
}
