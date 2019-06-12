package com.srmstudios.browseproducts.util.interfaces;

import java.util.List;

public interface IDatabaseListOps<T> {
    void onSuccess(List<T> response);
    void onError(String message,Throwable throwable);
}
