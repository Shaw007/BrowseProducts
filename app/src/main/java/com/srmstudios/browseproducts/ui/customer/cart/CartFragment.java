package com.srmstudios.browseproducts.ui.customer.cart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.util.DialogUtils;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.interfaces.DialogBoxTwoButtonCallback;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;
import com.srmstudios.browseproducts.util.singleton.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    public void onDateSelected(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        try {
            Calendar calendarDeliveryDate = Calendar.getInstance();
            calendarDeliveryDate.set(Calendar.YEAR,year);
            calendarDeliveryDate.set(Calendar.MONTH,monthOfYear);
            calendarDeliveryDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM, yyyy");
            String deliveryDateFormatted = simpleDateFormat.format(calendarDeliveryDate.getTime());
            presenter.proceedToCheckout(deliveryDateFormatted);
        }catch (Exception ex){
            ex.printStackTrace();
            showDialogMessage(ex.getMessage());
        }
    }
}























