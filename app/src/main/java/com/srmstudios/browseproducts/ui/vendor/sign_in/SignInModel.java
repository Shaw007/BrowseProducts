package com.srmstudios.browseproducts.ui.vendor.sign_in;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.data.room.model.User;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SignInModel implements SignInMVP.Model {
    private AppDatabase appDatabase;

    public SignInModel(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void getUser(String email, IDatabaseOps iDatabaseOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, User>() {
                    @Override
                    public User apply(AppDatabase appDatabase) throws Exception {
                        return appDatabase.getUserDao().getUser(email);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        iDatabaseOps.onSuccess(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if(e instanceof NullPointerException){
                            iDatabaseOps.onError("User not found", e);
                        }else {
                            iDatabaseOps.onError("Something went wrong", e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
