package com.srmstudios.browseproducts.ui.vendor.view_products;

import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.Product;

import java.io.File;
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
            Uri photoUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoUri = FileProvider.getUriForFile(vendorProductsViewHolder.imgProductImage.getContext(),
                        vendorProductsViewHolder.imgProductImage.getContext().getApplicationContext().getPackageName() + ".provider",
                        new File(product.getProductImageUrl()));
            } else {
                photoUri = Uri.fromFile(new File(product.getProductImageUrl()));
            }
            Glide.with(vendorProductsViewHolder.imgProductImage.getContext())
                    .load(Uri.fromFile(new File(product.getProductImageUrl())))
                    .into(vendorProductsViewHolder.imgProductImage);
            //vendorProductsViewHolder.imgProductImage.setImageURI(photoUri);
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
