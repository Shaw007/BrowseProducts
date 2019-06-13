package com.srmstudios.browseproducts.ui.vendor.vendor_products;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public interface VendorProductsMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewVendorProductsAdapter(VendorProductsAdapter adapter);
        void showEditDiscountDialog(int productId,int currentDiscount);
    }

    interface Presenter{
        void getVendorProducts(String vendorName);
        void updateProductDiscount(int productId,int newDiscount);
    }

    interface Model{
        void getVendorProducts(String vendorName,
                               IDatabaseListOps iDatabaseListOps);
        void updateProductDiscount(int productId, int newDiscount, IDatabaseOps iDatabaseOps);
    }

}
