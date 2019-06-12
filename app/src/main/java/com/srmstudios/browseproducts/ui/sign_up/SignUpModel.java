package com.srmstudios.browseproducts.ui.sign_up;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.data.room.model.User;
import com.srmstudios.browseproducts.util.Crypto;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SignUpModel implements SignUpMVP.Model {
    private AppDatabase appDatabase;

    public SignUpModel(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void insertUser(String name, String email, String password, boolean isCustomer, IDatabaseOps iDatabaseOps) {
        Observable.just(appDatabase)
                .map(new Function<AppDatabase, Object>() {
                    @Override
                    public Object apply(AppDatabase appDatabase) throws Exception {
                        User user = appDatabase.getUserDao().getUser(email);
                        if(user != null) {
                            return "User already exist";
                        }else {
                            User newUser = new User();
                            newUser.setName(name);
                            newUser.setEmail(email);
                            newUser.setPassword(Crypto.encrypt(password));
                            newUser.setIsCustomer(isCustomer);
                            appDatabase.getUserDao().insert(newUser);
                            return newUser;
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
