package me.manabu.activities.parents;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import me.manabu.utils.NavigationDrawer;

@SuppressLint("Registered")
public class BasicNavigationDrawerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initDrawer() {
        drawer = new NavigationDrawer(this, toolbar).getDrawer();
    }

    public Drawer getDrawer() {
        return drawer;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    protected void setToolbar(@IdRes int toolbarId) {
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);
    }
}