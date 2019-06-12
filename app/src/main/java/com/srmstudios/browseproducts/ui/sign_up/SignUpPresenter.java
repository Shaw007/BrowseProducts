package com.srmstudios.browseproducts.ui.sign_up;

import com.srmstudios.browseproducts.data.room.model.User;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public class SignUpPresenter implements SignUpMVP.Presenter {
    private SignUpMVP.View view;
    private SignUpMVP.Model model;

    public SignUpPresenter(SignUpMVP.View view, SignUpMVP.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onClickBtnSignUp(String name, String email, String password, boolean isCustomer) {
        model.insertUser(name,email,password, isCustomer, new IDatabaseOps() {
            @Override
            public void onSuccess(Object response) {
                if(response instanceof String){
                    view.showDialogMessage(response.toString());
                }else if(response instanceof User){
                    User user = (User) response;
                    view.openHomeScreen(user);
                }
            }

            @Override
            public void onError(String message,Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }


}











