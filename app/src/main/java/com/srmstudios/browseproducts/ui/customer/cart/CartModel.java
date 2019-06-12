package com.srmstudios.browseproducts.ui.customer.cart;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.data.room.model.CartJoinProduct;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CartModel implements CartMVP.Model {
    private AppDatabase appDatabase;

    public CartModel(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void getUserCart(String userEmail, IDatabaseListOps iDatabaseListOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, List<CartJoinProduct>>() {
                    @Override
                    public List<CartJoinProduct> apply(AppDatabase appDatabase) throws Exception {
                        return appDatabase.getCartDao().getUserCart(userEmail);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CartJoinProduct>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CartJoinProduct> cartJoinProducts) {
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
