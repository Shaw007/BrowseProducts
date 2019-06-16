package com.srmstudios.browseproducts.ui.customer.cart;

import com.srmstudios.browseproducts.data.room.model.OrderItem;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.List;

public class CartMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewCartAdapter(CartAdapter adapter);
        void setTxtTotalCartAmount(String totalAmount);
        void showTxtNoDataFound();
        void showDeleteItemConfirmationDialog(int cartId);
    }

    interface Presenter{
        void getUserCart(String userEmail);
        void proceedToCheckout(String deliveryDate,double latitude,double longitude);
        void deleteCartItem(int cartId);
    }

    interface Model{
        void getUserCart(String userEmail, IDatabaseListOps iDatabaseListOps);
        void placeOrder(List<OrderItem> orderItems,String userEmail,IDatabaseOps iDatabaseOps);
        void deleteCartItem(int cartId,IDatabaseOps iDatabaseOps);
    }

}
