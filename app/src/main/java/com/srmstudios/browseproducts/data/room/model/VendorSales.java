package com.srmstudios.browseproducts.data.room.model;


import androidx.room.ColumnInfo;

public class VendorSales {
    @ColumnInfo(name = "order_number")
    private String orderNumber;
    @ColumnInfo(name = "total_order_amount")
    private double totalOrderAmount;
    @ColumnInfo(name = "total_products_quantity")
    private int productsQuantity;
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

    public double getTotalOrderAmount() {
        return Math.round(totalOrderAmount);
    }

    public void setTotalOrderAmount(double totalOrderAmount) {
        this.totalOrderAmount = Math.round(totalOrderAmount);
    }

    public int getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(int productsQuantity) {
        this.productsQuantity = productsQuantity;
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
