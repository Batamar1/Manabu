package me.manabu.activities.fragments;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_loading.*
import me.manabu.R
import me.manabu.activities.MainActivity
import org.jetbrains.anko.sdk25.coroutines.onClick

class LoadingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
            R.layout.fragment_loading,
            container,
            false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingFragmentRetryButton.onClick {
            (activity as MainActivity).loadDataForSignedUser()
            removeError()
        }
    }

    fun removeError(){
        loadingFragmentPBar.visibility = VISIBLE
        loadingFragmentText.text = getString(R.string.loading_fragment_text)
        loadingFragmentRetryButton.visibility = GONE
    }

    fun showError(){
        loadingFragmentPBar.visibility = GONE
        loadingFragmentText.text = getString(R.string.loading_fragment_text_failed)
        loadingFragmentRetryButton.visibility = VISIBLE
    }
}
