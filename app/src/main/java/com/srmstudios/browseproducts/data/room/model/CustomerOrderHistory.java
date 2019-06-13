package com.srmstudios.browseproducts.data.room.model;

import androidx.room.ColumnInfo;

public class CustomerOrderHistory {
    @ColumnInfo(name = "order_id")
    private String orderId;
    @ColumnInfo(name = "total_amount")
    private double totalAmount;
    @ColumnInfo(name = "is_dispatched")
    private boolean isDispatched;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isDispatched() {
        return isDispatched;
    }

    public void setDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }
}
