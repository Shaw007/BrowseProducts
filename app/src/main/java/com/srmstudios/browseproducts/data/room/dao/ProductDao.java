package com.srmstudios.browseproducts.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.data.room.model.User;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Query("select * from product where product_id=:productId")
    Product getProductById(int productId);

    @Query("select * from product where product_name=:productName")
    Product getProductByName(String productName);

    @Query("select * from product where product_vendor=:productVendor")
    List<Product> getVendorProducts(String productVendor);

    @Query("select * from product")
    List<Product> getAllProducts();
}
