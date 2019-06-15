package com.srmstudios.browseproducts.data.room.model;

import androidx.room.ColumnInfo;

public class CartJoinProduct {
    @ColumnInfo(name = "cart_id")
    private int cartId;
    @ColumnInfo(name = "user_email")
    private String userEmail;
    @ColumnInfo(name = "product_id")
    private int productId;
    @ColumnInfo(name = "product_name")
    private String productName;
    @ColumnInfo(name = "product_image_url")
    private String productImageUrl;
    @ColumnInfo(name = "product_details")
    private String productDetails;
    @ColumnInfo(name = "product_price")
    private double productPrice;
    @ColumnInfo(name = "product_vendor")
    private String productVendor;
    @ColumnInfo(name = "product_vendor_email")
    private String productVendorEmail;
    @ColumnInfo(name = "product_discount")
    private int productDiscount;
    @ColumnInfo(name = "product_quantity")
    private int productQuantity;

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

    public double getProductPrice() {
        return Math.round(productPrice);
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = Math.round(productPrice);
    }

    public String getProductVendor() {
        return productVendor;
    }

    public void setProductVendor(String productVendor) {
        this.productVendor = productVendor;
    }

    public String getProductVendorEmail() {
        return productVendorEmail;
    }

    public void setProductVendorEmail(String productVendorEmail) {
        this.productVendorEmail = productVendorEmail;
    }

    public int getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(int productDiscount) {
        this.productDiscount = productDiscount;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
