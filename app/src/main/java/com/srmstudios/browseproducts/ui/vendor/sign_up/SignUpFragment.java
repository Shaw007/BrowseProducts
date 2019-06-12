package com.srmstudios.browseproducts.ui.vendor.sign_up;


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
public class SignUpFragment extends Fragment implements SignUpMVP.View,View.OnClickListener{
    @BindView(R.id.edtName)
    protected EditText edtName;
    @BindView(R.id.edtEmail)
    protected EditText edtEmail;
    @BindView(R.id.edtPassword)
    protected EditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    protected EditText edtConfirmPassword;
    @BindView(R.id.btnSignUp)
    protected Button btnSignUp;

    private Unbinder unbinder;
    private SignUpPresenter presenter;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignUpPresenter(this,
                new SignUpModel(BrowseProductsDatabase.getInstance(getActivity().getApplicationContext())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUp:{
                if(validateInputFields()) {
                    presenter.onClickBtnSignUp(edtName.getText().toString(),
                            edtEmail.getText().toString(),
                            edtPassword.getText().toString(),
                            edtConfirmPassword.getText().toString());
                }
                break;
            }
        }
    }

    private boolean validateInputFields(){
        if(Utils.isEditTextNullOrEmpty(edtName)){
            showToast(R.string.please_enter_name);
            return false;
        }
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
        if(Utils.isEditTextNullOrEmpty(edtConfirmPassword)){
            showToast(R.string.please_enter_confirm_password);
            return false;
        }
        if(!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())){
            showToast(R.string.passwords_donot_match);
            return false;
        }
        Utils.hideSoftKeyboard(getActivity());
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showToast(int resourceId) {
        Utils.showToastLongTime(getActivity(),resourceId);
    }

    @Override
    public void showToast(String message) {
        Utils.showToastLongTime(getActivity(),message);
    }
}



