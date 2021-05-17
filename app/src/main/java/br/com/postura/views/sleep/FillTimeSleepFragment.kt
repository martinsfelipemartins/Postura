package br.com.postura.views.sleep

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.postura.R
import br.com.postura.utils.*
import br.com.postura.views.wakeup.FillTimeWakeUpFragment
import kotlinx.android.synthetic.main.fragment_fill_time.*
import kotlinx.android.synthetic.main.toolbar_fill_time.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */

class FillTimeSleepFragment : Fragment() {
    companion object {
        fun newInstance() = FillTimeSleepFragment()
        const val VALUE_FRAGMENT = "FillTimeWakeUpFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fill_time, container, false)
    }

    override fun onStart() {
        super.onStart()
        configToolbar()
        goToFillWakeUp()
        getTimePickerInfo()
        getSleepMinutesSelected()
    }

    private fun getTimePickerInfo() {
        configTimePickerUtil(timePickerSleep, context!!, true)
    }

    private fun goToFillWakeUp() {

        button_next.setOnClickListener {
            Toast.makeText(context, getSleepMinutesSelected(), Toast.LENGTH_LONG).show()

            activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
                R.anim.enter_left_to_right, R.anim.enter_right_to_left
            )
                ?.replace(
                    R.id.container_fill_time,
                    FillTimeWakeUpFragment.newInstance(
                        getSleepHourSelected(),
                        getSleepMinutesSelected()
                    ),
                    VALUE_FRAGMENT
                )
                ?.commitNow()
        }
    }

    private fun configToolbar() {
        activity?.button_back?.visibility = View.INVISIBLE
    }
}