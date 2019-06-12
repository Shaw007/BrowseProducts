package com.srmstudios.browseproducts.ui.sign_up;

import com.srmstudios.browseproducts.data.room.model.User;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public interface SignUpMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void openHomeScreen(User user);
    }

    interface Presenter{
        void onClickBtnSignUp(String name,
                              String email,
                              String password,
                              boolean isCustomer);
    }

    interface Model{
        void insertUser(String name,String email,String password, boolean isCustomer, IDatabaseOps iDatabaseOps);
    }
}












