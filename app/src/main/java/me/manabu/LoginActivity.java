package me.manabu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import me.manabu.Controllers.LoginController;
import me.manabu.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button facebookButton = (Button)findViewById(R.id.button_login_facebook);
        Button googleButton = (Button)findViewById(R.id.button_login_google);

        //Временно пока логина нет.
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                LoginController.logIn((Activity) v.getContext());
                startActivity(intent);
                finish();
            }
        };

        facebookButton.setOnClickListener(listener);
        googleButton.setOnClickListener(listener);
    }

}