package me.manabu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.manabu.R;
import me.manabu.activities.parents.BasicNavigationDrawerActivity;
import me.manabu.utils.AuthUtils;

import static me.manabu.activities.LoginActivity.RC_ACTIVITY_LOGIN;

public class MainActivity extends BasicNavigationDrawerActivity implements View.OnClickListener {

    private Button repeatsButton, lessonsButton;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setToolbar(R.id.main_toolbar_include);

        AuthUtils.init(this);

        //Not logged? -> LoginActivity
        if (!AuthUtils.isSignedIn(this)) {
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

    private void redirectIfNotSignedIn() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, RC_ACTIVITY_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_ACTIVITY_LOGIN && resultCode == RESULT_OK) {
            initDrawer();
        } else {
            Log.d("MainActivity", "onActivityResult failed.");
        }
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
        if(getDrawer().isDrawerOpen()){
            getDrawer().closeDrawer();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}