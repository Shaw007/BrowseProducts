package com.srmstudios.browseproducts.ui.vendor.sales;


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
import com.srmstudios.browseproducts.ui.customer.cart.DatePickerFragment;
import com.srmstudios.browseproducts.util.AppConstants;
import com.srmstudios.browseproducts.util.DialogUtils;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;
import com.srmstudios.browseproducts.util.singleton.SessionManager;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment implements SalesMVP.View,View.OnClickListener, DatePickerFragment.DateSelectedListener {
    @BindView(R.id.txtSelectedDate)
    protected TextView txtSelectedDate;
    @BindView(R.id.btnSelectDate)
    protected Button btnSelectDate;
    @BindView(R.id.txtTotalDayEndSales)
    protected TextView txtTotalDayEndSales;
    @BindView(R.id.txtTotalOrdersReceived)
    protected TextView txtTotalOrdersReceived;
    @BindView(R.id.recyclerViewSales)
    protected RecyclerView recyclerViewSales;

    private Unbinder unbinder;
    private SalesPresenter presenter;

    public SalesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SalesPresenter(this,
                new SalesModel(BrowseProductsDatabase.getInstance(getContext()).getAppDatabase()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sales, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        recyclerViewSales.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSales.setNestedScrollingEnabled(false);

        txtSelectedDate.setText(Utils.getFormattedDateString(AppConstants.DATE_FORMAT_ONE,new Date()));

        btnSelectDate.setOnClickListener(this);

        getVendorSales(new Date());
    }

    private void getVendorSales(Date bookingDate){
        presenter.getVendorOrdersByBookingDate(Utils.getFormattedDateString(AppConstants.DATE_FORMAT_TWO,bookingDate),
                SessionManager.getInstance(getContext()).getUser().getEmail());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSelectDate:{
                long minDateMilliseconds = Utils.getMillisecondsBySubtractingYearsToCurrentTimestamp(-10);
                long maxDateMilliseconds = new Date().getTime();
                Date selectedDate = Utils.getFormattedDate(AppConstants.DATE_FORMAT_ONE,txtSelectedDate.getText().toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedDate);

                DialogFragment datePickerFragment = new DatePickerFragment(SalesFragment.this,
                        minDateMilliseconds,
                        maxDateMilliseconds,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "DatePickerDialog");
                break;
            }
        }
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
    public void setRecyclerViewSalesAdapter(SalesAdapter adapter) {
        recyclerViewSales.setAdapter(adapter);
    }

    @Override
    public void setTxtTotalDayEndSales(double totalDayEndSales) {
        txtTotalDayEndSales.setText("Day End Sale: PKR " + totalDayEndSales);
    }

    @Override
    public void setTxtTotalOrdersReceived(int ordersReceived) {
        txtTotalOrdersReceived.setText("Total Orders Received: " + ordersReceived);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onDateSelected(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            txtSelectedDate.setText(Utils.getFormattedDateString(AppConstants.DATE_FORMAT_ONE,calendar.getTime()));
            getVendorSales(calendar.getTime());
        }catch (Exception ex){
            ex.printStackTrace();
            showDialogMessage(ex.getMessage());
        }
    }
}



