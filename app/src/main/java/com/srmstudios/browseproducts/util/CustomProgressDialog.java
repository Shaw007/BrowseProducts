package com.srmstudios.browseproducts.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.srmstudios.browseproducts.R;

public class CustomProgressDialog {
    private Dialog dialog;
    private Context context;
    private Utils utils;

    public CustomProgressDialog(Context context, int mesgRes){
        this.context = context;
        this.dialog = new Dialog(context);
        this.dialog.setCancelable(false);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.layout_dialog_av_loader_with_text);
        ((TextView)dialog.findViewById(R.id.txtMesg)).setText(Utils.getStringFromResourceId(context,mesgRes));
    }

    public void showDialog(){
        this.dialog.show();
    }

    public void hideDialog(){
        this.dialog.dismiss();
    }
}
