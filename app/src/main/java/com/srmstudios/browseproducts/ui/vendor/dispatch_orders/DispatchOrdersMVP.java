package com.srmstudios.browseproducts.ui.vendor.dispatch_orders;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public interface DispatchOrdersMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewVendorOrdersAdapter(DispatchOrdersAdapter adapter);
        void showDispatchingCofirmationDialog(String orderId);
    }

    interface Presenter{
        void getVendorOrders(String vendorUserEmail);
        void dispatchOrder(String orderId);
    }

    interface Model{
        void getVendorOrders(String vendorUserEmail, IDatabaseListOps iDatabaseListOps);
        void dispatchOrder(String orderId, IDatabaseOps iDatabaseOps);
    }

}
