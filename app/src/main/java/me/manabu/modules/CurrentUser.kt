package me.manabu.modules

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import me.manabu.R
import me.manabu.activities.MainActivity
import me.manabu.webapi.Api
import me.manabu.webapi.models.BasicResponse
import me.manabu.webapi.models.DeckModel
import me.manabu.webapi.models.UserDeckModel
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CurrentUser {

    private var account: GoogleSignInAccount? = null
    var decks: MutableList<UserDeckModel> = mutableListOf()

    fun loadInfo(context: Context) {
        if (account == null) account = GoogleSignIn.getLastSignedInAccount(context)
    }

    fun isSignedIn(): Boolean {
        account?.account?.let { return true }
        return false
    }

    fun set(account: GoogleSignInAccount) {
        this.account = account
    }

    fun getAccount(): GoogleSignInAccount {
        if (!CurrentUser.isSignedIn()) {
            throw ExceptionInInitializerError("Account still not initialized")
        }

        return account!!
    }

    fun signOut(activity: Activity) {
        val client = GoogleAuth.getClientForActivity(activity)
        client.signOut()
                .addOnSuccessListener { _ ->
                    account = null

                    decks.removeAll {
                        true
                    }

                    redirectToLoginActivity(activity)
                }
                .addOnFailureListener { _ ->
                    activity.toast(activity.resources.getString(R.string.login_sign_out_failed))
                }
                .addOnCompleteListener { _ -> Log.d("Authentication", "signOut done!") }
    }

    fun receiveDecks(goodCallback: () -> Unit = {}, badCallback: () -> Unit = {}){
        Api.retrofit.getUserDecks(CurrentUser.getAccount().id!!).enqueue(object : Callback<BasicResponse<List<UserDeckModel>>> {
            override fun onResponse(call: Call<BasicResponse<List<UserDeckModel>>>?, response: Response<BasicResponse<List<UserDeckModel>>>?) {
                if (response != null && response.isSuccessful) {
                    decks = response.body()!!.data.toMutableList()
                    goodCallback()

                    Log.d("UserDecks", "Decks loaded")
                } else {
                    badCallback()
                    Log.d("Current User", "Can't get decks")
                }
            }

            override fun onFailure(call: Call<BasicResponse<List<UserDeckModel>>>?, t: Throwable?) {
                badCallback()
                Log.d("Current User", "Can't get decks")
            }

        })
    }

    private fun redirectToLoginActivity(activity: Activity){
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(intent)
    }
}