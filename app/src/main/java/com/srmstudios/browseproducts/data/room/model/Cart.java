package com.srmstudios.browseproducts.data.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "cart_id")
    private int cart_id;
    @ColumnInfo(name = "user_email")
    private String userEmail;
    @ColumnInfo(name = "order_id")
    private String orderId;
    @ColumnInfo(name = "product_id")
    private int productId;
    @ColumnInfo(name = "product_quantity")
    private int productQuantity;
    @ColumnInfo(name = "delivery_date")
    private String deliveryDate;
    @ColumnInfo(name = "is_booked")
    private boolean isBooked = false;
    @ColumnInfo(name = "is_dispatched")
    private boolean isDispatched = false;

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
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

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean booked) {
        isBooked = booked;
    }

    public boolean isDispatched() {
        return isDispatched;
    }

    public void setIsDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }
}
