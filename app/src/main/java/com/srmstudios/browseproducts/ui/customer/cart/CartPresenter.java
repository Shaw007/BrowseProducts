package com.srmstudios.browseproducts.ui.customer.cart;

import com.srmstudios.browseproducts.data.room.model.CartJoinProduct;
import com.srmstudios.browseproducts.data.room.model.OrderItem;
import com.srmstudios.browseproducts.util.AppConstants;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartPresenter implements CartMVP.Presenter, CartAdapter.ICartAdapter {
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
    public void proceedToCheckout(String deliveryDate,double latitude,double longitude) {
        if(adapter == null){
            return;
        }
        if(adapter.getCartJoinProducts() == null){
            return;
        }
        if(adapter.getCartJoinProducts().size() == 0){
            return;
        }
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartJoinProduct cartJoinProduct : adapter.getCartJoinProducts()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderNumber(Utils.generateUniqueOrderId());
            orderItem.setVendorEmail(cartJoinProduct.getProductVendorEmail());
            orderItem.setUserEmail(cartJoinProduct.getUserEmail());
            orderItem.setProductId(cartJoinProduct.getProductId());
            // discounted price multiply by product quantity
            double totalPrice = cartJoinProduct.getProductQuantity() * (cartJoinProduct.getProductPrice() - (cartJoinProduct.getProductPrice()*(cartJoinProduct.getProductDiscount()/100f)));
            orderItem.setTotalPrice(totalPrice);
            orderItem.setProductQuantity(cartJoinProduct.getProductQuantity());
            orderItem.setBookingDate(Utils.getFormattedDateString(AppConstants.DATE_FORMAT_TWO,new Date()));
            orderItem.setDeliveryDate(deliveryDate);
            orderItem.setLatitude(latitude);
            orderItem.setLongitude(longitude);
            orderItem.setDispatched(false);
            orderItems.add(orderItem);
        }
        model.placeOrder(orderItems, adapter.getCartJoinProducts().get(0).getUserEmail(),new IDatabaseOps() {
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

    @Override
    public void deleteCartItem(int cartId) {
        model.deleteCartItem(cartId, new IDatabaseOps() {
            @Override
            public void onSuccess(Object response) {
                view.showDialogMessage(response.toString());
                if(response.toString().contains("successfully")){
                    adapter.deleteItem(cartId);
                    calculateTotalCartAmount(adapter.getCartJoinProducts());
                }
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }

    private void setupCartAdapter(List<CartJoinProduct> cartJoinProducts){
        adapter = new CartAdapter(cartJoinProducts,this);
        view.setRecyclerViewCartAdapter(adapter);
        calculateTotalCartAmount(cartJoinProducts);
    }

    private void calculateTotalCartAmount(List<CartJoinProduct> cartJoinProducts){
        double totalCartAmount = 0;
        for(CartJoinProduct cartJoinProduct : cartJoinProducts){
            if(cartJoinProduct.getProductDiscount() == 0) {
                totalCartAmount += cartJoinProduct.getProductPrice() * cartJoinProduct.getProductQuantity();
            }else {
                double discountedPrice = cartJoinProduct.getProductPrice() -
                        cartJoinProduct.getProductPrice()*(cartJoinProduct.getProductDiscount()/100f);
                totalCartAmount += Math.round(discountedPrice) * cartJoinProduct.getProductQuantity();
            }
        }
        view.setTxtTotalCartAmount(String.valueOf(totalCartAmount));
    }

    @Override
    public void onBtnDeleteItem(int cartId) {
        view.showDeleteItemConfirmationDialog(cartId);
    }
}
















