package me.manabu.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import me.manabu.activities.fragments.LessonCardFragment
import me.manabu.webapi.models.DeckLevelCardModel

class LessonSlideAdapter(fm: FragmentManager, private val cards: MutableList<DeckLevelCardModel>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt(LessonCardFragment.CARD_BUNDLE_ID, position)

        val lcf = LessonCardFragment()
        lcf.arguments = bundle

        return lcf
    }

    override fun getPageWidth(position: Int): Float {
        //Да, костыль. Зато красиво.
        //Кстати,  флоат умеет адекватно работать только с 6ти цифрами, дальше ошибочки в расчётах.
        return 0.999999f
    }

    override fun getCount(): Int {
        return cards.size
    }
}