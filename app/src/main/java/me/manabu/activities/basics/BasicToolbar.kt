package me.manabu.activities.basics

import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

abstract class BasicToolbar : AppCompatActivity() {

    lateinit var toolbar: Toolbar
        private set

    protected fun setToolbar(@IdRes toolbarId: Int) {
        toolbar = findViewById(toolbarId)
        setSupportActionBar(toolbar)
    }
}
