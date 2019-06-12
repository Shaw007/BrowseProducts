package com.srmstudios.browseproducts.ui.customer.cart;

import com.srmstudios.browseproducts.data.room.model.CartJoinProduct;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

public class CartPresenter implements CartMVP.Presenter {
    private CartMVP.View view;
    private CartMVP.Model model;
    private CartAdapter adapter;

    public CartPresenter(CartMVP.View view, CartMVP.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getUserCart(String userEmail) {
        model.getUserCart(userEmail, new IDatabaseListOps() {
            @Override
            public void onSuccess(List response) {
                List<CartJoinProduct> cartJoinProducts = response;
                setupCartAdapter(cartJoinProducts);
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message + ": " + throwable.getMessage());
            }
        });
    }

    private void setupCartAdapter(List<CartJoinProduct> cartJoinProducts){
        adapter = new CartAdapter(cartJoinProducts);
        view.setRecyclerViewCartAdapter(adapter);
    }
}
