package com.srmstudios.browseproducts.data.room.model;

import androidx.room.ColumnInfo;

public class CustomerOrderHistory {
    @ColumnInfo(name = "order_number")
    private String orderNumber;
    @ColumnInfo(name = "vendor_name")
    private String vendorName;
    @ColumnInfo(name = "total_amount")
    private double totalAmount;
    @ColumnInfo(name = "is_dispatched")
    private boolean isDispatched;
    @ColumnInfo(name = "delivery_date")
    private String deliveryDate;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
