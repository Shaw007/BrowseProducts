package com.srmstudios.browseproducts.ui.vendor.sales;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.data.room.model.VendorSales;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SalesModel implements SalesMVP.Model {
    private AppDatabase appDatabase;

    public SalesModel(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }


    @Override
    public void getVendorOrdersByBookingDate(String bookingDate,String vendorEmail,IDatabaseListOps iDatabaseListOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, List<VendorSales>>() {
                    @Override
                    public List<VendorSales> apply(AppDatabase appDatabase) throws Exception {
                        return appDatabase.getOrderItemDao().getVendorSalesByBookingDate(bookingDate,vendorEmail);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VendorSales>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<VendorSales> vendorSales) {
                        iDatabaseListOps.onSuccess(vendorSales);
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















