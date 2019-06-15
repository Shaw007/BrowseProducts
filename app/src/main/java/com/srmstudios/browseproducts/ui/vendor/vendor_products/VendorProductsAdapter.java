package com.srmstudios.browseproducts.ui.vendor.vendor_products;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VendorProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Product> products;
    private IVendorProductsAdapter iVendorProductsAdapter;

    public VendorProductsAdapter(List<Product> products,IVendorProductsAdapter iVendorProductsAdapter) {
        this.products = products;
        this.iVendorProductsAdapter = iVendorProductsAdapter;
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
            if(product.getProductDiscount() == 0){
                vendorProductsViewHolder.txtProductPriceNew.setText("Rs. " + product.getProductPrice());
                vendorProductsViewHolder.txtProductPriceOld.setVisibility(View.GONE);
                vendorProductsViewHolder.txtDiscountPercent.setVisibility(View.GONE);
            }else {
                double discountedPrice = product.getProductPrice() - (product.getProductPrice()*(product.getProductDiscount()/100f));
                vendorProductsViewHolder.txtProductPriceNew.setText("Rs. " + Math.round(discountedPrice));
                vendorProductsViewHolder.txtProductPriceOld.setText("Rs. " + product.getProductPrice());
                vendorProductsViewHolder.txtProductPriceOld.setPaintFlags(vendorProductsViewHolder.txtProductPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                vendorProductsViewHolder.txtProductPriceOld.setVisibility(View.VISIBLE);
                vendorProductsViewHolder.txtDiscountPercent.setText("-"+product.getProductDiscount()+"%");
                vendorProductsViewHolder.txtDiscountPercent.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProductDiscount(int productId,int newDiscount){
        for(int i=0;i<products.size();i++){
            if(products.get(i).getProductId() == productId){
                products.get(i).setProductDiscount(newDiscount);
                notifyItemChanged(i);
                break;
            }
        }
    }

    class VendorProductsViewHolder extends RecyclerView.ViewHolder{
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
        @BindView(R.id.txtEditDiscount)
        protected TextView txtEditDiscount;

        public VendorProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            txtEditDiscount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iVendorProductsAdapter.onBtnEditDiscountClick(products.get(getLayoutPosition()).getProductId(),
                            products.get(getLayoutPosition()).getProductDiscount());
                }
            });
        }
    }

    public interface IVendorProductsAdapter{
        void onBtnEditDiscountClick(int productId,int currentDiscount);
    }
}















