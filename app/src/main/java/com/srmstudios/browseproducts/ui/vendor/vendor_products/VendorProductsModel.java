package com.srmstudios.browseproducts.ui.vendor.vendor_products;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class VendorProductsModel implements VendorProductsMVP.Model {
    private AppDatabase appDatabase;

    public VendorProductsModel(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void getVendorProducts(String vendorName, IDatabaseListOps iDatabaseListOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, List<Product>>() {
                    @Override
                    public List<Product> apply(AppDatabase appDatabase) throws Exception {
                        return appDatabase.getProductDao().getVendorProducts(vendorName);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Product>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Product> products) {
                        iDatabaseListOps.onSuccess(products);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        iDatabaseListOps.onError("Something went wrong", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void updateProductDiscount(int productId, int newDiscount, IDatabaseOps iDatabaseOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, Object>() {
                    @Override
                    public Object apply(AppDatabase appDatabase) throws Exception {
                        appDatabase.getProductDao().updateProductDiscount(productId,newDiscount);
                        return "Discount updated successfully.";
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
