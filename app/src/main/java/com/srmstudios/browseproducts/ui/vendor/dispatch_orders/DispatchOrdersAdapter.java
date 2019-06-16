package com.srmstudios.browseproducts.ui.vendor.dispatch_orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.VendorOrder;
import com.srmstudios.browseproducts.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DispatchOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VendorOrder> vendorOrders;
    private IDispatchOrdersAdapter iDispatchOrdersAdapter;

    public DispatchOrdersAdapter(List<VendorOrder> vendorOrders,IDispatchOrdersAdapter iDispatchOrdersAdapter) {
        this.vendorOrders = vendorOrders;
        this.iDispatchOrdersAdapter = iDispatchOrdersAdapter;
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
            dispatchOrdersViewHolder.txtOrderId.setText(vendorOrder.getOrderNumber());
            dispatchOrdersViewHolder.txtCustomerName.setText(vendorOrder.getCustomerName());
            dispatchOrdersViewHolder.txtTotalAmount.setText("Rs. " + Utils.getFormattedPrice(vendorOrder.getTotalAmount()));
            dispatchOrdersViewHolder.txtDeliveryDate.setText(vendorOrder.getDeliveryDate());
            if(vendorOrder.isDispatched()){
                dispatchOrdersViewHolder.txtDispatchOrder.setVisibility(View.GONE);
            }else {
                dispatchOrdersViewHolder.txtDispatchOrder.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return vendorOrders.size();
    }

    public void dispatchOrder(String orderNumber){
        for(int i=0;i<vendorOrders.size();i++){
            if(vendorOrders.get(i).getOrderNumber().equals(orderNumber)){
                vendorOrders.get(i).setDispatched(true);
                notifyItemChanged(i);
                break;
            }
        }
    }

    class DispatchOrdersViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtOrderId)
        protected TextView txtOrderId;
        @BindView(R.id.txtCustomerName)
        protected TextView txtCustomerName;
        @BindView(R.id.txtTotalAmount)
        protected TextView txtTotalAmount;
        @BindView(R.id.txtDeliveryDate)
        protected TextView txtDeliveryDate;
        @BindView(R.id.txtDispatchOrder)
        protected TextView txtDispatchOrder;
        @BindView(R.id.txtShowLocation)
        protected TextView txtShowLocation;

        public DispatchOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            txtDispatchOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iDispatchOrdersAdapter.onClickBtnDispatchOrder(vendorOrders.get(getLayoutPosition()).getOrderNumber());
                }
            });
            txtShowLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iDispatchOrdersAdapter.onClickBtnShowLocation(vendorOrders.get(getLayoutPosition()).getLatitude(),
                            vendorOrders.get(getLayoutPosition()).getLongitude());
                }
            });
        }
    }

    public interface IDispatchOrdersAdapter{
        void onClickBtnDispatchOrder(String orderId);
        void onClickBtnShowLocation(double latitude,double longitude);
    }
}














