package com.srmstudios.browseproducts.ui.product_detail;


import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.DialogUtils;
import com.srmstudios.browseproducts.util.GlideUtils;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;
import com.srmstudios.browseproducts.util.singleton.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment implements ProductDetailMVP.View,View.OnClickListener{
    @BindView(R.id.imgProductImage)
    protected ImageView imgProductImage;
    @BindView(R.id.txtProductName)
    protected TextView txtProductName;
    @BindView(R.id.txtProductDesc)
    protected TextView txtProductDesc;
    @BindView(R.id.txtProductPriceNew)
    protected TextView txtProductPriceNew;
    @BindView(R.id.txtProductPriceOld)
    protected TextView txtProductPriceOld;
    @BindView(R.id.txtDiscountPercent)
    protected TextView txtDiscountPercent;
    @BindView(R.id.txtVendorName)
    protected TextView txtVendorName;
    @BindView(R.id.txtVendorOrdersBookedCount)
    protected TextView txtVendorOrdersBookedCount;
    @BindView(R.id.txtProductBookedCount)
    protected TextView txtProductBookedCount;
    @BindView(R.id.imgMinus)
    protected ImageView imgMinus;
    @BindView(R.id.txtQuantity)
    protected TextView txtQuantity;
    @BindView(R.id.imgPlus)
    protected ImageView imgPlus;
    @BindView(R.id.txtAddToCart)
    protected TextView txtAddToCart;

    private Unbinder unbinder;
    private ProductDetailPresenter presenter;
    private int productId;
    private String vendorEmail;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ProductDetailPresenter(this,
                new ProductDetailModel(BrowseProductsDatabase.getInstance(getContext()).getAppDatabase()));
        productId = getActivity().getIntent().getIntExtra(ProductDetailActivity.KEY_PRODUCT_ID,0);
        vendorEmail = getActivity().getIntent().getStringExtra(ProductDetailActivity.KEY_VENDOR_EMAIL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_detail, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        presenter.getProductDetails(productId);

        imgMinus.setOnClickListener(this);
        imgPlus.setOnClickListener(this);
        txtAddToCart.setOnClickListener(this);
    }

    @Override
    public void setupProductDetails(Product product) {
        GlideUtils.loadImage(getContext(),
                imgProductImage,
                product.getProductImageUrl());
        txtProductName.setText(product.getProductName());
        txtProductDesc.setText(product.getProductDetails());
        txtVendorName.setText("(By "+product.getProductVendor()+")");

        if(product.getProductDiscount() == 0){
            txtProductPriceNew.setText("Rs. " + product.getProductPrice());
            txtProductPriceOld.setVisibility(View.GONE);
            txtDiscountPercent.setVisibility(View.GONE);
        }else {
            double discountedPrice = product.getProductPrice() - (product.getProductPrice()*(product.getProductDiscount()/100f));
            txtProductPriceNew.setText("Rs. " + Math.round(discountedPrice));
            txtProductPriceOld.setText("Rs. " + product.getProductPrice());
            txtProductPriceOld.setPaintFlags(txtProductPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtProductPriceOld.setVisibility(View.VISIBLE);
            txtDiscountPercent.setText("-"+product.getProductDiscount()+"%");
            txtDiscountPercent.setVisibility(View.VISIBLE);
        }
        presenter.getVendorAndProductStats(productId,vendorEmail);
    }

    @Override
    public void setupVendorAndProductStats(List<Integer> vendorAndProductStats) {
        txtVendorOrdersBookedCount.setText("Vendor Orders Booked: " + vendorAndProductStats.get(0));
        txtProductBookedCount.setText("Product Booked Times: " + vendorAndProductStats.get(1));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgMinus:{
                updateQuantity(true);
                break;
            }
            case R.id.imgPlus:{
                updateQuantity(false);
                break;
            }
            case R.id.txtAddToCart:{
                presenter.addToCart(SessionManager.getInstance(getContext()).getUser().getEmail(),
                        productId,
                        Integer.parseInt(txtQuantity.getText().toString()));
                break;
            }
        }
    }

    private void updateQuantity(boolean isMinus){
        int quantity = Integer.parseInt(txtQuantity.getText().toString());
        if(isMinus){
            if(quantity > 1){
                quantity--;
            }
        }else {
            quantity++;
            if(quantity > 20){
                showDialogMessage(R.string.max_20_allowed);
                return;
            }
        }
        txtQuantity.setText(String.valueOf(quantity));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showDialogMessage(int resourceId) {
        DialogUtils.showSingleButtonDialog(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                Utils.getStringFromResourceId(getContext(),resourceId));
    }

    @Override
    public void showDialogMessage(String message) {
        DialogUtils.showSingleButtonDialog(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                message);
    }
}
