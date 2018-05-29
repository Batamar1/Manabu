package me.manabu.activities.fragments

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import me.manabu.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
