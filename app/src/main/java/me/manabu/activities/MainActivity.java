package me.manabu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;

import me.manabu.R;
import me.manabu.helpers.AuthHelper;
import me.manabu.helpers.NavigationDrawerHelper;

import static me.manabu.activities.LoginActivity.RC_ACTIVITY_LOGIN;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button repeatsButton, lessonsButton;
    private Toolbar toolbar;
    private Drawer drawer;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar_include);
        setSupportActionBar(toolbar);

        AuthHelper.init(this);

        //Not logged? -> LoginActivity
        if (!AuthHelper.isSignedIn(this)) {
            redirectIfNotSignedIn();
        } else {
            initDrawer();
        }

        lessonsButton = (Button) findViewById(R.id.main_button_lessons);
        repeatsButton = (Button) findViewById(R.id.main_button_repeats);

        lessonsButton.setText("Уроки");
        repeatsButton.setText("Повторения");

        lessonsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_button_lessons:
                Intent intent = new Intent(v.getContext(), LessonCardActivity.class);
                intent.putExtra("deckId", 2);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen()){
            drawer.closeDrawer();
            return;
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.main_double_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 3000);
    }

    private void redirectIfNotSignedIn() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, RC_ACTIVITY_LOGIN);

        Log.d("MainActivity", "redirectIfNotSignedIn.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_ACTIVITY_LOGIN && resultCode == RESULT_OK) {
            initDrawer();
            Log.d("MainActivity", "onActivityResult ok.");
        } else {
            Log.d("MainActivity", "onActivityResult failed.");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initDrawer(){
        NavigationDrawerHelper.init(this);
        drawer = NavigationDrawerHelper.getDrawer(this, toolbar);
    }
}