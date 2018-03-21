package me.manabu.activities

import android.os.Bundle
import me.manabu.R
import me.manabu.activities.basics.BasicToolbarActivity

class RepeatSwipeActivity : BasicToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repeat_swipe_card)
        setToolbar(R.id.repeat_swipe_toolbar_include)

//        val anim = AnimationUtils.loadAnimation(this, R.anim.repeat_swipe_left)
//
//        val swipe = findViewById(R.id.swipeTest) as ImageButton
//                swipe.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        view.startAnimation(anim);
//                    }
//                });
//                swipe.startAnimation(anim);
//                swipe.setOnTouchListener((v, event) -> {
//                    ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
//                    String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
//                    ClipData data = new ClipData("LUL", mimeTypes, item);
//
//                    v.startDrag(
//                            data,
//                            null,
//                            v,
//                            0
//                    );
//                    return true;
//                });
//                swipe.setOnDragListener(new View.OnDragListener() {
//                    @Override
//                    public boolean onDrag(View v, DragEvent event) {
//                        switch (event.getAction()){
//                            case ACTION_DRAG_ENTERED:
//                                Log.d("move", "yay");
//                        }
//                        Log.d("move", String.valueOf(event.getX()));
//                        return true;
//                    }
//                });
    }
}