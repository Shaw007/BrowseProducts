package com.srmstudios.browseproducts.ui.customer.cart;

import android.graphics.Paint;
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
    private ICartAdapter iCartAdapter;

    public CartAdapter(List<CartJoinProduct> cartJoinProducts,ICartAdapter iCartAdapter) {
        this.cartJoinProducts = cartJoinProducts;
        this.iCartAdapter = iCartAdapter;
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
            cartViewHolder.txtProductQuantity.setText("Quantity: " + cartJoinProduct.getProductQuantity());
            if(cartJoinProduct.getProductDiscount() == 0){
                cartViewHolder.txtProductPriceNew.setText("Rs. " +
                        (cartJoinProduct.getProductPrice()*cartJoinProduct.getProductQuantity()));
                cartViewHolder.txtProductPriceOld.setVisibility(View.GONE);
                cartViewHolder.txtDiscountPercent.setVisibility(View.GONE);
            }else {
                double discountedPrice = cartJoinProduct.getProductPrice() - (cartJoinProduct.getProductPrice()*(cartJoinProduct.getProductDiscount()/100f));
                cartViewHolder.txtProductPriceNew.setText("Rs. " + (Math.round(discountedPrice)*cartJoinProduct.getProductQuantity()));
                cartViewHolder.txtProductPriceOld.setText("Rs. " + (cartJoinProduct.getProductPrice()*cartJoinProduct.getProductQuantity()));
                cartViewHolder.txtProductPriceOld.setPaintFlags(cartViewHolder.txtProductPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                cartViewHolder.txtProductPriceOld.setVisibility(View.VISIBLE);
                cartViewHolder.txtDiscountPercent.setText("-"+cartJoinProduct.getProductDiscount()+"%");
                cartViewHolder.txtDiscountPercent.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return cartJoinProducts.size();
    }

    public void deleteItem(int cardId){
        for(int i=0;i<cartJoinProducts.size();i++){
            if(cartJoinProducts.get(i).getCartId() == cardId){
                cartJoinProducts.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public List<CartJoinProduct> getCartJoinProducts(){
        return cartJoinProducts;
    }

    public void clear(){
        cartJoinProducts.clear();
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgProductImage)
        protected ImageView imgProductImage;
        @BindView(R.id.txtProductName)
        protected TextView txtProductName;
        @BindView(R.id.txtProductPriceNew)
        protected TextView txtProductPriceNew;
        @BindView(R.id.txtProductPriceOld)
        protected TextView txtProductPriceOld;
        @BindView(R.id.txtDiscountPercent)
        protected TextView txtDiscountPercent;
        @BindView(R.id.txtProductQuantity)
        protected TextView txtProductQuantity;
        @BindView(R.id.imgDelete)
        protected ImageView imgDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iCartAdapter.onBtnDeleteItem(cartJoinProducts.get(getLayoutPosition()).getCartId());
                }
            });
        }
    }

    public interface ICartAdapter{
        void onBtnDeleteItem(int cartId);
    }
}















