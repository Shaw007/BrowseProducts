package com.srmstudios.browseproducts.ui.customer.order_history;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.data.room.model.CartJoinProduct;
import com.srmstudios.browseproducts.data.room.model.CustomerOrderHistory;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class OrderHistoryModel implements OrderHistoryMVP.Model {
    private AppDatabase appDatabase;

    public OrderHistoryModel(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void getCustomerOrderHistory(String email,IDatabaseListOps iDatabaseListOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, List<CustomerOrderHistory>>() {
                    @Override
                    public List<CustomerOrderHistory> apply(AppDatabase appDatabase) throws Exception {
                        return appDatabase.getCartDao().getCustomerOrderHistory(email);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CustomerOrderHistory>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CustomerOrderHistory> cartJoinProducts) {
                        iDatabaseListOps.onSuccess(cartJoinProducts);
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
