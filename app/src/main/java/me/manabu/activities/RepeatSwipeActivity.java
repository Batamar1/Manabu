package me.manabu.activities;

import android.content.ClipData;
import android.content.ClipDescription;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import me.manabu.R;
import me.manabu.activities.parents.BasicRepeatActivity;
import me.manabu.utils.OnSwipeTouchListener;

import static android.view.DragEvent.ACTION_DRAG_ENTERED;

public class RepeatSwipeActivity extends BasicRepeatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat_swipe_card);
        setToolbar(R.id.repeat_swipe_toolbar_include);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.repeat_swipe_left);

        ImageButton swipe = (ImageButton) findViewById(R.id.swipeTest);
//        swipe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.startAnimation(anim);
//            }
//        });
//        swipe.startAnimation(anim);
//        swipe.setOnTouchListener((v, event) -> {
//            ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
//            String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
//            ClipData data = new ClipData("LUL", mimeTypes, item);
//
//            v.startDrag(
//                    data,
//                    null,
//                    v,
//                    0
//            );
//            return true;
//        });
//        swipe.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                switch (event.getAction()){
//                    case ACTION_DRAG_ENTERED:
//                        Log.d("move", "yay");
//                }
//                Log.d("move", String.valueOf(event.getX()));
//                return true;
//            }
//        });
    }
}