package me.manabu.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import me.manabu.activities.fragments.LessonCardFragment

class LessonSlideAdapter(fm: FragmentManager, var test: Array<String>) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return LessonCardFragment()
    }

    override fun getCount(): Int {
        return test.size
    }
}