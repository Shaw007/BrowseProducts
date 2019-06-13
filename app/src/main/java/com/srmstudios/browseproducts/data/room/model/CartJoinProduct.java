package com.srmstudios.browseproducts.data.room.model;

import androidx.room.ColumnInfo;

public class CartJoinProduct {
    @ColumnInfo(name = "cart_id")
    private int cartId;
    @ColumnInfo(name = "user_email")
    private String userEmail;
    @ColumnInfo(name = "product_quantity")
    private int productQuantity;
    @ColumnInfo(name = "order_id")
    private String orderId;
    @ColumnInfo(name = "product_id")
    private int productId;
    @ColumnInfo(name = "product_name")
    private String productName;
    @ColumnInfo(name = "product_image_url")
    private String productImageUrl;
    @ColumnInfo(name = "product_details")
    private String productDetails;
    @ColumnInfo(name = "product_price")
    private String productPrice;
    @ColumnInfo(name = "product_vendor")
    private String productVendor;
    @ColumnInfo(name = "is_booked")
    private boolean isBooked;
    @ColumnInfo(name = "is_dispatched")
    private boolean isDispatched = false;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public void setProductVendor(String productVendor) {
        this.productVendor = productVendor;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public boolean isDispatched() {
        return isDispatched;
    }

    public void setDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }
}
