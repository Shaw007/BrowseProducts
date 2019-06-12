package com.srmstudios.browseproducts.ui.vendor.view_products;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public interface ViewProductsMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewProductsAdapter(VendorProductsAdapter adapter);
    }

    interface Presenter{
        void getVendorProducts(String vendorName);
    }

    interface Model{
        void getVendorProducts(String vendorName,
                               IDatabaseListOps iDatabaseListOps);
    }

}
