package br.com.postura

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment/*
import kotlinx.android.synthetic.main.fragment_fill_time_wake_up.constraintLayoutWakeUp
import kotlinx.android.synthetic.main.fragment_fill_time_wake_up.hourFilledWake
import kotlinx.android.synthetic.main.fragment_fill_time_wake_up.minuteFilledWake*/
import kotlinx.android.synthetic.main.fragment_fill_time.*
import kotlinx.android.synthetic.main.fragment_fill_time_wake_up.*
import kotlinx.android.synthetic.main.toolbar_fill_time.button_back

/**
 * A simple [Fragment] subclass.
 */
class FillTimeWakeUpFragment : Fragment() {

    companion object {
        fun newInstance() = FillTimeWakeUpFragment()
        const val FRAGMENT_TAG = "FillTimeWakeUpFragment"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fill_time_wake_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configToolbar()
    }

    override fun onStart() {
        super.onStart()
        getTimePickerInfo()
    }

    private fun getTimePickerInfo(){
        configTimePickerUtil(timePickerWakeUp, context!!)
    }


    private fun onBackPressed() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.back_right_to_left, R.anim.back_left_to_right)
            ?.replace(R.id.container_fill_time, FillTimeFragment.newInstance(), FRAGMENT_TAG)
            ?.commitNow()
    }

    private fun configToolbar() {
        activity?.button_back?.visibility = View.VISIBLE
        activity?.button_back?.setOnClickListener {
            onBackPressed()
        }
    }
}