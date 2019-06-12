package com.srmstudios.browseproducts.ui.vendor.vendor_products;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

public interface VendorProductsMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewVendorProductsAdapter(VendorProductsAdapter adapter);
    }

    interface Presenter{
        void getVendorProducts(String vendorName);
    }

    interface Model{
        void getVendorProducts(String vendorName,
                               IDatabaseListOps iDatabaseListOps);
    }

}
