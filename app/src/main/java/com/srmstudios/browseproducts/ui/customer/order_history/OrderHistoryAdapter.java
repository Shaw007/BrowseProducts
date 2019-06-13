package com.srmstudios.browseproducts.ui.customer.order_history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.CustomerOrderHistory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CustomerOrderHistory> orders;

    public OrderHistoryAdapter(List<CustomerOrderHistory> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order_history,parent,false);
        OrderHistoryViewHolder orderHistoryViewHolder = new OrderHistoryViewHolder(v);
        return orderHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CustomerOrderHistory order = orders.get(position);
        if(holder instanceof OrderHistoryViewHolder) {
            OrderHistoryViewHolder orderHistoryViewHolder = (OrderHistoryViewHolder) holder;
            orderHistoryViewHolder.txtOrderId.setText(order.getOrderId());
            orderHistoryViewHolder.txtTotalAmount.setText("PKR " + order.getTotalAmount());
            orderHistoryViewHolder.txtDeliveryDate.setText("Delivery Date: " + order.getDeliveryDate());
            if(order.isDispatched()) {
                orderHistoryViewHolder.txtIsDispatched.setText("Dispatched: Yes");
            }else {
                orderHistoryViewHolder.txtIsDispatched.setText("Dispatched: No");
            }
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderHistoryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtOrderId)
        protected TextView txtOrderId;
        @BindView(R.id.txtTotalAmount)
        protected TextView txtTotalAmount;
        @BindView(R.id.txtDeliveryDate)
        protected TextView txtDeliveryDate;
        @BindView(R.id.txtIsDispatched)
        protected TextView txtIsDispatched;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}













