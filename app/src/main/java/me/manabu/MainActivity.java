package me.manabu;

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

import com.mikepenz.materialdrawer.DrawerBuilder;

import me.manabu.Controllers.LoginController;

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

        redirectIfLogged();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(toolbar);

        lessonsButton = (Button) findViewById(R.id.button_main_lessons);
        repeatsButton = (Button) findViewById(R.id.button_main_repeats);

        //lessonsButton.setText("Уроки");
        //repeatsButton.setText("Повторения");

        new DrawerBuilder().withActivity(this).build();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            //TODO: В ресурсы
            Toast.makeText(this, "Нажмите ещё раз чтобы выйти :)", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 3000);
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
