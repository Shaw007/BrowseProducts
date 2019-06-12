package com.srmstudios.browseproducts.ui.customer.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.CartJoinProduct;
import com.srmstudios.browseproducts.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CartJoinProduct> cartJoinProducts;

    public CartAdapter(List<CartJoinProduct> cartJoinProducts) {
        this.cartJoinProducts = cartJoinProducts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user_cart,parent,false);
        CartViewHolder cartViewHolder = new CartViewHolder(v);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CartJoinProduct cartJoinProduct = cartJoinProducts.get(position);
        if(holder instanceof CartViewHolder){
            CartViewHolder cartViewHolder = (CartViewHolder) holder;
            GlideUtils.loadImage(cartViewHolder.imgProductImage.getContext(),
                    cartViewHolder.imgProductImage,
                    cartJoinProduct.getProductImageUrl());
            cartViewHolder.txtProductName.setText(cartJoinProduct.getProductName());
            cartViewHolder.txtProductQuantity.setText("Quantity " + cartJoinProduct.getProductQuantity());
            cartViewHolder.txtProductPrice.setText("PKR " + Double.parseDouble(cartJoinProduct.getProductPrice())*cartJoinProduct.getProductQuantity());
        }
    }

    @Override
    public int getItemCount() {
        return cartJoinProducts.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgProductImage)
        protected ImageView imgProductImage;
        @BindView(R.id.txtProductName)
        protected TextView txtProductName;
        @BindView(R.id.txtProductQuantity)
        protected TextView txtProductQuantity;
        @BindView(R.id.txtProductPrice)
        protected TextView txtProductPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}















