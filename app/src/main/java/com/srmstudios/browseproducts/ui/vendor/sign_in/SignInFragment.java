package com.srmstudios.browseproducts.ui.vendor.sign_in;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;

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

    private Unbinder unbinder;
    private SignInPresenter presenter;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignInPresenter(this,
                new SignInModel(BrowseProductsDatabase.getInstance(getActivity().getApplicationContext())));
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
        }
    }

    private boolean validateInputFields(){
        if(Utils.isEditTextNullOrEmpty(edtEmail)){
            showToast(R.string.please_enter_email);
            return false;
        }
        if(!Utils.isValidEmail(edtEmail.getText().toString())){
            showToast(R.string.please_enter_valid_email);
            return false;
        }
        if(Utils.isEditTextNullOrEmpty(edtPassword)){
            showToast(R.string.please_enter_password);
            return false;
        }
        Utils.hideSoftKeyboard(getActivity());
        return true;
    }

    @Override
    public void showToast(int resourceId) {
        Utils.showToastLongTime(getActivity(),resourceId);
    }

    @Override
    public void showToast(String message) {
        Utils.showToastLongTime(getActivity(),message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
