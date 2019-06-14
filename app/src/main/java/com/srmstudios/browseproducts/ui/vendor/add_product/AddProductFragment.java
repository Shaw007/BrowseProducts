package com.srmstudios.browseproducts.ui.vendor.add_product;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.util.CustomProgressDialog;
import com.srmstudios.browseproducts.util.DialogUtils;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;
import com.srmstudios.browseproducts.util.singleton.SessionManager;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment implements AddProductMVP.View,View.OnClickListener{
    @BindView(R.id.imgAddProductImage)
    protected ImageView imgAddProductImage;
    @BindView(R.id.edtProductName)
    protected EditText edtProductName;
    @BindView(R.id.edtProductDesc)
    protected EditText edtProductDesc;
    @BindView(R.id.edtProductPrice)
    protected EditText edtProductPrice;
    @BindView(R.id.edtProductDiscount)
    protected EditText edtProductDiscount;
    @BindView(R.id.btnAddProduct)
    protected Button btnAddProduct;

    private Unbinder unbinder;
    private AddProductPresenter presenter;
    private String imageUrlMain = "";
    private CustomProgressDialog customProgressDialogPleaseWait;

    public AddProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AddProductPresenter(this,
                new AddProductModel(BrowseProductsDatabase.getInstance(getContext()).getAppDatabase()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v) {
        unbinder = ButterKnife.bind(this, v);

        imgAddProductImage.setOnClickListener(this);
        btnAddProduct.setOnClickListener(this);
        customProgressDialogPleaseWait = new CustomProgressDialog(getContext(),R.string.please_wait);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgAddProductImage:{
                requestCameraPermissionFromUser();
                break;
            }
            case R.id.btnAddProduct:{
                if(validateInputFields()){
                    int discount = 0;
                    if(Utils.isEditTextNullOrEmpty(edtProductDiscount)){
                        discount = 0;
                    }else {
                        discount = Integer.parseInt(edtProductDiscount.getText().toString());
                    }
                    String price = "";
                    if(edtProductPrice.getText().toString().startsWith(".")){
                        price = "0" + edtProductPrice.getText().toString();
                    }else if(price.endsWith(".")){
                        price = edtProductPrice.getText().toString() + "0";
                    }else {
                        price = edtProductPrice.getText().toString();
                    }
                    presenter.onClickBtnAddProduct(imageUrlMain,
                            edtProductName.getText().toString(),
                            edtProductDesc.getText().toString(),
                            Double.parseDouble(price),
                            SessionManager.getInstance(getContext()).getUser().getName(),
                            SessionManager.getInstance(getContext()).getUser().getEmail(),
                            discount);
                }
                break;
            }
        }
    }

    private boolean validateInputFields(){
        if(imageUrlMain.equals("")) {
            showDialogMessage(R.string.please_capture_image);
            return false;
        }
        if(Utils.isEditTextNullOrEmpty(edtProductName)){
            showDialogMessage(R.string.please_enter_product_name);
            return false;
        }
        if(Utils.isEditTextNullOrEmpty(edtProductDesc)){
            showDialogMessage(R.string.please_enter_product_desc);
            return false;
        }
        if(Utils.isEditTextNullOrEmpty(edtProductPrice)){
            showDialogMessage(R.string.please_enter_product_price);
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
    public void clearInputFields() {
        imageUrlMain = "";
        imgAddProductImage.setImageResource(android.R.drawable.btn_plus);
        edtProductName.setText("");
        edtProductDesc.setText("");
        edtProductPrice.setText("");
        edtProductDiscount.setText("");
        edtProductName.requestFocus();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPermissionDenied) {
            // Permission was not granted, displaying error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void permissionsGranted(){
        captureImage();
    }


    // CAMERA CODE
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private void captureImage(){
        File file = Utils.getOutputMediaFile(Utils.MEDIA_TYPE_IMAGE);
        if(file != null) {
            imageUrlMain = file.getAbsolutePath();
            SessionManager.getInstance(getContext()).setImageUrl(imageUrlMain);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fileUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", file);
            } else {
                fileUri = Uri.fromFile(file);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        }else {
            DialogUtils.showSingleButtonDialog(getContext(),
                    Utils.getStringFromResourceId(getContext(),R.string.alert),
                    Utils.getStringFromResourceId(getContext(),R.string.unable_to_create_image_directory));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == getActivity().RESULT_OK) {
                    new CompressImage().execute();
                }
            }
        }
    }

    private class CompressImage extends AsyncTask<Void,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!(getActivity().isFinishing())) {
                customProgressDialogPleaseWait.showDialog();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            String savedPath = SessionManager.getInstance(getContext()).getImageUrl();
            Bitmap bitmap = Utils.getCompressedBitmap(getContext(),savedPath);
            if(bitmap != null){
                String newPath = Utils.savePhoto(getContext(),bitmap);
                bitmap.recycle();
                return newPath;
            }else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                imgAddProductImage.setImageURI(Utils.getUriFromFile(getContext(),s));
                imageUrlMain = s;
                customProgressDialogPleaseWait.hideDialog();
            } catch (Exception ex) {
                ex.printStackTrace();
                DialogUtils.showSingleButtonDialog(getContext(),
                        Utils.getStringFromResourceId(getContext(),R.string.alert),
                        ex.getMessage());
            }
        }
    }



    // PERMISSION CODE
    final private int REQUEST_CODE_ASK_PERMISSIONS = 125;
    private boolean mPermissionDenied = false;
    private void requestCameraPermissionFromUser() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ( (hasCameraPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasReadPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasWritePermission != PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        } else {
            permissionsGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[1] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[2] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission Allowed
                    int hasCameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                    int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if ( (hasCameraPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasReadPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasWritePermission == PackageManager.PERMISSION_GRANTED)
                    ) {
                        permissionsGranted();
                    }
                } else {
                    // Permission Denied
                    mPermissionDenied = true;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        DialogUtils.showSingleButtonDialog(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                Utils.getStringFromResourceId(getContext(),R.string.these_permissions_are_required));
    }
}
