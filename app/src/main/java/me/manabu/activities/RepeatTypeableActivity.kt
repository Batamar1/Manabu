package me.manabu.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_repeat_typeable.*

import me.manabu.R
import me.manabu.activities.parents.BasicToolbarActivity
import me.manabu.utils.DpUtils

class RepeatTypeableActivity : BasicToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repeat_typeable)
        setToolbar(R.id.repeatTypeableToolbarInclude)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        setProperPadding()

        var flag = false

        repeatTypeableButtonNext.setOnClickListener { _ ->
            if (flag) {
                repeatTypeableEnterField.background = resources.getDrawable(R.drawable.edittext_review_bg_correct)
                flag = false
            } else {
                repeatTypeableEnterField.background = resources.getDrawable(R.drawable.edittext_review_bg_wrong)
                flag = true
            }
        }

    }

    private fun setProperPadding() {
        val dp8 = DpUtils.fromDpToPixels(this, 8f).toInt()

        repeatTypeableButtonNext.viewTreeObserver.addOnGlobalLayoutListener {
            repeatTypeableEnterField.setPadding(dp8, dp8, repeatTypeableButtonNext.width + dp8, dp8) }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
