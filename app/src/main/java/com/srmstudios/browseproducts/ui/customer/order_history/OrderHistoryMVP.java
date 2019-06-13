package com.srmstudios.browseproducts.ui.customer.order_history;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

public interface OrderHistoryMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
    }

    interface Presenter{

    }

    interface Model{
        void getCustomerOrderHistory(String email, IDatabaseListOps iDatabaseListOps);
    }

}
