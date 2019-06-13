package com.srmstudios.browseproducts.ui.vendor.dispatch_orders;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

public interface DispatchOrdersMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewVendorOrdersAdapter(DispatchOrdersAdapter adapter);
    }

    interface Presenter{
        void getVendorOrders(String vendorUserEmail);
    }

    interface Model{
        void getVendorOrders(String vendorUserEmail, IDatabaseListOps iDatabaseListOps);
    }

}
