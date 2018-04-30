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
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CurrentUser {

    private var account: GoogleSignInAccount? = null
    var decks: MutableList<DeckModel> = mutableListOf()
    var decksLoaded = false

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
        val client = getClient(activity)
        client.signOut()
                .addOnSuccessListener { _ ->
                    account = null

                    decksLoaded = false
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

    fun receiveDecks(goodCallback: () -> Unit, badCallback: () -> Unit){
        Api.retrofit.getUserDecks(CurrentUser.getAccount().id!!).enqueue(object : Callback<BasicResponse<List<DeckModel>>> {
            override fun onResponse(call: Call<BasicResponse<List<DeckModel>>>?, response: Response<BasicResponse<List<DeckModel>>>?) {
                if (response != null && response.isSuccessful) {
                    decksLoaded = true
                    decks = response.body()!!.data.toMutableList()
                    goodCallback()

                    Log.d("UserDecks", "Decks loaded")
                }
            }

            override fun onFailure(call: Call<BasicResponse<List<DeckModel>>>?, t: Throwable?) {
                badCallback()
                Log.d("Current User", "Can't get decks")
            }

        })
    }

    private fun getClient(activity: Activity): GoogleSignInClient {
        return GoogleAuth.getClientForActivity(activity)
    }

    private fun redirectToLoginActivity(activity: Activity){
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(intent)
    }
}