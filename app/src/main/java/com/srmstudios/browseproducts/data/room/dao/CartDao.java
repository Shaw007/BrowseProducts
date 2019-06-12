package com.srmstudios.browseproducts.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.srmstudios.browseproducts.data.room.model.Cart;
import com.srmstudios.browseproducts.data.room.model.CartJoinProduct;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insert(Cart cart);

    @Query("select * from cart where user_email=:userEmail and product_id=:productId")
    Cart getCartByUserAndProductId(String userEmail,int productId);

    @Query("select c.cart_id,c.user_email,c.product_quantity,c.product_id,p.product_name,p.product_image_url,p.product_details,p.product_price,p.product_vendor " +
            "from cart c,product p " +
            "where c.product_id=p.product_id and user_email=:userEmail")
    List<CartJoinProduct> getUserCart(String userEmail);

}
