package me.manabu.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*
import me.manabu.R
import me.manabu.modules.CurrentUser
import me.manabu.modules.GoogleAuth
import org.jetbrains.anko.act
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    companion object {
        const val RC_ACTIVITY_LOGIN = 100
        private const val RC_LOGIN_GOOGLE = 101
    }

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButtonGoogle.onClick {
            val signInIntent = GoogleAuth.getClientForActivity(act).signInIntent
            startActivityForResult(signInIntent, RC_LOGIN_GOOGLE)
         }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != RESULT_OK){
            Log.d("LoginActivity", "onActivityResult: result code check failed.")
            return
        }

        when (requestCode) {
            RC_LOGIN_GOOGLE -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            CurrentUser.set(account)
            setResult(RESULT_OK, intent)
            finish()
        } catch (e: ApiException) {
            toast("${getString(R.string.login_error_google_sign_in)}, #${e.statusCode}")
        }

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        toast(resources.getString(R.string.main_double_exit))

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 3000)
    }
}