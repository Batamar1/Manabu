package me.manabu.activities.parents;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

abstract public class BasicToolbarActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    protected void setToolbar(@IdRes int toolbarId) {
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
