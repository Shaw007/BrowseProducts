package com.srmstudios.browseproducts.ui.vendor.sales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.VendorSales;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VendorSales> vendorSales;

    public SalesAdapter(List<VendorSales> vendorSales) {
        this.vendorSales = vendorSales;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sales,parent,false);
        SalesViewHolder salesViewHolder = new SalesViewHolder(v);
        return salesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VendorSales vendorSale = vendorSales.get(position);
        if(holder instanceof SalesViewHolder){
            SalesViewHolder salesViewHolder = (SalesViewHolder) holder;
            salesViewHolder.txtOrderNumber.setText(vendorSale.getOrderNumber());
            salesViewHolder.txtTotalOrderAmount.setText("PKR " + vendorSale.getTotalOrderAmount());
            salesViewHolder.txtProductsQuantity.setText("Products Quantity: " + vendorSale.getProductsQuantity());
            if(vendorSale.isDispatched()) {
                salesViewHolder.txtIsDispatched.setText("Dispatched: Yes");
            }else {
                salesViewHolder.txtIsDispatched.setText("Dispatched: No");
            }
            salesViewHolder.txtDeliveryDate.setText(vendorSale.getDeliveryDate());
        }
    }

    @Override
    public int getItemCount() {
        return vendorSales.size();
    }

    class SalesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtOrderNumber)
        protected TextView txtOrderNumber;
        @BindView(R.id.txtTotalOrderAmount)
        protected TextView txtTotalOrderAmount;
        @BindView(R.id.txtProductsQuantity)
        protected TextView txtProductsQuantity;
        @BindView(R.id.txtIsDispatched)
        protected TextView txtIsDispatched;
        @BindView(R.id.txtDeliveryDate)
        protected TextView txtDeliveryDate;

        public SalesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}















