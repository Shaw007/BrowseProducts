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
    private long minDateMilliseconds;
    private long maxDateMilliseconds;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

    public DatePickerFragment(){

    }

    @SuppressLint("ValidFragment")
    public DatePickerFragment(DateSelectedListener dateSelectedListener, long minDateMilliseconds, long maxDateMilliseconds, int selectedYear, int selectedMonth, int selectedDay) {
        this.dateSelectedListener = dateSelectedListener;
        this.minDateMilliseconds = minDateMilliseconds;
        this.maxDateMilliseconds = maxDateMilliseconds;
        this.selectedYear = selectedYear;
        this.selectedMonth = selectedMonth;
        this.selectedDay = selectedDay;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        DatePickerDialog datePickerDialog;
        if(selectedYear == 0 && selectedMonth == 0 && selectedDay == 0){
            // current time by default
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int dayOnMonth = c.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerDialogTheme,this,year,month,dayOnMonth);
        }else {
            datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerDialogTheme,this,selectedYear,selectedMonth,selectedDay);
        }
        datePickerDialog.getDatePicker().setMinDate(minDateMilliseconds);
        datePickerDialog.getDatePicker().setMaxDate(maxDateMilliseconds);
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

