package me.manabu.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.manabu.R;
import me.manabu.adapters.DecksDeckAdapter;
import me.manabu.adapters.LessonItemAdapter;
import me.manabu.adapters.models.DecksDeckModel;
import me.manabu.adapters.models.LessonItemModel;
import me.manabu.api.models.DeckModel;
import me.manabu.helpers.NavigationDrawerHelper;
import me.manabu.helpers.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DecksActivity extends AppCompatActivity {

    private ListView decksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_card);


        decksList = (ListView) findViewById(R.id.decks_listview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.decks_toolbar_include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerHelper.getDrawer(this, toolbar);

        ArrayList<DecksDeckModel> infoList = new ArrayList<>();

        //TODO: To strings
        infoList.add(new DecksDeckModel("1000 слов русского", "Языки - Русский", 1000));
        infoList.add(new DecksDeckModel("10000 слов английского", "Языки - Английский", 10000));
        infoList.add(new DecksDeckModel("Как запикапить тян", "Жизнь", 57));

        DecksDeckAdapter adapter = new DecksDeckAdapter(this, infoList);
        decksList.setAdapter(adapter);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}