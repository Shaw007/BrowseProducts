package com.srmstudios.browseproducts.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.srmstudios.browseproducts.data.room.model.Cart;
import com.srmstudios.browseproducts.data.room.model.CartJoinProduct;
import com.srmstudios.browseproducts.data.room.model.CustomerOrderHistory;
import com.srmstudios.browseproducts.data.room.model.VendorOrder;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insert(Cart cart);

    @Query("select * from cart where user_email=:userEmail and product_id=:productId and order_id is null")
    Cart getCartByUserAndProductId(String userEmail,int productId);

    @Query("select c.cart_id,c.user_email,c.order_id,c.product_quantity,c.product_id,p.product_name,p.product_image_url,p.product_details,p.product_price,p.product_vendor,p.product_discount,c.is_booked,c.is_dispatched " +
            "from cart c,product p " +
            "where c.product_id=p.product_id and user_email=:userEmail and is_booked=0")
    List<CartJoinProduct> getUserCart(String userEmail);

    @Query("update cart set order_id=:orderId,is_booked=1,delivery_date=:deliveryDate " +
            "where cart_id in (:cartIdList) " +
            "and user_email=:userEmail and is_booked=0")
    void bookUserCart(List<Integer> cartIdList,String orderId,String userEmail,String deliveryDate);

    @Query("select c.order_id,sum(p.product_price-(p.product_price*(p.product_discount/100.0))) as total_amount,c.is_dispatched,c.delivery_date " +
            "from cart c,product p where c.product_id = p.product_id " +
            "and is_booked = 1  and c.user_email=:userEmail " +
            "group by c.order_id")
    List<CustomerOrderHistory> getCustomerOrderHistory(String userEmail);

    @Query("select c.order_id,sum(p.product_price-(p.product_price*(p.product_discount/100.0))) as total_amount,c.is_dispatched,u.name as customer_name,c.delivery_date " +
            "from cart c,product p,user u where c.product_id = p.product_id and c.user_email = u.email " +
            "and is_booked = 1 and p.product_vendor_email=:vendorUserEmail " +
            "group by c.order_id")
    List<VendorOrder> getVendorOrders(String vendorUserEmail);

    @Query("update cart set is_dispatched=1 " +
            "where order_id=:orderId")
    void dispatchOrder(String orderId);
}








