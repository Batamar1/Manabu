package me.manabu.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import me.manabu.R
import me.manabu.modules.CurrentUser
import me.manabu.webapi.Api
import me.manabu.webapi.models.DeckModel
import me.manabu.webapi.models.UserDeckModel
import okhttp3.ResponseBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DecksDeckAdapter(context: Context, private val data: MutableList<DeckModel>) :
        ArrayAdapter<DeckModel>(context, R.layout.item_decks_deck, data) {

    companion object {
        private fun addDeckToUser(context: Context, deck: DeckModel, holder: DecksDeckHolder) {
            setLoadingIcon(holder)

            Api.retrofit.copyDeckToUser(deck.id, CurrentUser.getAccount().id!!).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    if(response != null && response.isSuccessful){
                        setRemoveAction(context, deck, holder)
                        context.toast(context.getString(R.string.decks_deck_added))

                        CurrentUser.receiveDecks()
                    } else {
                        setAddAction(context, deck, holder)
                        context.toast(context.getString(R.string.decks_deck_adding_failed))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    setAddAction(context, deck, holder)
                    context.toast(context.getString(R.string.decks_deck_adding_failed))
                }
            })
        }

        private fun removeDeckFromUser(context: Context, deck: DeckModel, holder: DecksDeckHolder) {
            setLoadingIcon(holder)

            val userDeck = getUserDeckFromMainDeck(deck)

            Api.retrofit.removeUserDeck(userDeck.id).enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    if(response != null && response.isSuccessful){
                        context.toast(context.getString(R.string.decks_deck_removed))
                        setAddAction(context, deck, holder)

                        CurrentUser.decks.remove(userDeck)
                    } else {
                        setRemoveAction(context, deck, holder)
                        context.toast(context.getString(R.string.decks_deck_removing_failed))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    setRemoveAction(context, deck, holder)
                    context.toast(context.getString(R.string.decks_deck_removing_failed))
                }
            })
        }

        private fun getUserDeckFromMainDeck(mainDeck: DeckModel): UserDeckModel {
            return CurrentUser.decks.find { mainDeck.id == it.originalId }!!
        }

        private fun setAddAction(context: Context, deck: DeckModel, holder: DecksDeckHolder){
            holder.action?.setImageDrawable(context.getDrawable(R.drawable.ic_add_white_48px))
            holder.action?.imageTintList = context.resources.getColorStateList(R.color.primary_dark)
            holder.action?.visibility = VISIBLE
            holder.action?.onClick {
                DecksDeckAdapter.addDeckToUser(context, deck, holder)
            }

            holder.loader?.visibility = GONE
        }

        private fun setLoadingIcon(holder: DecksDeckHolder){
            holder.action?.onClick{}

            holder.action?.visibility = GONE
            holder.loader?.visibility = VISIBLE
        }

        private fun setRemoveAction(context: Context, deck: DeckModel, holder: DecksDeckHolder){
            holder.action?.setImageDrawable(context.getDrawable(R.drawable.ic_clear_black_48px))
            holder.action?.imageTintList = context.resources.getColorStateList(R.color.md_red_500)
            holder.action?.visibility = VISIBLE
            holder.action?.onClick {
                DecksDeckAdapter.removeDeckFromUser(context, deck, holder)
            }

            holder.loader?.visibility = GONE
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val holder = DecksDeckHolder()
        var infoItem = convertView

        val inflater = (context as Activity).layoutInflater
        if (infoItem == null) {
            infoItem = inflater.inflate(R.layout.item_decks_deck, parent, false)
        }

        val deck = data[position]

        holder.title = infoItem?.findViewById(R.id.itemDeckTitle) as TextView
        holder.action = infoItem.findViewById(R.id.itemDeckAction) as ImageButton
        holder.loader = infoItem.findViewById(R.id.itemDeckAddProgress) as ProgressBar

        holder.title?.text = deck.name

        //TODO("FINISH ADD/REMOVE BUTTONs")

        if(CurrentUser.decks.any{deck.id==it.originalId}){
            setRemoveAction(context, deck, holder)
        } else {
            setAddAction(context, deck, holder)
        }

        return infoItem
    }

    class DecksDeckHolder {
        var title: TextView? = null
        var action: ImageButton? = null
        var loader: ProgressBar? = null
    }
}