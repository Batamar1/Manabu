package me.manabu.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import kotlinx.android.synthetic.main.activity_lessons.*
import me.manabu.R
import me.manabu.adapters.LessonSlideAdapter
import android.view.MenuItem
import me.manabu.modules.CurrentUser

class LessonsActivity : AppCompatActivity()  {

    companion object {
        const val DECK_ID = "LESSONS_ACTIVITY_DECK_ID"
    }
    private var currentDeckId: Int? = null
    private val _cards = 5

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        toolbar = findViewById(R.id.lessonToolbarInclude)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        currentDeckId = intent.getIntExtra(DECK_ID, -1)

        Log.d("OK", "WE GOT DECK ${CurrentUser.decks[currentDeckId!!].id} - ${CurrentUser.decks[currentDeckId!!].name}")

        if(currentDeckId == -1){
            finish()
        }

        val test = arrayOf("rofl1", "rofl2", "rofl3")

        val pager = lessonCardsPager
        val pagerAdapter = LessonSlideAdapter(supportFragmentManager, test)
        pager.adapter = pagerAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}