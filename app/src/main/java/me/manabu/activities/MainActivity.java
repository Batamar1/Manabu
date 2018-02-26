package me.manabu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import me.manabu.R;
import me.manabu.controllers.LoginController;
import me.manabu.helpers.NavigationDrawerHelper;

public class MainActivity extends AppCompatActivity {

    private Button repeatsButton, lessonsButton;

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Not logged? -> LoginActivity
        redirectIfLogged();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar_include);
        setSupportActionBar(toolbar);
        NavigationDrawerHelper.init(this, toolbar);

        lessonsButton = (Button) findViewById(R.id.button_main_lessons);
        repeatsButton = (Button) findViewById(R.id.button_main_repeats);

        lessonsButton.setText("Уроки");
        repeatsButton.setText("Повторения");


        lessonsButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), LessonCardActivity.class);
            intent.putExtra("deckId", 2);
            startActivity(intent);
        });

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

    private void redirectIfLogged() {
        if (!LoginController.isLogged(this)) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
