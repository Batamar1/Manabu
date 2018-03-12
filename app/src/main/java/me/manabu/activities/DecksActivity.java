package me.manabu.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import me.manabu.R;
import me.manabu.activities.parents.BasicNavigationDrawerActivity;
import me.manabu.adapters.DecksDeckAdapter;
import me.manabu.adapters.models.DecksDeckModel;

public class DecksActivity extends BasicNavigationDrawerActivity {

    private ListView decksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks);
        setToolbar(R.id.decks_toolbar_include);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initDrawer();

        ArrayList<DecksDeckModel> infoList = new ArrayList<>();

        //TODO: To strings
        infoList.add(new DecksDeckModel("1000 слов русского", "Языки - Русский", 1000));
        infoList.add(new DecksDeckModel("10000 слов английского", "Языки - Английский", 10000));
        infoList.add(new DecksDeckModel("C++ за 21 день", "Жизнь", 100500));

        decksList = (ListView) findViewById(R.id.decks_listview);
        DecksDeckAdapter adapter = new DecksDeckAdapter(this, infoList);
        decksList.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.decks_fab_create);
        fab.setOnClickListener(view -> Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_decks, menu);
//        menu.add(R.string.app_name).setIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_plus).sizeDp(16)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case MENU_ITEM_ITEM1:
//                clearArray();
//                return true;
//
//            default:
//                return false;
//        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}