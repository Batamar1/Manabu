package me.manabu.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList

import me.manabu.R
import me.manabu.adapters.models.DecksDeckModel

class DecksDeckAdapter(private val activityContext: Context, private val data: ArrayList<DecksDeckModel>) :
        ArrayAdapter<DecksDeckModel>(activityContext, R.layout.item_decks_deck, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val holder = ItemHolder()
        var infoItem = convertView

        val inflater = (context as Activity).layoutInflater
        infoItem = inflater.inflate(R.layout.item_decks_deck, null)

        holder.title = infoItem!!.findViewById(R.id.itemDeckTitle) as TextView
        holder.desc = infoItem.findViewById(R.id.itemDeckDescription) as TextView
        holder.cards = infoItem.findViewById(R.id.itemDeckCardsCounter) as TextView

        holder.title!!.text = data[position].title
        holder.desc!!.text = data[position].description
        holder.cards!!.text = data[position].cards.toString()

        return infoItem
    }

    private inner class ItemHolder {
        internal var title: TextView? = null
        internal var desc: TextView? = null
        internal var cards: TextView? = null
    }
}