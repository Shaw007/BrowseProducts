package com.srmstudios.browseproducts.ui.sign_in;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.User;
import com.srmstudios.browseproducts.ui.account_selection.AccountSelectionActivity;
import com.srmstudios.browseproducts.ui.customer.home.CustomerHomeActivity;
import com.srmstudios.browseproducts.ui.vendor.home.VendorHomeActivity;
import com.srmstudios.browseproducts.util.DialogUtils;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;
import com.srmstudios.browseproducts.util.singleton.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements SignInMVP.View,View.OnClickListener{
    @BindView(R.id.edtEmail)
    protected EditText edtEmail;
    @BindView(R.id.edtPassword)
    protected EditText edtPassword;
    @BindView(R.id.btnSignIn)
    protected Button btnSignIn;
    @BindView(R.id.txtSignUp)
    protected TextView txtSignUp;

    private Unbinder unbinder;
    private SignInPresenter presenter;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignInPresenter(this,
                new SignInModel(BrowseProductsDatabase.getInstance(getContext()).getAppDatabase()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        btnSignIn.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignIn:{
                if(validateInputFields()){
                    presenter.onClickBtnSignIn(edtEmail.getText().toString(),
                            edtPassword.getText().toString());
                }
                break;
            }
            case R.id.txtSignUp:{
                Intent intent = new Intent(getContext(), AccountSelectionActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private boolean validateInputFields(){
        if(Utils.isEditTextNullOrEmpty(edtEmail)){
            showDialogMessage(R.string.please_enter_email);
            return false;
        }
        if(!Utils.isValidEmail(edtEmail.getText().toString())){
            showDialogMessage(R.string.please_enter_valid_email);
            return false;
        }
        if(Utils.isEditTextNullOrEmpty(edtPassword)){
            showDialogMessage(R.string.please_enter_password);
            return false;
        }
        Utils.hideSoftKeyboard(getActivity());
        return true;
    }

    @Override
    public void showDialogMessage(int resourceId) {
        DialogUtils.showSingleButtonDialog(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                Utils.getStringFromResourceId(getContext(),resourceId));
    }

    @Override
    public void showDialogMessage(String message) {
        DialogUtils.showSingleButtonDialog(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                message);
    }

    @Override
    public void openHomeScreen(User user) {
        Intent intent;
        if(user.isCustomer()) {
            intent = new Intent(getContext(), CustomerHomeActivity.class);
        }else {
            intent = new Intent(getContext(), VendorHomeActivity.class);
        }
        SessionManager.getInstance(getContext().getApplicationContext()).setUser(user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
