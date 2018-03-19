package me.manabu.adapters


import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList

import me.manabu.R
import me.manabu.adapters.models.LessonItemModel

class LessonItemAdapter(private val activityContext: Context, private val data: ArrayList<LessonItemModel>) : ArrayAdapter<LessonItemModel>(activityContext, R.layout.item_lesson_text, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val holder = ItemHolder()
        var infoItem = convertView

        val inflater = (context as Activity).layoutInflater
        infoItem = inflater.inflate(R.layout.item_lesson_text, null)

        holder.header = infoItem!!.findViewById(R.id.itemLessonHeader) as TextView
        holder.text = infoItem.findViewById(R.id.itemLessonDescription) as TextView

        holder.header!!.text = data[position].header
        holder.text!!.text = data[position].text

        return infoItem
    }

    private inner class ItemHolder {
        internal var header: TextView? = null
        internal var text: TextView? = null
    }
}