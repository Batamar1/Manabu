package me.manabu.modules


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

object Authentication {

    private lateinit var mGoogleSignInOptions: GoogleSignInOptions
    var account: GoogleSignInAccount? = null
        private set

    fun init(activity: Activity) {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.server_client_id))
                .requestEmail()
                .build()

        if (account == null) account = GoogleSignIn.getLastSignedInAccount(activity)
    }

    fun isSignedIn(): Boolean {
        account?.let { return true }
        return false
    }

    fun signIn(account: GoogleSignInAccount) {
        Authentication.account = account
    }

    fun signOut(activity: Activity) {
        val client = getClientForActivity(activity)
        client.signOut()
                .addOnSuccessListener { _ ->
                    account = null

                    val intent = Intent(activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    activity.startActivity(intent)
                }
                .addOnFailureListener { _ ->
                    Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.login_sign_out_failed),
                            Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener { _ -> Log.d("Authentication", "signOut done!") }
    }

    fun getClientForActivity(activity: Activity): GoogleSignInClient {
        return GoogleSignIn.getClient(activity, mGoogleSignInOptions)
    }
}