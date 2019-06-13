package com.srmstudios.browseproducts.ui.customer.cart;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.srmstudios.browseproducts.R;

import java.util.Calendar;

/**
 * Created by Shahrukh Malik on 12/28/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DateSelectedListener dateSelectedListener;
    private long millisecondsOfTwoMonthFromNow;

    public DatePickerFragment(){

    }

    @SuppressLint("ValidFragment")
    public DatePickerFragment(DateSelectedListener dateSelectedListener,long millisecondsOfTwoMonthFromNow){
        this.dateSelectedListener = dateSelectedListener;
        this.millisecondsOfTwoMonthFromNow = millisecondsOfTwoMonthFromNow;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
            // current time by default
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int dayOnMonth = c.get(Calendar.DAY_OF_MONTH);

            Calendar calendarTwoDaysAfter = Calendar.getInstance();
            calendarTwoDaysAfter.add(Calendar.DAY_OF_MONTH, 2);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerDialogTheme,this,year,month,dayOnMonth);
            datePickerDialog.getDatePicker().setMinDate(calendarTwoDaysAfter.getTime().getTime());
            datePickerDialog.getDatePicker().setMaxDate(millisecondsOfTwoMonthFromNow);
            return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        this.dateSelectedListener.onDateSelected(datePicker,year,monthOfYear,dayOfMonth);
    }

    public interface DateSelectedListener {
        void onDateSelected(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth);
    }

}

