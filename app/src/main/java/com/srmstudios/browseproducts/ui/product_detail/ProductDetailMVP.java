package com.srmstudios.browseproducts.ui.product_detail;

import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.List;

public interface ProductDetailMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setupProductDetails(Product product);
        void setupVendorAndProductStats(List<Integer> vendorAndProductStats);
    }

    interface Presenter{
        void getProductDetails(int productId);
        void getVendorAndProductStats(int productId,String vendorEmail);
        void addToCart(String userEmail,int productId,int quantity);
    }

    interface Model{
        void getProductDetails(int productId,IDatabaseOps iDatabaseOps);
        void getVendorAndProductStats(int productId,String vendorEmail,IDatabaseOps iDatabaseOps);
        void addToCart(String userEmail,int productId,int quantity,IDatabaseOps iDatabaseOps);
    }

}
