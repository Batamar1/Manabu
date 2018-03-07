package me.manabu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import me.manabu.R;
import me.manabu.adapters.LessonItemAdapter;
import me.manabu.adapters.models.LessonItemModel;
import me.manabu.api.models.DeckModel;
import me.manabu.helpers.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonCardActivity extends AppCompatActivity {

    private int DECK_ID;

    private TextView itemName;
    private ListView listViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_card);

        itemName = (TextView) findViewById(R.id.lesson_item_name);
        listViewInfo = (ListView) findViewById(R.id.lesson_listview_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.lesson_toolbar_include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        DECK_ID = intent.getIntExtra("deckId", 0);

        loadData();
    }

    private void loadData(){
        RetrofitHelper.retrofit.getLessonsFromDeck(DECK_ID).enqueue(new Callback<List<DeckModel>>() {
            @Override
            public void onResponse(Call<List<DeckModel>> call, Response<List<DeckModel>> response) {
                if(response.isSuccessful()){
                    Log.d("LessonCardActivity", "Got deck #" + DECK_ID + "!");

                    DeckModel deck = response.body().get(0);
                    fillData(deck);
                } else {
                    Log.d("LessonCardActivity", "Can't get deck for some reason.");
                }
            }

            @Override
            public void onFailure(Call<List<DeckModel>> call, Throwable t) {
                Log.d("LessonCardActivity", "Get deck failed - " + t.toString());
            }
        });
    }

    private void fillData(DeckModel deck){
        getSupportActionBar().setTitle(deck.getName());
        itemName.setText(deck.getId1().getTarget());
        ArrayList<LessonItemModel> infoList = new ArrayList<>();

        //TODO: To strings
        infoList.add(new LessonItemModel("Desc", deck.getId1().getDescription()));
        infoList.add(new LessonItemModel("Mnemonic", deck.getId1().getMnemonic()));

        LessonItemAdapter adapter = new LessonItemAdapter(this, infoList);

        listViewInfo.setAdapter(adapter);
        //descriptionText.setText(deck.getId1().getDescription());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}