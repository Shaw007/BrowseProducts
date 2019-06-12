package com.srmstudios.browseproducts.util.singleton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.srmstudios.browseproducts.data.room.model.User;
import com.srmstudios.browseproducts.ui.sign_in.SignInActivity;
import com.srmstudios.browseproducts.ui.vendor.VendorHomeActivity;
import com.srmstudios.browseproducts.util.AppConstants;

public class SessionManager {
    private static SessionManager sessionManager;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;

    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER = "user";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(AppConstants.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public synchronized static SessionManager getInstance(Context context){
        if(sessionManager == null){
            sessionManager = new SessionManager(context);
        }
        return sessionManager;
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public void setUser(User user) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_USER, new Gson().toJson(user,User.class));
        editor.commit();
    }

    public User getUser() {
        String userString = pref.getString(KEY_USER, "");
        if(userString.equals("")){
            return null;
        }
        User user = new Gson().fromJson(userString,User.class);
        return user;
    }

    public void logoutUser() {
        editor.remove(IS_LOGGED_IN);
        editor.remove(KEY_USER);
        editor.apply();
        editor.commit();

        Intent intent = new Intent(context, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
