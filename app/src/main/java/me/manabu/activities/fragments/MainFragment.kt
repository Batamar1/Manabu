package me.manabu.activities.fragments;

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.fragment_main.*
import me.manabu.R
import me.manabu.activities.LessonsActivity
import me.manabu.activities.MainActivity
import me.manabu.modules.CurrentUser


class MainFragment : Fragment(), View.OnClickListener, MaterialSpinner.OnItemSelectedListener<String> {

    private var currentDeck: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (CurrentUser.decks.isEmpty()) {
            (activity as MainActivity).changeFragment(NoDecksFragment())
            null
        } else {
            inflater.inflate(R.layout.fragment_main, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainButtonLessons.text = "Уроки"
        mainButtonLessons.setOnClickListener(this)

        mainButtonRepeats.text = "Повторения"
        mainButtonRepeats.setOnClickListener(this)

        val deckList = CurrentUser.decks.map { deckModel -> deckModel.name }.toMutableList()
        mainDeckChooser.setItems(deckList)
        mainDeckChooser.setOnItemSelectedListener(this)
        currentDeck = mainDeckChooser.selectedIndex
    }

    //OnChooserClicks
    override fun onItemSelected(view: MaterialSpinner?, position: Int, id: Long, item: String?) {
        currentDeck = position
    }

    //OnButtonClicks
    override fun onClick(v: View) = when (v) {
        mainButtonLessons -> {
            val lessonsIntent = Intent(v.context, LessonsActivity::class.java)
            lessonsIntent.putExtra(LessonsActivity.DECK_ID_INTENT, currentDeck)
            startActivity(lessonsIntent)
        }
        mainButtonRepeats -> {
//            val repeatsIntent = Intent(v.context, RepeatTypeable::class.java)
//            startActivity(repeatsIntent)
        }
        else -> { }
    }
}
