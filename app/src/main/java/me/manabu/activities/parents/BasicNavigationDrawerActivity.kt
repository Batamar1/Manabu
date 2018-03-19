package me.manabu.activities.parents

import com.mikepenz.materialdrawer.Drawer

import me.manabu.utils.NavigationDrawer

abstract class BasicNavigationDrawerActivity : BasicToolbarActivity() {

    var drawer: Drawer? = null
        private set

    protected fun initDrawer() {
        drawer = NavigationDrawer(this, toolbar).drawer
    }
}