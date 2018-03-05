package me.manabu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import me.manabu.R;
import me.manabu.helpers.AuthHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int RC_ACTIVITY_LOGIN = 100;
    private static final int RC_LOGIN_GOOGLE = 101;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signInButton = (Button) findViewById(R.id.login_button_google);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_google:
                Intent signInIntent = AuthHelper.getClientForActivity(this).getSignInIntent();
                startActivityForResult(signInIntent, RC_LOGIN_GOOGLE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK) {
            Log.d("LoginActivity", "onActivityResult: failed");
            return;
        }

        switch (requestCode) {
            case RC_LOGIN_GOOGLE:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            AuthHelper.signIn(account);
            Log.d("LoginActivity", "handleSignInResult done.");
            setResult(RESULT_OK);
            finish();
        } catch (ApiException e) {
            Toast.makeText(this,
                    getString(R.string.login_google_sign_in_error) + ", #" + e.getStatusCode(),
                    Toast.LENGTH_LONG).show();
            Log.d("LoginActivity", "handleSignInResult failed: " + e.getStatusCode());
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.main_double_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 3000);
    }
}