package me.manabu.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import com.mikepenz.materialdrawer.Drawer
import me.manabu.R
import me.manabu.activities.LoginActivity.Companion.RC_ACTIVITY_LOGIN
import me.manabu.activities.fragments.LoadingFragment
import me.manabu.activities.fragments.MainFragment
import me.manabu.activities.fragments.NoDecksFragment
import me.manabu.modules.CurrentUser
import me.manabu.modules.NavigationDrawer
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    var drawer: Drawer? = null
    lateinit var toolbar: Toolbar

    private var doubleBackToExitPressedOnce = false
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.mainToolbarInclude)
        setSupportActionBar(toolbar)

        CurrentUser.loadInfo(this)

        //Not logged? -> LoginActivity
        if (!CurrentUser.isSignedIn()) {
            redirectIfNotSignedIn()
        } else {
            loadDataForSignedUser()
        }

        changeFragment(LoadingFragment())
    }

    fun changeFragment(fragment: Fragment) {
        currentFragment?.let {
            if (fragment::class == it::class) {
                return
            }
        }

        currentFragment = fragment

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
            Log.d("MainActivity", "onActivityResult ok")
            loadDataForSignedUser()
        } else {
            Log.d("MainActivity", "onActivityResult failed")
        }
    }

    override fun onBackPressed() {
        drawer?.let {
            if (it.isDrawerOpen) {
                it.closeDrawer()
                return
            }
        }

        if (isTaskRoot) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            toast(resources.getString(R.string.main_double_exit))

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

    fun loadDataForSignedUser() {
        CurrentUser.receiveDecks({
            if (drawer == null) {
                initDrawer()
            }

            if(CurrentUser.decks.isEmpty()){
                changeFragment(NoDecksFragment())
            } else {
                changeFragment(MainFragment())
            }
        }, {
            (currentFragment as LoadingFragment).showError()
        })
    }

    private fun initDrawer() {
        drawer = NavigationDrawer.build(this)
        NavigationDrawer.loadUserBackground(this)
    }
}