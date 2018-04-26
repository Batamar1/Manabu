package me.manabu.activities.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.fragment_decks.*
import me.manabu.R
import me.manabu.adapters.DecksDeckAdapter
import me.manabu.modules.Authentication
import me.manabu.webapi.Api
import me.manabu.webapi.models.BasicResponse
import me.manabu.webapi.models.DeckModel
import me.manabu.webapi.models.Status
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DecksFragment : Fragment() {

    private var _decks: MutableList<DeckModel> = mutableListOf<DeckModel>()
    private lateinit var dialog: MaterialDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
            R.layout.fragment_decks,
            container,
            false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog = MaterialDialog.Builder(activity!!)
                .title(R.string.decks_deck_progress_title)
                .content(R.string.decks_deck_progress_content)
                .progress(true, 0)
                .cancelable(false)
                .show()

        getAllDecks()

        Log.d("rofl", Authentication.account!!.id)


        decksFabCreate.setOnClickListener { view1 ->
            Snackbar.make(view1, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun getAllDecks() {
        Api.retrofit.getAllDecks().enqueue(object : Callback<BasicResponse<List<DeckModel>>> {
            override fun onResponse(call: Call<BasicResponse<List<DeckModel>>>?, response: Response<BasicResponse<List<DeckModel>>>?) {
                hideDialog()

                if (response != null && response.isSuccessful) {
                    getDataFromResponse(response.body()!!)
                } else {
                    toast("Can't get decks for some reason.")
                }
            }

            override fun onFailure(call: Call<BasicResponse<List<DeckModel>>>?, t: Throwable?) {
                Log.d("LessonCardActivity", "Get deck failed - " + t.toString())

                hideDialog()
            }

        })
    }

    private fun getDataFromResponse(body: BasicResponse<List<DeckModel>>) {
        if (body.status == Status.FAIL) {
            toast(body.errors)
            return
        }

        _decks = body.data.toMutableList()

        updateDecksList()
    }

    private fun updateDecksList() {
        val adapter = DecksDeckAdapter(activity!!, _decks)
        decksListOfDecks.adapter = adapter
    }

    private fun hideDialog(){
        if(dialog.isShowing){
            dialog.hide()
        }
    }
}