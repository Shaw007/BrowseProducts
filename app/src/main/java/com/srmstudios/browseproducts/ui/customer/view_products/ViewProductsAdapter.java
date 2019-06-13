package com.srmstudios.browseproducts.ui.customer.view_products;

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

public class ViewProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Product> products;
    private IViewProductsAdapter iViewProductsAdapter;

    public ViewProductsAdapter(List<Product> products,IViewProductsAdapter iViewProductsAdapter) {
        this.products = products;
        this.iViewProductsAdapter = iViewProductsAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view_products,parent,false);
        ViewProductsViewHolder viewProductsViewHolder = new ViewProductsViewHolder(v);
        return viewProductsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = products.get(position);
        if(holder instanceof ViewProductsViewHolder){
            ViewProductsViewHolder viewProductsViewHolder = (ViewProductsViewHolder) holder;
            GlideUtils.loadImage(viewProductsViewHolder.imgProductImage.getContext(),
                    viewProductsViewHolder.imgProductImage,
                    product.getProductImageUrl());
            viewProductsViewHolder.txtProductName.setText(product.getProductName());
            if(product.getProductDiscount() == 0){
                viewProductsViewHolder.txtProductPrice.setText("PKR " + product.getProductPrice());
                viewProductsViewHolder.txtProductDiscountedPrice.setVisibility(View.GONE);
            }else {
                double actualPrice = Double.parseDouble(product.getProductPrice());
                double discountedPrice = actualPrice - (actualPrice*(product.getProductDiscount()/100f));
                viewProductsViewHolder.txtProductPrice.setText("Actual Price: PKR " + product.getProductPrice());
                viewProductsViewHolder.txtProductDiscountedPrice.setText("Discounted Price: PKR " + Math.round(discountedPrice));
                viewProductsViewHolder.txtProductDiscountedPrice.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewProductsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgProductImage)
        protected ImageView imgProductImage;
        @BindView(R.id.txtProductName)
        protected TextView txtProductName;
        @BindView(R.id.txtProductPrice)
        protected TextView txtProductPrice;
        @BindView(R.id.txtProductDiscountedPrice)
        protected TextView txtProductDiscountedPrice;

        public ViewProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iViewProductsAdapter.onItemClick(products.get(getLayoutPosition()).getProductId());
                }
            });
        }
    }

    public interface IViewProductsAdapter{
        void onItemClick(int productId);
    }
}












