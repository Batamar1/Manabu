package me.manabu.activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;

import me.manabu.R;
import me.manabu.activities.parents.BasicToolbarActivity;
import me.manabu.utils.DpUtils;

public class RepeatTypeableActivity extends BasicToolbarActivity {

    boolean var;

    EditText et;
    volatile ImageButton bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat_typeable);
        setToolbar(R.id.repeat_typeable_toolbar_include);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //TODO: refactor
        et = (EditText) findViewById(R.id._reeditText);
        bt = (ImageButton) findViewById(R.id._rtb);

        setProperPadding();

        var = false;

        bt.setOnClickListener(v -> {

            Log.d("LOL", String.valueOf(bt.getWidth()));

            if(var){
                et.setBackground(getResources().getDrawable(R.drawable.edittext_review_bg_correct));
                var = false;
            } else {
                et.setBackground(getResources().getDrawable(R.drawable.edittext_review_bg_wrong));
                var = true;
            }
        });

    }

    private void setProperPadding(){
        int dp8 = (int) DpUtils.fromDpToPixels(this, 8);

        bt.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            et.setPadding(dp8, dp8, bt.getWidth() + dp8, dp8);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
