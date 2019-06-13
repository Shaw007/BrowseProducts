package com.srmstudios.browseproducts.ui.customer.cart;

import com.srmstudios.browseproducts.data.room.model.CartJoinProduct;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.ArrayList;
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
                view.showDialogMessage(message);
            }
        });
    }

    @Override
    public void proceedToCheckout() {
        List<Integer> cartIdsList = new ArrayList<>();
        for(CartJoinProduct cartJoinProduct : adapter.getCartJoinProducts()){
            cartIdsList.add(cartJoinProduct.getCartId());
        }
        model.bookUserCartItems(cartIdsList,
                Utils.generateUniqueOrderId(),
                view.getLoggedInUserEmail(),
                new IDatabaseOps() {
                    @Override
                    public void onSuccess(Object response) {
                        view.showDialogMessage(response.toString());
                        if(response.toString().contains("successfully")){
                            adapter.clear();
                            view.setTxtTotalCartAmount("0");
                        }
                    }

                    @Override
                    public void onError(String message, Throwable throwable) {
                        view.showDialogMessage(message);
                    }
                });
    }

    private void setupCartAdapter(List<CartJoinProduct> cartJoinProducts){
        adapter = new CartAdapter(cartJoinProducts);
        view.setRecyclerViewCartAdapter(adapter);

        double totalCartAmount = 0;
        for(CartJoinProduct cartJoinProduct : cartJoinProducts){
            totalCartAmount += Double.parseDouble(cartJoinProduct.getProductPrice())*cartJoinProduct.getProductQuantity();
        }
        view.setTxtTotalCartAmount(String.valueOf(totalCartAmount));
    }
}
