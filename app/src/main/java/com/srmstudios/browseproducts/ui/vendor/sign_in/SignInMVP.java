package com.srmstudios.browseproducts.ui.vendor.sign_in;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public interface SignInMVP {

    interface View{
        void showToast(int resourceId);
        void showToast(String message);
    }

    interface Presenter{
        void onClickBtnSignIn(String email,
                              String password);
    }

    interface Model{
        void getUser(String email, IDatabaseOps iDatabaseOps);
    }

}
