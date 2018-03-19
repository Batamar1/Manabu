package me.manabu.utils


import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import me.manabu.R
import me.manabu.activities.MainActivity

object AuthUtils {

    private var mGoogleSignInOptions: GoogleSignInOptions? = null
    var account: GoogleSignInAccount? = null
        private set

    fun init(activity: Activity) {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.server_client_id))
                .requestEmail()
                .build()

        if (account == null) {
            account = GoogleSignIn.getLastSignedInAccount(activity)
        }
    }

    fun isSignedIn(activity: Activity): Boolean {
        if (account == null || account!!.account == null) {
            Log.d("AuthUtils", "isSignedIn false")
            return false
        }
        Log.d("AuthUtils", "isSignedIn true")
        return true
    }

    fun signIn(account: GoogleSignInAccount) {
        this.account = account
        Log.d("AuthUtils", "signIn done")
    }

    fun signOut(activity: Activity) {
        val client = getClientForActivity(activity)
        client.signOut()
                .addOnSuccessListener { aVoid ->
                    account = null

                    val intent = Intent(activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    activity.startActivity(intent)

                    Log.d("AuthUtils", "signOut success!")
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.main_double_exit),
                            Toast.LENGTH_SHORT).show()
                    Log.d("AuthUtils", "signOut failed!")
                }
                .addOnCompleteListener { e -> Log.d("AuthUtils", "signOut done!") }
    }

    fun getClientForActivity(activity: Activity): GoogleSignInClient {
        return GoogleSignIn.getClient(activity, mGoogleSignInOptions!!)
    }
}