package me.manabu.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import kotlinx.android.synthetic.main.activity_review.*
import me.manabu.R
import me.manabu.models.ReviewCardModel
import me.manabu.webapi.models.DeckLevelCardModel
import org.jetbrains.anko.sdk25.coroutines.onClick

class ReviewActivity : AppCompatActivity() {

    companion object {
        const val FROM_LESSONS = "FROM_LESSONS"
    }

    private lateinit var toolbar: Toolbar

    private lateinit var availableReviews: MutableList<ReviewCardModel>
    private var currentItem: ReviewCardModel? = null
    private var iterator: MutableIterator<ReviewCardModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        setupToolbar()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        loadRepeatItems()

        //При условии, что есть айтемы на проверку

//        iterator = testItemsList.iterator()
//        currentItem = iterator.next()

        reviewProgressBar.max = 100
        reviewProgressBar.progress = 0

        reviewButtonCheck.onClick {
            reviewProgressBar.progress += 5
//            if (reviewTypeField.text.toString() == currentItem.answer) {
//                reviewTypeField.background = resources.getDrawable(R.drawable.edittext_review_bg_correct, theme)
//                Handler().postDelayed({
//                    if(iterator.hasNext()){
//                        currentItem = iterator.next()
//                        reviewTypeField.text.clear()
//                        reviewTypeField.background = resources.getDrawable(R.drawable.edittext_review_bg, theme)
//                        placeItem()
//                    } else {
//                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                        imm.toggleSoftInput(0, 0)
//
//                        reviewDescription.text = "Поздравляем, вы всё!"
//                        reviewTypeField.visibility = GONE
//                        reviewButtonCheck.visibility = GONE
//                    }
//                }, 2000)
//            } else {
//                reviewTypeField.background = resources.getDrawable(R.drawable.edittext_review_bg_wrong, theme)
//            }
        }

    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.reviewToolbarInclude)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.reviews_title)
        //supportActionBar!!.subtitle = currentDeck.name
    }

    private fun loadRepeatItems(){
        val fromLessons = intent.getBooleanExtra(FROM_LESSONS, false)
        if(fromLessons){
//            availableReviews.convert(LessonsActivity.availableLessons)
            TODO("Разобраться как передавать все айдишники")
            //idDeck, idLevelm id Card
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

