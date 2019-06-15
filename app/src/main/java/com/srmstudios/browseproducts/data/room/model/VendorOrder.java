package com.srmstudios.browseproducts.data.room.model;

import androidx.room.ColumnInfo;

public class VendorOrder {
    @ColumnInfo(name = "order_number")
    private String orderNumber;
    @ColumnInfo(name = "total_amount")
    private double totalAmount;
    @ColumnInfo(name = "is_dispatched")
    private boolean isDispatched;
    @ColumnInfo(name = "customer_name")
    private String customerName;
    @ColumnInfo(name = "delivery_date")
    private String deliveryDate;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getTotalAmount() {
        return Math.round(totalAmount);
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = Math.round(totalAmount);
    }

    public boolean isDispatched() {
        return isDispatched;
    }

    public void setDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
}
