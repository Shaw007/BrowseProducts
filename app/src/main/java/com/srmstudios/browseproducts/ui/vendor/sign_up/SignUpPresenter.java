package com.srmstudios.browseproducts.ui.vendor.sign_up;

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
    public void onClickBtnSignUp(String name, String email, String password, String confirmPassword) {
        model.insertUser(name,email,password, new IDatabaseOps() {
            @Override
            public void onSuccess(Object response) {
                if(response instanceof String){
                    view.showToast(response.toString());
                }else if(response instanceof User){
                    User user = (User) response;
                    view.showToast(user.getEmail());
                }
            }

            @Override
            public void onError(String message,Throwable throwable) {
                view.showToast(message);
            }
        });
    }


}











