package com.srmstudios.browseproducts.ui.sign_in;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.User;
import com.srmstudios.browseproducts.util.Crypto;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public class SignInPresenter implements SignInMVP.Presenter {
    private SignInMVP.View view;
    private SignInMVP.Model model;

    public SignInPresenter(SignInMVP.View view, SignInMVP.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onClickBtnSignIn(String email, String password) {
       model.getUser(email, new IDatabaseOps() {
           @Override
           public void onSuccess(Object response) {
               if(response instanceof User){
                   User user = (User) response;
                   if(Crypto.decrypt(user.getPassword()).equals(password)) {
                       view.openHomeScreen(user);
                   }else {
                       view.showDialogMessage(R.string.wrong_password);
                   }
               }
           }

           @Override
           public void onError(String message, Throwable throwable) {
               view.showDialogMessage(message + ": " + throwable.getMessage());
           }
       });
    }
}
