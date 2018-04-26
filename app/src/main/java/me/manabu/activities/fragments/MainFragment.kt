package me.manabu.activities.fragments;

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import me.manabu.R
import me.manabu.activities.LessonCardActivity
import me.manabu.activities.RepeatTypeable

class MainFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
                R.layout.fragment_main,
                container,
                false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainButtonLessons.text = "Уроки"
        mainButtonLessons.setOnClickListener(this)

        mainButtonRepeats.text = "Повторения"
        mainButtonRepeats.setOnClickListener(this)
    }

    override fun onClick(v: View) = when (v) {
        mainButtonLessons -> {
            val lessonsIntent = Intent(v.context, LessonCardActivity::class.java)
            lessonsIntent.putExtra("deckId", 2)
            startActivity(lessonsIntent)
        }
        mainButtonRepeats -> {
            val repeatsIntent = Intent(v.context, RepeatTypeable::class.java)
            startActivity(repeatsIntent)
        }
        else -> {}
    }
}
