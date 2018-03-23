package me.manabu.activities.basics

import com.mikepenz.materialdrawer.Drawer

import me.manabu.modules.NavigationDrawer

abstract class BasicNavigationDrawerActivity : BasicToolbarActivity() {

    lateinit var drawer: Drawer
        private set

    protected fun initDrawer() {
        drawer = NavigationDrawer(this, toolbar).drawer
    }
}