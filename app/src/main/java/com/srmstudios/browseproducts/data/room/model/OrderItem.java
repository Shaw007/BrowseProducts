package com.srmstudios.browseproducts.data.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_items")
public class OrderItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "order_id")
    private int orderId;
    @ColumnInfo(name = "order_number")
    private String orderNumber;
    @ColumnInfo(name = "vendor_email")
    private String vendorEmail;
    @ColumnInfo(name = "user_email")
    private String userEmail;
    @ColumnInfo(name = "product_id")
    private int productId;
    @ColumnInfo(name = "total_price")
    private double totalPrice;
    @ColumnInfo(name = "product_quantity")
    private int productQuantity;
    @ColumnInfo(name = "booking_date")
    private String bookingDate;
    @ColumnInfo(name = "delivery_date")
    private String deliveryDate;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "is_dispatched")
    private boolean isDispatched = false;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
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

    public double getTotalPrice() {
        return Math.round(totalPrice);
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = Math.round(totalPrice);
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isDispatched() {
        return isDispatched;
    }

    public void setDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }
}














