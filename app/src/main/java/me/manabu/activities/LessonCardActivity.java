package me.manabu.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import me.manabu.R;
import me.manabu.activities.fragments.CardDescriptionFragment;
import me.manabu.adapters.FragmentAdapter;
import me.manabu.api.Models.DeckModel;
import me.manabu.helpers.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonCardActivity extends AppCompatActivity {

    private TextView itemName;
    private TextView descriptionText;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_card);

        itemName = (TextView) findViewById(R.id.itemName);
        //descriptionText = (TextView) findViewById(R.id.descriptionText);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        loadDataIntoFragments();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.lesson_card_tab_container);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new CardDescriptionFragment(), "People");
        adapter.addFragment(new CardDescriptionFragment(), "People");
        viewPager.setAdapter(adapter);
    }

    private void loadDataIntoFragments(){
        Intent intent = getIntent();
        RetrofitHelper.retrofit.getLessonsFromDeck(intent.getIntExtra("deckId", 0)).enqueue(new Callback<List<DeckModel>>() {
            @Override
            public void onResponse(Call<List<DeckModel>> call, Response<List<DeckModel>> response) {
                Log.d("LessonCardActivity", "Deck ID - " + String.valueOf(intent.getIntExtra("deckId", 0)));

                if(response.isSuccessful()){
                    DeckModel deck = response.body().get(0);
                    getSupportActionBar().setTitle(deck.getName());
                    itemName.setText(deck.getId1().getTarget());
                    //descriptionText.setText(deck.getId1().getDescription());
                    Log.d("LessonCardActivity", "Got deck!");
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
