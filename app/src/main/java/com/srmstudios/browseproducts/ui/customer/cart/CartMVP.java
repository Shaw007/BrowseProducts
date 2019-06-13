package com.srmstudios.browseproducts.ui.customer.cart;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.List;

public class CartMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewCartAdapter(CartAdapter adapter);
        void setTxtTotalCartAmount(String totalAmount);
        String getLoggedInUserEmail();
    }

    interface Presenter{
        void getUserCart(String userEmail);
        void proceedToCheckout(String deliveryDate);
    }

    interface Model{
        void getUserCart(String userEmail, IDatabaseListOps iDatabaseListOps);
        void bookUserCartItems(List<Integer> cartIdList, String orderId, String userEmail, String deliveryDate,IDatabaseOps iDatabaseOps);
    }

}
