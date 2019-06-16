package com.srmstudios.browseproducts.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.srmstudios.browseproducts.data.room.model.CustomerOrderHistory;
import com.srmstudios.browseproducts.data.room.model.OrderItem;
import com.srmstudios.browseproducts.data.room.model.VendorOrder;
import com.srmstudios.browseproducts.data.room.model.VendorSales;

import java.util.List;

@Dao
public interface OrderItemDao {

    @Insert
    void insertOrderItems(List<OrderItem> orderItems);

    @Query("select oi.order_number,u.name as vendor_name,sum(oi.total_price) as total_amount,oi.is_dispatched,oi.delivery_date " +
            "from order_items oi,user u " +
            "where oi.vendor_email = u.email " +
            "and oi.user_email=:userEmail " +
            "group by oi.order_number")
    List<CustomerOrderHistory> getCustomerOrderHistory(String userEmail);

    @Query("select oi.order_number,sum(oi.total_price) as total_amount,oi.is_dispatched,u.name as customer_name,oi.delivery_date,oi.latitude,oi.longitude " +
            "from order_items oi,user u " +
            "where oi.user_email = u.email " +
            "and oi.vendor_email=:vendorUserEmail " +
            "group by order_number")
    List<VendorOrder> getVendorOrders(String vendorUserEmail);

    @Query("select oi.order_number,u.name as customer_name,sum(oi.total_price) as total_order_amount,sum(oi.product_quantity) as total_products_quantity,oi.is_dispatched,oi.delivery_date FROM " +
            "order_items oi,user u " +
            "where oi.user_email = u.email " +
            "and oi.booking_date=:bookingDate and oi.vendor_email=:vendorEmail " +
            "group by oi.order_number")
    List<VendorSales> getVendorSalesByBookingDate(String bookingDate,String vendorEmail);

    @Query("update order_items set is_dispatched=1 " +
            "where order_number=:orderNumber")
    void dispatchOrder(String orderNumber);

    @Query("select count(DISTINCT(order_number)) from order_items where vendor_email=:vendorEmail")
    int getVendorOrdersBookedCount(String vendorEmail);

    @Query("select sum(product_quantity) from order_items where product_id=:productId")
    int getProductBookedCount(int productId);
}
