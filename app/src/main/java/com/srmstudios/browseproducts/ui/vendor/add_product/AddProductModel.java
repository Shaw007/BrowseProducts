package com.srmstudios.browseproducts.ui.vendor.add_product;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AddProductModel implements AddProductMVP.Model {
    private AppDatabase appDatabase;

    public AddProductModel(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void addProduct(String productImage, String productName, String productDesc, double productPrice, String productVendor, String productVendorEmail, int productDiscount, IDatabaseOps iDatabaseOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, Object>() {
                    @Override
                    public Object apply(AppDatabase appDatabase) throws Exception {
                        Product product = appDatabase.getProductDao().getProductByName(productName);
                        if(product != null) {
                            return "Product with same name already exist. Try something else.";
                        }else {
                            Product newProduct = new Product();
                            newProduct.setProductImageUrl(productImage);
                            newProduct.setProductName(productName);
                            newProduct.setProductDetails(productDesc);
                            newProduct.setProductPrice(productPrice);
                            newProduct.setProductVendor(productVendor);
                            newProduct.setProductVendorEmail(productVendorEmail);
                            newProduct.setProductDiscount(productDiscount);
                            appDatabase.getProductDao().insert(newProduct);
                            return newProduct;
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
