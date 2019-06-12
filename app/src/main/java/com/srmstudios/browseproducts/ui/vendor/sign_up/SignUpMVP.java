package com.srmstudios.browseproducts.ui.vendor.sign_up;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public interface SignUpMVP {

    interface View{
        void showToast(int resourceId);
        void showToast(String message);
    }

    interface Presenter{
        void onClickBtnSignUp(String name,
                              String email,
                              String password,
                              String confirmPassword);
    }

    interface Model{
        void insertUser(String name,String email,String password, IDatabaseOps iDatabaseOps);
    }
}












