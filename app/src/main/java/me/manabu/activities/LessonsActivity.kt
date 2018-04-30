package me.manabu.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_lessons.*
import me.manabu.R
import me.manabu.adapters.LessonSlideAdapter
import android.content.Intent
import android.view.MenuItem


class LessonsActivity : AppCompatActivity()  {

    //private var DECK_ID: Int = 0
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        toolbar = findViewById(R.id.lessonToolbarInclude)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val test = arrayOf("rofl1", "rofl2", "rofl3")

        val pager = lessonCardsPager
        val pagerAdapter = LessonSlideAdapter(supportFragmentManager, test)
        pager.adapter = pagerAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}