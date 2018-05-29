package me.manabu.activities.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_loading.*
import kotlinx.android.synthetic.main.fragment_nodecks.*
import me.manabu.R
import me.manabu.activities.MainActivity
import me.manabu.modules.NavigationDrawer
import org.jetbrains.anko.sdk25.coroutines.onClick

class NoDecksFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
            R.layout.fragment_nodecks,
            container,
            false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noDecksButton.onClick {
            (activity as MainActivity).changeFragment(DecksFragment())
            (activity as MainActivity).drawer!!.setSelection(NavigationDrawer.decksPage)
        }
    }
}
