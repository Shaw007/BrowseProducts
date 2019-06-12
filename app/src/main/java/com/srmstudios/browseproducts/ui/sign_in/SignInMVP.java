package com.srmstudios.browseproducts.ui.sign_in;

import com.srmstudios.browseproducts.data.room.model.User;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public interface SignInMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void openHomeScreen(User user);
    }

    interface Presenter{
        void onClickBtnSignIn(String email,
                              String password);
    }

    interface Model{
        void getUser(String email, IDatabaseOps iDatabaseOps);
    }

}
