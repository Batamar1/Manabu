package me.manabu.utils;

import android.content.Context;
import android.util.TypedValue;

public class DpUtils {
    public static float fromDpToPixels(Context context, float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
