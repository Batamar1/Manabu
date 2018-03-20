package me.manabu.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

import me.manabu.R
import me.manabu.activities.LoginActivity.Companion.RC_ACTIVITY_LOGIN
import me.manabu.activities.parents.BasicNavigationDrawerActivity
import me.manabu.modules.Authentication

class MainActivity : BasicNavigationDrawerActivity(), View.OnClickListener {

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar(R.id.mainToolbarInclude)

        Authentication.init(this)

        //Not logged? -> LoginActivity
        if (!Authentication.isSignedIn()) {
            redirectIfNotSignedIn()
        } else {
            initDrawer()
        }

        mainButtonLessons.text = "Уроки"
        mainButtonLessons.setOnClickListener(this)

        mainButtonRepeats.text = "Повторения"
        mainButtonRepeats.setOnClickListener(this)
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

    override fun onClick(v: View) = when (v) {
        mainButtonLessons -> {
            val lessonsIntent = Intent(v.context, LessonCardActivity::class.java)
            lessonsIntent.putExtra("deckId", 2)
            startActivity(lessonsIntent)
        }
        mainButtonRepeats -> {
            val repeatsIntent = Intent(v.context, RepeatTypeableActivity::class.java)
            startActivity(repeatsIntent)
        }
        else -> {}
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen) {
            drawer.closeDrawer()
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
}