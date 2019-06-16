package com.srmstudios.browseproducts.ui.customer.view_products;

import android.graphics.Paint;
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
import com.srmstudios.browseproducts.util.Utils;

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
            GlideUtils.loadImageListItems(viewProductsViewHolder.imgProductImage.getContext(),
                    viewProductsViewHolder.imgProductImage,
                    product.getProductImageUrl());
            viewProductsViewHolder.txtProductName.setText(product.getProductName());
            if(product.getProductDiscount() == 0){
                viewProductsViewHolder.txtProductPriceNew.setText("Rs. " + Utils.getFormattedPrice(product.getProductPrice()));
                viewProductsViewHolder.txtProductPriceOld.setVisibility(View.GONE);
                viewProductsViewHolder.txtDiscountPercent.setVisibility(View.GONE);
            }else {
                double discountedPrice = product.getProductPrice() - (product.getProductPrice()*(product.getProductDiscount()/100f));
                viewProductsViewHolder.txtProductPriceNew.setText("Rs. " + Utils.getFormattedPrice(Math.round(discountedPrice)));
                viewProductsViewHolder.txtProductPriceOld.setText("Rs. " + Utils.getFormattedPrice(product.getProductPrice()));
                viewProductsViewHolder.txtProductPriceOld.setPaintFlags(viewProductsViewHolder.txtProductPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                viewProductsViewHolder.txtProductPriceOld.setVisibility(View.VISIBLE);
                viewProductsViewHolder.txtDiscountPercent.setText("-"+product.getProductDiscount()+"%");
                viewProductsViewHolder.txtDiscountPercent.setVisibility(View.VISIBLE);
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
        @BindView(R.id.txtProductPriceNew)
        protected TextView txtProductPriceNew;
        @BindView(R.id.txtProductPriceOld)
        protected TextView txtProductPriceOld;
        @BindView(R.id.txtDiscountPercent)
        protected TextView txtDiscountPercent;

        public ViewProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iViewProductsAdapter.onItemClick(products.get(getLayoutPosition()).getProductId(),
                            products.get(getLayoutPosition()).getProductVendorEmail());
                }
            });
        }
    }

    public interface IViewProductsAdapter{
        void onItemClick(int productId,String vendorEmail);
    }
}












