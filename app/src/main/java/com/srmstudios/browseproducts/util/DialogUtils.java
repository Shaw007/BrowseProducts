package com.srmstudios.browseproducts.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.util.interfaces.DialogBoxOneButtonCallback;
import com.srmstudios.browseproducts.util.interfaces.DialogBoxTwoButtonCallback;
import com.srmstudios.browseproducts.util.interfaces.DialogBoxTwoButtonInputCallback;

public class DialogUtils {

    public static void showSingleButtonDialog(Context context, String title, String message){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_box_single_button);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);

        txtTitle.setText(title);
        txtMessage.setText(message);

        dialog.setCanceledOnTouchOutside(false);

        final LinearLayout linearBox = dialog.findViewById(R.id.linearBox);
        linearBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void showSingleButtonDialog(Context context, String title, String message, DialogBoxOneButtonCallback dialogBoxOneButtonCallback){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_box_single_button);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);

        txtTitle.setText(title);
        txtMessage.setText(message);

        dialog.setCanceledOnTouchOutside(false);

        final LinearLayout linearBox = dialog.findViewById(R.id.linearBox);
        linearBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogBoxOneButtonCallback.onDismiss();
            }
        });

        dialog.show();
    }

    public static void showTwoButonDialogBox(final Context context, String title, String message, final DialogBoxTwoButtonCallback callback){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_box_two_button);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);

        txtTitle.setText(title);
        txtMessage.setText(message);
        TextView txtYes = dialog.findViewById(R.id.txtYes);
        TextView txtNo = dialog.findViewById(R.id.txtNo);
        dialog.setCanceledOnTouchOutside(false);

        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.onFailure();

            }
        });

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                callback.onSuccess();
            }
        });


        dialog.show();
    }

    public static void showTwoButonDiscountInputDialogBox(final Context context, String title,String initialValue, final DialogBoxTwoButtonInputCallback callback){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_box_two_button_discount_input);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        EditText edtProductDiscount = dialog.findViewById(R.id.edtProductDiscount);
        if(!initialValue.equals("0")) {
            edtProductDiscount.setText(initialValue);
        }

        txtTitle.setText(title);
        TextView txtYes = dialog.findViewById(R.id.txtYes);
        TextView txtNo = dialog.findViewById(R.id.txtNo);
        dialog.setCanceledOnTouchOutside(false);
        edtProductDiscount.requestFocus();

        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.onFailure();

            }
        });

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.isEditTextNullOrEmpty(edtProductDiscount)){
                    Toast.makeText(context,
                            Utils.getStringFromResourceId(context,R.string.please_enter_discount),
                            Toast.LENGTH_LONG).show();
                    return;
                }
                dialog.dismiss();
                callback.onSuccess(edtProductDiscount.getText().toString());
            }
        });


        dialog.show();
    }
}
