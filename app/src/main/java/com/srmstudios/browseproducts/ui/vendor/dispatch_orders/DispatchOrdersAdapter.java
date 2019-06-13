package com.srmstudios.browseproducts.ui.vendor.dispatch_orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.VendorOrder;

import java.util.List;

public class DispatchOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VendorOrder> vendorOrders;

    public DispatchOrdersAdapter(List<VendorOrder> vendorOrders) {
        this.vendorOrders = vendorOrders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dispatch_order,parent,false);
        DispatchOrdersViewHolder dispatchOrdersViewHolder = new DispatchOrdersViewHolder(v);
        return dispatchOrdersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VendorOrder vendorOrder = vendorOrders.get(position);
        if (holder instanceof DispatchOrdersViewHolder) {
            DispatchOrdersViewHolder dispatchOrdersViewHolder = (DispatchOrdersViewHolder) holder;

        }
    }

    @Override
    public int getItemCount() {
        return vendorOrders.size();
    }

    class DispatchOrdersViewHolder extends RecyclerView.ViewHolder{

        public DispatchOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}














