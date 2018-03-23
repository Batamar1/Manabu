package me.manabu.activities

import android.os.Bundle

import me.manabu.R
import me.manabu.activities.basics.BasicToolbarActivity

class SettingsActivity : BasicToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}
