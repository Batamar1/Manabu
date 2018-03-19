package me.manabu.activities.parents

import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

abstract class BasicToolbarActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
        private set

    protected fun setToolbar(@IdRes toolbarId: Int) {
        toolbar = findViewById(toolbarId) as Toolbar
        setSupportActionBar(toolbar)
    }
}
