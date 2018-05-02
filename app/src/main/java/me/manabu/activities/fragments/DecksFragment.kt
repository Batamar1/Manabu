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
import me.manabu.modules.CurrentUser
import me.manabu.webapi.Api
import me.manabu.webapi.models.BasicResponse
import me.manabu.webapi.models.DeckModel
import me.manabu.webapi.models.Status
import okhttp3.ResponseBody
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class DecksFragment : Fragment() {

    companion object {
        private var _decks: MutableList<DeckModel> = mutableListOf()
        private var _decksReceived = false
    }

    private lateinit var dialog: MaterialDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
            R.layout.fragment_decks,
            container,
            false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(!_decksReceived) receiveAllDecks()
        showDialog()
        startWaitingLoop()

//        decksFabCreate.setOnClickListener { view1 ->
//            Snackbar.make(view1, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    private fun receiveAllDecks() {
        Api.retrofit.getAllDecks().enqueue(object : Callback<BasicResponse<List<DeckModel>>> {
            override fun onResponse(call: Call<BasicResponse<List<DeckModel>>>?, response: Response<BasicResponse<List<DeckModel>>>?) {
                if (response != null && response.isSuccessful) {
                    getDataFromResponse(response.body()!!)
                    _decksReceived = true

                    Log.d("DecksFragment", "Decks loaded")
                } else {
                    toast("Can't get decks for some reason.")
                }
            }

            override fun onFailure(call: Call<BasicResponse<List<DeckModel>>>?, t: Throwable?) {
                Log.d("LessonsActivity", "Get deck failed - " + t.toString())

                hideDialog()
            }

        })
    }

    fun addDeckToUser(deckId: String) {
        Api.retrofit.copyDeckToUser(deckId, CurrentUser.getAccount().id!!).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                activity!!.toast("norm")
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                activity!!.toast("ne norm")
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
        decksListOfDecks.adapter = DecksDeckAdapter(activity!!, _decks)
    }

    private fun showDialog() {
        dialog = MaterialDialog.Builder(activity!!)
                .title(R.string.decks_deck_progress_title)
                .content(R.string.decks_deck_progress_content)
                .progress(true, 0)
                .cancelable(false)
                .show()
    }

    private fun startWaitingLoop() {
        thread {
            var exit = false;

            while (!exit) {
                if (CurrentUser.decksLoaded && _decksReceived) {
                    exit = true
                }
            }

            onUiThread {
                hideDialog()
            }
        }
    }

    private fun hideDialog() {
        if (dialog.isShowing) {
            dialog.hide()
        }
    }
}