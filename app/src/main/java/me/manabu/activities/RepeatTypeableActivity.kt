package me.manabu.activities

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_repeat_typeable.*
import me.manabu.R
import me.manabu.activities.parents.BasicToolbarActivity
import me.manabu.utils.DpUtils

class RepeatTypeableActivity : BasicToolbarActivity() {


    data class ReviewItem(var desc: String, var answer: String)

    private lateinit var currentItem: ReviewItem
    private lateinit var iterator: MutableIterator<ReviewItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repeat_typeable)
        setToolbar(R.id.repeatTypeableToolbarInclude)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        setProperPadding()

        var testItemsList = mutableListOf<ReviewItem>(
                ReviewItem("Test1", "test1"),
                ReviewItem("Test2", "test2"),
                ReviewItem("Test3", "test3")
        )

        //При условии, что есть айтемы на проверку

        iterator = testItemsList.iterator()
        currentItem = iterator.next()

        placeItem()

        repeatTypeableButtonNext.setOnClickListener { _ ->
            if (repeatTypeableEnterField.text.toString() == currentItem.answer) {
                repeatTypeableEnterField.background = resources.getDrawable(R.drawable.edittext_review_bg_correct, theme)
                Handler().postDelayed({
                    if(iterator.hasNext()){
                        currentItem = iterator.next()
                        repeatTypeableEnterField.text.clear()
                        repeatTypeableEnterField.background = resources.getDrawable(R.drawable.edittext_review_bg, theme)
                        placeItem()
                    } else {
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.toggleSoftInput(0, 0)

                        repeatTypeableDescription.text = "Поздравляем, вы всё!"
                        repeatTypeableEnterField.visibility = GONE
                        repeatTypeableButtonNext.visibility = GONE
                    }
                }, 2000)
            } else {
                repeatTypeableEnterField.background = resources.getDrawable(R.drawable.edittext_review_bg_wrong, theme)
            }
        }

    }

    private fun placeItem(){
        repeatTypeableDescription.text = currentItem.desc
    }

    private fun setProperPadding() {
        val dp8 = DpUtils.fromDpToPixels(this, 8).toInt()

        repeatTypeableButtonNext.viewTreeObserver.addOnGlobalLayoutListener {
            repeatTypeableEnterField.setPadding(dp8, dp8, repeatTypeableButtonNext.width + dp8, dp8) }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
