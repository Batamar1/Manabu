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

import me.manabu.R
import me.manabu.modules.Authentication

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val RC_ACTIVITY_LOGIN = 100
        private const val RC_LOGIN_GOOGLE = 101
    }

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signInButton = findViewById(R.id.loginButtonGoogle) as Button
        signInButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.loginButtonGoogle -> {
                val signInIntent = Authentication.getClientForActivity(this).signInIntent
                startActivityForResult(signInIntent, RC_LOGIN_GOOGLE)
            }
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
            Authentication.signIn(account)
            setResult(RESULT_OK, intent)
            finish()
        } catch (e: ApiException) {
            Toast.makeText(this,
                    "${getString(R.string.login_error_google_sign_in)}, #${e.statusCode}",
                    Toast.LENGTH_LONG).show()
        }

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, resources.getString(R.string.main_double_exit), Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 3000)
    }
}