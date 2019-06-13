package com.srmstudios.browseproducts.ui.customer.order_history;

public class OrderHistoryPresenter implements OrderHistoryMVP.Presenter {
    private OrderHistoryMVP.View view;
    private OrderHistoryMVP.Model model;

    public OrderHistoryPresenter(OrderHistoryMVP.View view, OrderHistoryMVP.Model model) {
        this.view = view;
        this.model = model;
    }
}
