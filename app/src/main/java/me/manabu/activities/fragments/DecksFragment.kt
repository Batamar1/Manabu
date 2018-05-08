package me.manabu.activities.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.fragment_decks.*
import me.manabu.R
import me.manabu.adapters.DecksDeckAdapter
import me.manabu.webapi.Api
import me.manabu.webapi.models.BasicResponse
import me.manabu.webapi.models.DeckModel
import me.manabu.webapi.models.ResponseStatus.FAIL
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class DecksFragment : Fragment() {

    private var decks: MutableList<DeckModel> = mutableListOf()
    private lateinit var dialog: MaterialDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
            R.layout.fragment_decks,
            container,
            false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showDialog()
        receiveAllDecks({
            hideDialog()
        })

//        decksFabCreate.setOnClickListener { view1 ->
//            Snackbar.make(view1, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    private fun receiveAllDecks(callback: () -> Unit = {}) {
        Api.retrofit.getAllDecks().enqueue(object : Callback<BasicResponse<List<DeckModel>>> {
            override fun onResponse(call: Call<BasicResponse<List<DeckModel>>>?, response: Response<BasicResponse<List<DeckModel>>>?) {
                if (response != null && response.isSuccessful) {
                    getDataFromResponse(response.body()!!)
                    Log.d("DecksFragment", "Decks loaded")
                    callback()
                } else {
                    toast("Can't get decks for some reason.")
                    callback()
                }
            }

            override fun onFailure(call: Call<BasicResponse<List<DeckModel>>>?, t: Throwable?) {
                Log.d("DecksFragment", "Get deck failed - " + t.toString())
                callback()
            }

        })
    }

    private fun getDataFromResponse(body: BasicResponse<List<DeckModel>>) {
        if (body.status == FAIL) {
            body.errors.forEach {
                toast(getString(R.string.error2dot) + it)
            }
            return
        }

        decks = body.data.toMutableList()

        updateDecksList()
    }

    private fun updateDecksList() {
        decksListOfDecks.adapter = DecksDeckAdapter(activity!!, decks)
    }

    private fun showDialog() {
        dialog = MaterialDialog.Builder(activity!!)
                .title(R.string.decks_deck_progress_title)
                .content(R.string.decks_deck_progress_content)
                .progress(true, 0)
                .cancelable(false)
                .show()
    }

    private fun hideDialog() {
        if (dialog.isShowing) {
            dialog.hide()
        }
    }
}