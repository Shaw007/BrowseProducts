package com.srmstudios.browseproducts.ui.vendor.dispatch_orders;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.data.room.model.CartJoinProduct;
import com.srmstudios.browseproducts.data.room.model.VendorOrder;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DispatchOrdersModel implements DispatchOrdersMVP.Model{
    private AppDatabase appDatabase;

    public DispatchOrdersModel(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void getVendorOrders(String vendorUserEmail, IDatabaseListOps iDatabaseListOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, List<VendorOrder>>() {
                    @Override
                    public List<VendorOrder> apply(AppDatabase appDatabase) throws Exception {
                        return appDatabase.getCartDao().getVendorOrders(vendorUserEmail);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VendorOrder>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<VendorOrder> vendorOrders) {
                        iDatabaseListOps.onSuccess(vendorOrders);
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
}












