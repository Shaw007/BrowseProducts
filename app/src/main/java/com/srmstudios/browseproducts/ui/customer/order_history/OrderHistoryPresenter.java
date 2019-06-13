package com.srmstudios.browseproducts.ui.customer.order_history;

import com.srmstudios.browseproducts.data.room.model.CustomerOrderHistory;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

public class OrderHistoryPresenter implements OrderHistoryMVP.Presenter {
    private OrderHistoryMVP.View view;
    private OrderHistoryMVP.Model model;
    private OrderHistoryAdapter adapter;

    public OrderHistoryPresenter(OrderHistoryMVP.View view, OrderHistoryMVP.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getCustomerOrderHistory(String email) {
        model.getCustomerOrderHistory(email, new IDatabaseListOps() {
            @Override
            public void onSuccess(List response) {
                List<CustomerOrderHistory> orders = response;
                setupOrderHistoryAdapter(orders);
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }

    private void setupOrderHistoryAdapter(List<CustomerOrderHistory> orders){
        adapter = new OrderHistoryAdapter(orders);
        view.setRecyclerViewOrderHistoryAdapter(adapter);
    }
}
