package me.manabu.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_decks.*
import me.manabu.R
import me.manabu.activities.basics.BasicNavigationDrawer
import me.manabu.adapters.DecksDeckAdapter
import me.manabu.adapters.models.DecksDeckModel
import java.util.*

class DecksActivity : BasicNavigationDrawer() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decks)
        setToolbar(R.id.decksToolbarInclude)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        initDrawer()

        val infoList = ArrayList<DecksDeckModel>()

        //TODO: To strings
        infoList.add(DecksDeckModel("1000 слов русского", "Языки - Русский", 1000))
        infoList.add(DecksDeckModel("10000 слов английского", "Языки - Английский", 10000))
        infoList.add(DecksDeckModel("C++ за 21 день", "Жизнь", 100500))

        val adapter = DecksDeckAdapter(this, infoList)
        decksListOfDecks.adapter = adapter

        decksFabCreate.setOnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //        MenuInflater inflater = getMenuInflater();
        //        inflater.inflate(R.menu.menu_decks, menu);
        //        menu.add(R.string.app_name).setIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_plus).sizeDp(16)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //        switch (item.getItemId()) {
        //            case MENU_ITEM_ITEM1:
        //                clearArray();
        //                return true;
        //
        //            default:
        //                return false;
        //        }
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}