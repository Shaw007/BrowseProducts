package com.srmstudios.browseproducts.ui.customer.cart;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.util.CustomProgressDialog;
import com.srmstudios.browseproducts.util.DialogUtils;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.interfaces.DialogBoxOneButtonCallback;
import com.srmstudios.browseproducts.util.interfaces.DialogBoxTwoButtonCallback;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;
import com.srmstudios.browseproducts.util.singleton.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements CartMVP.View,View.OnClickListener,DatePickerFragment.DateSelectedListener{
    @BindView(R.id.recyclerViewCart)
    protected RecyclerView recyclerViewCart;
    @BindView(R.id.txtCartTotalAmount)
    protected TextView txtCartTotalAmount;
    @BindView(R.id.btnCheckout)
    protected Button btnCheckout;

    private Unbinder unbinder;
    private CartPresenter presenter;
    private Location currentLocation;
    private String deliveryDateFormatted;
    private CustomProgressDialog customProgressDialogPleaseWait;
    private boolean hasCustomerPressedCheckout = false;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CartPresenter(this,
                new CartModel(BrowseProductsDatabase.getInstance(getContext()).getAppDatabase()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));
        btnCheckout.setOnClickListener(this);

        presenter.getUserCart(SessionManager.getInstance(getContext()).getUser().getEmail());
        customProgressDialogPleaseWait = new CustomProgressDialog(getContext(),R.string.please_wait);

        showTurnOnOffLocationDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCheckout:{
                DialogUtils.showTwoButonDialogBox(getContext(),
                        Utils.getStringFromResourceId(getContext(),R.string.proceed_to_checkout),
                        Utils.getStringFromResourceId(getContext(),R.string.are_you_sure_checkout),
                        new DialogBoxTwoButtonCallback() {
                            @Override
                            public void onSuccess() {
                                DialogFragment datePickerFragment = new DatePickerFragment(CartFragment.this,
                                        Utils.getMillisecondsByAddingMonthsToCurrentTimestamp(1));
                                datePickerFragment.show(getActivity().getSupportFragmentManager(), "DatePickerDialog");
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        stopLocationUpdates();
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
    public void setRecyclerViewCartAdapter(CartAdapter adapter) {
        recyclerViewCart.setAdapter(adapter);
    }

    @Override
    public void setTxtTotalCartAmount(String totalAmount) {
        txtCartTotalAmount.setText("PKR " + totalAmount);
    }

    @Override
    public String getLoggedInUserEmail() {
        return SessionManager.getInstance(getContext()).getUser().getEmail();
    }

    @Override
    public void showDeleteItemConfirmationDialog(int cartId) {
        DialogUtils.showTwoButonDialogBox(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                Utils.getStringFromResourceId(getContext(),R.string.are_you_sure_remove_cart_item),
                new DialogBoxTwoButtonCallback() {
                    @Override
                    public void onSuccess() {
                        presenter.deleteCartItem(cartId);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
    }

    @Override
    public void onDateSelected(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        try {
            Calendar calendarDeliveryDate = Calendar.getInstance();
            calendarDeliveryDate.set(Calendar.YEAR,year);
            calendarDeliveryDate.set(Calendar.MONTH,monthOfYear);
            calendarDeliveryDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM, yyyy");
            deliveryDateFormatted = simpleDateFormat.format(calendarDeliveryDate.getTime());
            if(currentLocation == null){
                customProgressDialogPleaseWait.showDialog();
                hasCustomerPressedCheckout = true;
            }else {
                initiateCheckout();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            showDialogMessage(ex.getMessage());
        }
    }

    private void initiateCheckout(){
        presenter.proceedToCheckout(deliveryDateFormatted,
                currentLocation.getLatitude(),
                currentLocation.getLongitude());
        stopLocationUpdates();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Log.d("BOSS_DK","Location updates started");
            Log.d("BOSS_DK",locationResult.getLastLocation().getLatitude() + "," +
                    locationResult.getLastLocation().getLongitude());
            currentLocation = locationResult.getLastLocation();
            if(hasCustomerPressedCheckout){
                initiateCheckout();
                hasCustomerPressedCheckout = false;
                customProgressDialogPleaseWait.hideDialog();
            }
            /*Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(
                        currentLocation.getLatitude(),
                        currentLocation.getLongitude(),
                        // In this sample, get just a single address.
                        1);
                String str = "";
            }catch (Exception ex){
                ex.printStackTrace();
            }*/
        }
    };

    private LocationRequest createLocationRequest(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates(){
        LocationServices.getFusedLocationProviderClient(getContext()).requestLocationUpdates(createLocationRequest(),
                locationCallback,
                Looper.myLooper());
    }

    private void stopLocationUpdates(){
        LocationServices.getFusedLocationProviderClient(getContext()).removeLocationUpdates(locationCallback);
        Log.d("BOSS_DK","Location updates stopped");
    }

    private void permissionGranted(){
        startLocationUpdates();
    }

    private void onLocationSettingsTurnedOnByUser(){
        requestCameraPermissionFromUser();
    }

    private void onLocationSettingsTurnedOffByUser(){
        DialogUtils.showSingleButtonDialog(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                Utils.getStringFromResourceId(getContext(),R.string.location_setting_is_required),
                new DialogBoxOneButtonCallback() {
                    @Override
                    public void onDismiss() {
                        showTurnOnOffLocationDialog();
                    }
                });
    }

    private static final int REQUEST_CHECK_SETTINGS = 167;
    protected void showTurnOnOffLocationDialog() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest());

        Task<LocationSettingsResponse> task =
                LocationServices.getSettingsClient(getContext()).checkLocationSettings(builder.build());

        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    onLocationSettingsTurnedOnByUser();
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        getActivity(),
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        onLocationSettingsTurnedOnByUser();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        onLocationSettingsTurnedOffByUser();
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    // PERMISSION CODE
    final private int REQUEST_CODE_ASK_PERMISSIONS = 125;
    private boolean mPermissionDenied = false;
    private void requestCameraPermissionFromUser() {
        int hasLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if ( (hasLocationPermission != PackageManager.PERMISSION_GRANTED) ) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        } else {
            permissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission Allowed
                    int hasLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
                    if ( (hasLocationPermission == PackageManager.PERMISSION_GRANTED) ) {
                        permissionGranted();
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
                Utils.getStringFromResourceId(getContext(), R.string.alert),
                Utils.getStringFromResourceId(getContext(), R.string.location_permission_required),
                new DialogBoxOneButtonCallback() {
                    @Override
                    public void onDismiss() {
                        requestCameraPermissionFromUser();
                    }
                });
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
}























