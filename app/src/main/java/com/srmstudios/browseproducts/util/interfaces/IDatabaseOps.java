package com.srmstudios.browseproducts.util.interfaces;

public interface IDatabaseOps<T> {
    void onSuccess(T response);
    void onError(String message,Throwable throwable);
}
