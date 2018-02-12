package me.manabu.Controllers;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import me.manabu.LoginActivity;

public class LoginController {

    private static SharedPreferences sPref;

    private static final String IS_LOGGED = "LOGIN_IS_LOGGED";

    public static boolean isLogged(Activity activity){
        sPref = PreferenceManager.getDefaultSharedPreferences(activity);
        boolean loginState = sPref.getBoolean(IS_LOGGED,false);
        Log.d("LoginController", "isLogged: " + String.valueOf(loginState));
        return loginState;
    }

    public static void logIn(Activity activity){
        sPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(IS_LOGGED, true);
        editor.apply();
        Log.d("LoginController", "Login done");
    }

    public static void logOut(Activity activity){
        sPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(IS_LOGGED, false);
        editor.apply();
        Log.d("LoginController", "Logout done");
    }

}
