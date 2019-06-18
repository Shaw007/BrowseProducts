package com.srmstudios.browseproducts.ui.useful_resources;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.util.AppConstants;
import com.srmstudios.browseproducts.util.DialogUtils;
import com.srmstudios.browseproducts.util.PathUtil;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.interfaces.DialogBoxTwoButtonInputCallback;
import com.srmstudios.browseproducts.util.interfaces.DialogBoxTwoButtonInputMultipleReturnCallback;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;

import java.io.File;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsefulResourcesFragment extends Fragment implements UsefulResourcesMVP.View,View.OnClickListener{
    @BindView(R.id.btnAddLink)
    protected Button btnAddLink;
    @BindView(R.id.btnAddPDF)
    protected Button btnAddPDF;
    @BindView(R.id.recyclerViewUsefulResources)
    protected RecyclerView recyclerViewUsefulResources;
    @BindView(R.id.txtNoDateFound)
    protected TextView txtNoDateFound;

    private Unbinder unbinder;
    private UsefulResourcesPresenter presenter;
    private String selectedPdfUri;

    private static final int RESULT_CODE_GET_PDF = 456;

    public UsefulResourcesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UsefulResourcesPresenter(this,
                new UsefulResourcesModel(BrowseProductsDatabase.getInstance(getContext()).getAppDatabase()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_useful_resources, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        recyclerViewUsefulResources.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsefulResources.setNestedScrollingEnabled(false);

        btnAddLink.setOnClickListener(this);
        btnAddPDF.setOnClickListener(this);

        presenter.getRepoList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddLink:{
                DialogUtils.showTwoButtonLinkInputDialogBox(getContext(),
                        Utils.getStringFromResourceId(getContext(), R.string.add_link),
                        "",
                        new DialogBoxTwoButtonInputMultipleReturnCallback() {
                            @Override
                            public void onSuccess(String input1, String input2) {
                                presenter.addItemInList(AppConstants.REPO_TYPE_LINK,
                                        input1,input2);
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                break;
            }
            case R.id.btnAddPDF:{
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("application/pdf");
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(intent,RESULT_CODE_GET_PDF);
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case RESULT_CODE_GET_PDF:
                    if (resultCode == RESULT_OK) {
                        // Get the Uri of the selected file
                        Uri uri = data.getData();
                        String uriString = uri.toString();
                        File myFile = new File(uriString);
                        String displayName = null;
                        if (uriString.startsWith("content://")) {
                            Cursor cursor = null;
                            try {
                                cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                }
                            } finally {
                                cursor.close();
                            }
                        } else if (uriString.startsWith("file://")) {
                            displayName = myFile.getName();
                        }
                        presenter.addItemInList(AppConstants.REPO_TYPE_PDF,
                                displayName,
                                uriString);
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }catch (Exception ex){
            ex.printStackTrace();
            showDialogMessage(ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
    public void setRecyclerViewUsefulResourcesAdapter(UsefulResourcesAdapter adapter) {
        if(adapter == null){
            return;
        }
        if(adapter.getItemCount() == 0){
            txtNoDateFound.setVisibility(View.VISIBLE);
            txtNoDateFound.setText(Utils.getStringFromResourceId(getContext(),R.string.no_resources_found));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,200,0,0);
            txtNoDateFound.setLayoutParams(params);
            recyclerViewUsefulResources.setVisibility(View.GONE);
        }else {
            recyclerViewUsefulResources.setAdapter(adapter);
            recyclerViewUsefulResources.setVisibility(View.VISIBLE);
            txtNoDateFound.setVisibility(View.GONE);
        }
    }

    @Override
    public void showRecyclerViewUsefulResources() {
        recyclerViewUsefulResources.setVisibility(View.VISIBLE);
        txtNoDateFound.setVisibility(View.GONE);
    }

    @Override
    public void openLink(String url) {
        Utils.openURLIntent(getContext(),url);
    }

    @Override
    public void openPDF(String uri) {
        selectedPdfUri = uri;
        requestStoragePermissionFromUser();
    }

    private void permissionsGranted(){
        try {
            String path = PathUtil.getRealPath(getContext(),Uri.parse(selectedPdfUri));
            Utils.openPDFIntent(getContext(),path);
        }catch (Exception ex){
            ex.printStackTrace();
            showDialogMessage(ex.getMessage());
        }
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



    // PERMISSION CODE
    final private int REQUEST_CODE_ASK_PERMISSIONS = 125;
    private boolean mPermissionDenied = false;
    private void requestStoragePermissionFromUser() {
        int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (
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
                        (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission Allowed
                    int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (
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




