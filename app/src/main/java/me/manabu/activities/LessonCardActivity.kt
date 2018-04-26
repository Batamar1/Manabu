package me.manabu.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_lessons.*
import me.manabu.R
import me.manabu.activities.fragments.LessonCardFragment

class LessonCardActivity : FragmentActivity()  {

    //private var DECK_ID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)
        //setToolbar(R.id.lessonToolbarInclude)

//        val intent = intent
//        DECK_ID = intent.getIntExtra("deckId", 0)

        //loadData()

        ////////////////

        val test = arrayOf("rofl1", "rofl2", "rofl3")

        val pager = lessonCardsPager
        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager, test)
        pager.adapter = pagerAdapter



    }

    private class ScreenSlidePagerAdapter(fm: FragmentManager, var test: Array<String>) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return LessonCardFragment()
        }

        override fun getCount(): Int {
            return test.size
        }
    }

//
//    private fun loadData() {
//        Api.retrofit.getLessonsFromDeck(DECK_ID).enqueue(object : Callback<List<DeckModel>> {
//            override fun onResponse(call: Call<List<DeckModel>>, response: Response<List<DeckModel>>) {
//                if (response.isSuccessful) {
//                    Log.d("LessonCardActivity", "Got deck #$DECK_ID!")
//
//                    val deck = response.body()!![0]
//                    fillData(deck)
//                } else {
//                    Log.d("LessonCardActivity", "Can't get deck for some reason.")
//                }
//            }
//
//            override fun onFailure(call: Call<List<DeckModel>>, t: Throwable) {
//                Log.d("LessonCardActivity", "Get deck failed - " + t.toString())
//            }
//        })
//    }
//
//    private fun fillData(deck: DeckModel) {
//        supportActionBar!!.title = deck.name
//        lessonItemName.text = deck.id1.target
//        val infoList = ArrayList<LessonItemModel>()
//
//        //TODO: To strings
//        infoList.add(LessonItemModel("Desc", deck.id1.description))
//        infoList.add(LessonItemModel("Mnemonic", deck.id1.mnemonic))
//
//        val adapter = LessonItemAdapter(this, infoList)
//        lessonListview.adapter = adapter
//        //descriptionText.setText(deck.getId1().getDescription());
//    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
}