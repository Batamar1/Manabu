package me.manabu.Controllers;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import me.manabu.Constants;
import me.manabu.LoginActivity;

import static android.content.Context.MODE_PRIVATE;

public class LoginController {

    private static SharedPreferences sPref;

    private static String IS_LOGGED = "IS_LOGGED";

    public static boolean isLogged(Context context){
        sPref = context.getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        return sPref.getBoolean(IS_LOGGED, false);
    }

}
