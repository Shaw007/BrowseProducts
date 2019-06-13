package com.srmstudios.browseproducts.ui.vendor.add_product;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public interface AddProductMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void clearInputFields();
    }

    interface Presenter{
        void onClickBtnAddProduct(String productImage,
                        String productName,
                        String productDesc,
                        String productPrice,
                        String productVendor,
                        String productVendorEmail);
    }

    interface Model{
        void addProduct(String productImage,
                        String productName,
                        String productDesc,
                        String productPrice,
                        String productVendor,
                        String productVendorEmail,
                        IDatabaseOps iDatabaseOps);
    }
}
