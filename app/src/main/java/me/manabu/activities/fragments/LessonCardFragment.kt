package me.manabu.activities.fragments;

import android.os.Bundle
import android.support.v4.app.Fragment;
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_card_lesson.*
import me.manabu.R
import me.manabu.activities.LessonsActivity

class LessonCardFragment : Fragment() {

    companion object {
        const val CARD_BUNDLE_ID = "CARD_BUNDLE_ID"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
                R.layout.fragment_card_lesson,
                container,
                false) as ViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments == null){
            Log.d("Lesson Card", "Can't load card")
        }
        val currentCard = (activity as LessonsActivity).availableLessons[arguments!!.getInt(CARD_BUNDLE_ID)]

        lessonCardHeaderText.text = currentCard.name
        lessonCardDestText.text = currentCard.description
    }
}
