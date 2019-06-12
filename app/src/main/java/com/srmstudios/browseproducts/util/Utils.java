package com.srmstudios.browseproducts.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Utils {

    public static String removeLeadingAndTrailingSpaces(String str) {
        return str.replaceAll("^\\s+|\\s+$", "");
    }

    public static boolean isEditTextNullOrEmpty(EditText edt) {
        if (edt.getText() != null) {
            if (edt.getText().toString().trim().isEmpty()) {
                return true;
            } else {
                String trimmed = removeLeadingAndTrailingSpaces(edt.getText().toString());
                edt.setText(trimmed);
                edt.setSelection(edt.getText().length());
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void showToastLongTime(Context context,int resourceId){
        Toast.makeText(context,getStringFromResourceId(context,resourceId),Toast.LENGTH_LONG).show();
    }

    public static void showToastLongTime(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static String getStringFromResourceId(Context context,int stringResourceId){
        if(context != null) {
            return context.getResources().getString(stringResourceId);
        }
        else {
            return "";
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}












