package me.manabu.helpers;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import me.manabu.R;
import me.manabu.activities.MainActivity;

public class AuthHelper {

    public static final int RC_SIGN_IN = 111;
    private static final String IS_LOGGED = "AUTH_IS_LOGGED";

    private static SharedPreferences sPref;
    private static GoogleSignInOptions mGoogleSignInOptions;
    private static GoogleSignInAccount googleSignInAccount;

    public static void init(Activity activity) {
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.server_client_id))
                .requestEmail()
                .build();

        if (googleSignInAccount == null) {
            googleSignInAccount = GoogleSignIn.getLastSignedInAccount(activity);
        }
    }

    public static boolean isSignedIn(Activity activity) {
        sPref = PreferenceManager.getDefaultSharedPreferences(activity);
        boolean loginState = sPref.getBoolean(IS_LOGGED, false);
        Log.d("AuthHelper", "isSignedIn: " + String.valueOf(loginState));
        return loginState;
    }

    public static void signIn(Activity activity, GoogleSignInAccount account) {
        googleSignInAccount = account;
        sPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(IS_LOGGED, true);
        editor.apply();
        Log.d("AuthHelper", "signIn done");
    }

    public static void silentSignIn(Activity activity) {

    }

    public static void signOut(Activity activity) {
        GoogleSignInClient client = getClientForActivity(activity);
        client.signOut()
                .addOnSuccessListener(aVoid -> {
                    sPref = PreferenceManager.getDefaultSharedPreferences(activity);
                    SharedPreferences.Editor editor = sPref.edit();
                    editor.putBoolean(IS_LOGGED, false);
                    editor.apply();

                    googleSignInAccount = null;

                    Log.d("AuthHelper", "signOut success!");
                })
                .addOnFailureListener(e -> Log.d("AuthHelper", "signOut failed!"))
                .addOnCompleteListener(e -> Log.d("AuthHelper", "signOut done!"));
    }

    public static GoogleSignInClient getClientForActivity(Activity activity) {
        return GoogleSignIn.getClient(activity, mGoogleSignInOptions);
    }

    public static GoogleSignInAccount getAccount() {
        return googleSignInAccount;
    }
}