package me.manabu.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import me.manabu.R
import me.manabu.webapi.models.DeckModel
import org.jetbrains.anko.sdk25.coroutines.onClick

class DecksDeckAdapter(private val activityContext: Activity, private val data: MutableList<DeckModel>) :
        ArrayAdapter<DeckModel>(activityContext, R.layout.item_decks_deck, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val holder = ItemHolder()
        var infoItem = convertView

        val inflater = (context as Activity).layoutInflater
        if (infoItem == null) {
            infoItem = inflater.inflate(R.layout.item_decks_deck, parent, false)
        }

        val deck = data[position]

        holder.title = infoItem?.findViewById(R.id.itemDeckTitle) as TextView
        holder.add = infoItem.findViewById(R.id.itemDeckAdd) as ImageButton

        holder.title?.text = deck.name
        holder.add?.onClick {

        }

        return infoItem
    }

    internal class ItemHolder {
        var title: TextView? = null
        var add: ImageButton? = null
    }
}