package com.srmstudios.browseproducts.ui.customer.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
            cartViewHolder.txtProductQuantity.setText("Quantity " + cartJoinProduct.getProductQuantity());
            if(cartJoinProduct.getProductDiscount() == 0){
                cartViewHolder.txtProductPrice.setText("PKR " + cartJoinProduct.getProductPrice());
                cartViewHolder.txtProductDiscountedPrice.setVisibility(View.GONE);
            }else {
                double discountedPrice = cartJoinProduct.getProductPrice() - (cartJoinProduct.getProductPrice()*(cartJoinProduct.getProductDiscount()/100f));
                cartViewHolder.txtProductPrice.setText("Actual Price: PKR " + cartJoinProduct.getProductPrice());
                cartViewHolder.txtProductDiscountedPrice.setText("Discounted Price: PKR " + Math.round(discountedPrice));
                cartViewHolder.txtProductDiscountedPrice.setVisibility(View.VISIBLE);
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
        @BindView(R.id.txtProductQuantity)
        protected TextView txtProductQuantity;
        @BindView(R.id.txtProductPrice)
        protected TextView txtProductPrice;
        @BindView(R.id.txtProductDiscountedPrice)
        protected TextView txtProductDiscountedPrice;
        @BindView(R.id.btnDeleteItem)
        protected Button btnDeleteItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            btnDeleteItem.setOnClickListener(new View.OnClickListener() {
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















