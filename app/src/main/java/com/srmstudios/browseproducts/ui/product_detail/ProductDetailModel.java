package com.srmstudios.browseproducts.ui.product_detail;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.data.room.model.Cart;
import com.srmstudios.browseproducts.data.room.model.CartJoinProduct;
import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailModel implements ProductDetailMVP.Model {
    private AppDatabase appDatabase;

    public ProductDetailModel(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void getProductDetails(int productId,IDatabaseOps iDatabaseOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, Product>() {
                    @Override
                    public Product apply(AppDatabase appDatabase) throws Exception {
                        return appDatabase.getProductDao().getProductById(productId);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Product>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Product product) {
                        iDatabaseOps.onSuccess(product);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if(e instanceof NullPointerException){
                            iDatabaseOps.onError("Product not found", e);
                        }else {
                            iDatabaseOps.onError("Something went wrong", e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getVendorAndProductStats(int productId, String vendorEmail, IDatabaseOps iDatabaseOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, List<Integer>>() {
                    @Override
                    public List<Integer> apply(AppDatabase appDatabase) throws Exception {
                        int vendorOrdersBookedCount = appDatabase.getOrderItemDao().getVendorOrdersBookedCount(vendorEmail);
                        int productBookedCount = appDatabase.getOrderItemDao().getProductBookedCount(productId);
                        List<Integer> vendorAndProductStats = new ArrayList<>();
                        vendorAndProductStats.add(vendorOrdersBookedCount);
                        vendorAndProductStats.add(productBookedCount);
                        return vendorAndProductStats;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> vendorAndProductStats) {
                        iDatabaseOps.onSuccess(vendorAndProductStats);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if(e instanceof NullPointerException){
                            iDatabaseOps.onError("Product not found", e);
                        }else {
                            iDatabaseOps.onError("Something went wrong", e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void addToCart(String userEmail,int productId, int quantity, IDatabaseOps iDatabaseOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, Object>() {
                    @Override
                    public Object apply(AppDatabase appDatabase) throws Exception {
                        Cart cart = appDatabase.getCartDao().getCartByUserAndProductId(userEmail,productId);
                        if(cart != null) {
                            return "Product already added in cart.";
                        }else {
                            List<CartJoinProduct> presentCartList = appDatabase.getCartDao().getUserCart(userEmail);
                            if(presentCartList != null){
                                if(presentCartList.size() > 0){
                                    Product productToBeAdded = appDatabase.getProductDao().getProductById(productId);
                                    if(!presentCartList.get(0).getProductVendor().equals(productToBeAdded.getProductVendor())){
                                        return "Products of same vendor are allowed in one order.";
                                    }
                                }
                            }
                            Cart newCart = new Cart();
                            newCart.setUserEmail(userEmail);
                            newCart.setProductId(productId);
                            newCart.setProductQuantity(quantity);
                            appDatabase.getCartDao().insert(newCart);
                            return newCart;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object object) {
                        iDatabaseOps.onSuccess(object);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        iDatabaseOps.onError("Something went wrong", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
