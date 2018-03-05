package me.manabu.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import me.manabu.R;

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


    }

    private void loadDataIntoFragments(){
//        Intent intent = getIntent();
//        RetrofitHelper.retrofit.getLessonsFromDeck(intent.getIntExtra("deckId", 0)).enqueue(new Callback<List<DeckModel>>() {
//            @Override
//            public void onResponse(Call<List<DeckModel>> call, Response<List<DeckModel>> response) {
//                Log.d("LessonCardActivity", "Deck ID - " + String.valueOf(intent.getIntExtra("deckId", 0)));
//
//                if(response.isSuccessful()){
//                    DeckModel deck = response.body().get(0);
//                    getSupportActionBar().setTitle(deck.getName());
//                    itemName.setText(deck.getId1().getTarget());
//                    //descriptionText.setText(deck.getId1().getDescription());
//                    Log.d("LessonCardActivity", "Got deck!");
//                } else {
//                    Log.d("LessonCardActivity", "Can't get deck for some reason.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DeckModel>> call, Throwable t) {
//                Log.d("LessonCardActivity", "Get deck failed - " + t.toString());
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
