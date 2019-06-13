package com.srmstudios.browseproducts.ui.customer.order_history;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

public interface OrderHistoryMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewOrderHistoryAdapter(OrderHistoryAdapter adapter);
    }

    interface Presenter{
        void getCustomerOrderHistory(String email);
    }

    interface Model{
        void getCustomerOrderHistory(String email, IDatabaseListOps iDatabaseListOps);
    }

}
