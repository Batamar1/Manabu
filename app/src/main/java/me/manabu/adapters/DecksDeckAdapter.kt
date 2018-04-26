package me.manabu.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import me.manabu.R
import me.manabu.modules.Authentication
import me.manabu.webapi.Api
import me.manabu.webapi.models.DeckModel
import okhttp3.ResponseBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            Api.retrofit.copyDeckToUser(deck.id, Authentication.account!!.id!!).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    activityContext.toast("norm")
                    TODO("Запилить иконку загрузки, а так же запилить удаление деки от юзера.")
                }



                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    activityContext.toast("ne norm")
                }

            })
        }

        return infoItem
    }

    internal class ItemHolder {
        var title: TextView? = null
        var add: ImageButton? = null
    }
}