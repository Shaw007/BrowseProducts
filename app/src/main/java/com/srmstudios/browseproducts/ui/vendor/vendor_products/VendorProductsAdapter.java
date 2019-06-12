package com.srmstudios.browseproducts.ui.vendor.vendor_products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VendorProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Product> products;

    public VendorProductsAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_vendor_product,parent,false);
        VendorProductsViewHolder vendorProductsViewHolder = new VendorProductsViewHolder(v);
        return vendorProductsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = products.get(position);
        if(holder instanceof VendorProductsViewHolder){
            VendorProductsViewHolder vendorProductsViewHolder = (VendorProductsViewHolder) holder;
            GlideUtils.loadImage(vendorProductsViewHolder.imgProductImage.getContext(),
                    vendorProductsViewHolder.imgProductImage,
                    product.getProductImageUrl());
            vendorProductsViewHolder.txtProductName.setText(product.getProductName());
            vendorProductsViewHolder.txtProductPrice.setText("PKR " + product.getProductPrice());
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class VendorProductsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgProductImage)
        protected ImageView imgProductImage;
        @BindView(R.id.txtProductName)
        protected TextView txtProductName;
        @BindView(R.id.txtProductPrice)
        protected TextView txtProductPrice;

        public VendorProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
