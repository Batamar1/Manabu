package me.manabu.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import kotlinx.android.synthetic.main.activity_lessons.*
import me.manabu.R
import me.manabu.adapters.LessonSlideAdapter
import android.view.MenuItem
import kotlinx.android.synthetic.main.item_decks_deck.*
import me.manabu.modules.CurrentUser
import me.manabu.webapi.Api
import me.manabu.webapi.models.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LessonsActivity : AppCompatActivity() {

    companion object {
        const val DECK_ID_INTENT = "LESSONS_ACTIVITY_DECK_ID"
    }

    private lateinit var currentDeck: UserDeckModel
    lateinit var availableLessons: MutableList<DeckLevelCardModel>

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        setCurrentDeck()
        setupToolbar()
        receiveAvailableLessons({
            setupPager()
        })
    }

    private fun receiveAvailableLessons(callback: () -> Unit) {
        Api.retrofit.getAvailableLessons(currentDeck.id).enqueue(object : Callback<BasicResponse<List<DeckLevelModel>>> {
            override fun onResponse(call: Call<BasicResponse<List<DeckLevelModel>>>?, response: Response<BasicResponse<List<DeckLevelModel>>>?) {
                if (response != null && response.isSuccessful) {
                    availableLessons = mutableListOf()
                    response.body()!!.data.forEach {
                        availableLessons.addAll(it.cards)
                    }
                    callback()
                } else {
                    showErrorAndFinish()
                }
            }

            override fun onFailure(call: Call<BasicResponse<List<DeckLevelModel>>>?, t: Throwable?) {
                showErrorAndFinish()
            }

        })
    }

    private fun setCurrentDeck() {
        val intent = intent
        val currentDeckId = intent.getIntExtra(DECK_ID_INTENT, -1)
        if (currentDeckId == -1) {
            showErrorAndFinish()
        } else {
            currentDeck = CurrentUser.decks[currentDeckId]

            Log.d("LessonActivity", "Current deck is ${currentDeck.name} [${currentDeck.id}]")
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.lessonToolbarInclude)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = currentDeck.name
    }

    private fun showErrorAndFinish() {
        toast(R.string.lesson_error_get_deck)
        Log.d("LessonActivity", "Some error happened. Finishing activity.")
        finish()
    }

    private fun setupPager(){
        val pagerAdapter = LessonSlideAdapter(supportFragmentManager, availableLessons)
        lessonCardsPager.adapter = pagerAdapter
        lessonCardsPager.pageMargin = -64

        val showOn = availableLessons.size - 1
        lessonCardsPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                if(lessonsFabToReview.isShown && position != showOn )
                    lessonsFabToReview.hide()
                else if(position == showOn){
                    lessonsFabToReview.show()
                    lessonsFabToReview.
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {


            }
            override fun onPageScrollStateChanged(state: Int) {

            }
        })
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