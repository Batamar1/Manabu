package me.manabu.utils

import android.content.Context
import android.util.TypedValue

object DpUtils {
    fun fromDpToPixels(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }
}
