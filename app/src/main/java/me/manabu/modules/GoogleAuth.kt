package me.manabu.modules

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import me.manabu.R

object GoogleAuth{

    var mGoogleSignInOptions: GoogleSignInOptions? = null
        private set

    fun getClientForActivity(activity: Activity): GoogleSignInClient {
        if(mGoogleSignInOptions != null){
            mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getString(R.string.server_client_id))
                    .requestEmail()
                    .build()
        }

        return GoogleSignIn.getClient(activity, mGoogleSignInOptions!!)
    }
}