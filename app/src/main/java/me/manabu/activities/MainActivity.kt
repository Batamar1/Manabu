package me.manabu.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.mikepenz.materialdrawer.Drawer
import me.manabu.R
import me.manabu.activities.LoginActivity.Companion.RC_ACTIVITY_LOGIN
import me.manabu.activities.fragments.MainFragment
import me.manabu.modules.Authentication
import me.manabu.modules.NavigationDrawer

class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    lateinit var toolbar: Toolbar
    lateinit var drawerVar: Drawer
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.mainToolbarInclude)
        setSupportActionBar(toolbar)

        Authentication.init(this)

        //Not logged? -> LoginActivity
        if (!Authentication.isSignedIn()) {
            redirectIfNotSignedIn()
        } else {
            initDrawer()
        }

        changeFragment(MainFragment())

//        https://github.com/codepath/android_guides/wiki/Creating-and-Using-Fragments
    }

    fun changeFragment(fragment: Fragment) {
        if (currentFragment != null && fragment == currentFragment) {
            return
        } else {
            currentFragment = fragment
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentInclude, currentFragment)
                .commit()
    }

    private fun redirectIfNotSignedIn() {
        val i = Intent(this, LoginActivity::class.java)
        startActivityForResult(i, RC_ACTIVITY_LOGIN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_ACTIVITY_LOGIN && resultCode == RESULT_OK) {
            Log.d("MainActivity", "onActivityResult NORM.")
            initDrawer()
        } else {
            Log.d("MainActivity", "onActivityResult failed.")
        }
    }

    override fun onBackPressed() {
        if (drawerVar.isDrawerOpen) {
            drawerVar.closeDrawer()
            return
        }

        if (isTaskRoot) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, resources.getString(R.string.main_double_exit), Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 3000)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun initDrawer() {
        //drawer = NavigationDrawer(this, toolbar).build()
        drawerVar = NavigationDrawer.build(this)
        NavigationDrawer.loadUserBackground(this)
    }
}