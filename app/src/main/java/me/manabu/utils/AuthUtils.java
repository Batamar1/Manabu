package me.manabu.utils;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import me.manabu.R;
import me.manabu.activities.MainActivity;

public class AuthUtils {

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
        if(googleSignInAccount == null || googleSignInAccount.getAccount() == null) {
            Log.d("AuthUtils", "isSignedIn false");
            return false;
        }
        Log.d("AuthUtils", "isSignedIn true");
        return true;
    }

    public static void signIn(GoogleSignInAccount account) {
        googleSignInAccount = account;
        Log.d("AuthUtils", "signIn done");
    }

    public static void signOut(Activity activity) {
        GoogleSignInClient client = getClientForActivity(activity);
        client.signOut()
                .addOnSuccessListener(aVoid -> {
                    googleSignInAccount = null;

                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);

                    Log.d("AuthUtils", "signOut success!");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(
                            activity,
                            activity.getResources().getString(R.string.main_double_exit),
                            Toast.LENGTH_SHORT).show();
                    Log.d("AuthUtils", "signOut failed!");
                })
                .addOnCompleteListener(e -> Log.d("AuthUtils", "signOut done!"));
    }

    public static GoogleSignInClient getClientForActivity(Activity activity) {
        return GoogleSignIn.getClient(activity, mGoogleSignInOptions);
    }

    public static GoogleSignInAccount getAccount() {
        return googleSignInAccount;
    }
}