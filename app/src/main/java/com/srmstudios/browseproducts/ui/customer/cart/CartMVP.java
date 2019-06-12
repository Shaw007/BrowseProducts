package com.srmstudios.browseproducts.ui.customer.cart;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

public class CartMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewCartAdapter(CartAdapter adapter);
    }

    interface Presenter{
        void getUserCart(String userEmail);
    }

    interface Model{
        void getUserCart(String userEmail, IDatabaseListOps iDatabaseListOps);
    }

}
