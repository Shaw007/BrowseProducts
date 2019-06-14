package com.srmstudios.browseproducts.data.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class Product {
    @NonNull
    @PrimaryKey(autoGenerate = true)
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
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
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
}







