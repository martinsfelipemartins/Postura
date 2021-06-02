package br.com.postura.views.wakeup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.postura.R
import br.com.postura.utils.configTimePickerUtil
import br.com.postura.utils.getWakeUpHourSelected
import br.com.postura.utils.getWakeUpMinutesSelected
import br.com.postura.views.generatedhours.GeneratedHoursActivity
import br.com.postura.views.sleep.FillTimeSleepFragment
import kotlinx.android.synthetic.main.fragment_fill_time_wake_up.*
import kotlinx.android.synthetic.main.toolbar_fill_time.*

/**
 * A simple [Fragment] subclass.
 */

class FillTimeWakeUpFragment : Fragment() {
    companion object {
        lateinit var hourSleep: String
        lateinit var minutesSleep: String

        fun newInstance(hour: String, minute: String): FillTimeWakeUpFragment {
            hourSleep = hour
            minutesSleep = minute
        return FillTimeWakeUpFragment()
        }
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
        goToCalculatorScreen()
    }

    private fun getTimePickerInfo(){
        configTimePickerUtil(timePickerWakeUp, context!!, false)
    }

    private fun onBackPressed() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.back_right_to_left, R.anim.back_left_to_right)
            ?.replace(R.id.container_fill_time,
                FillTimeSleepFragment.newInstance(),
                FRAGMENT_TAG
            )
            ?.commitNow()
    }

    private fun configToolbar() {
        activity?.button_back?.visibility = View.VISIBLE
        activity?.button_back?.setOnClickListener {
            onBackPressed()
        }
    }
    private fun goToCalculatorScreen(){
        button_calculator.setOnClickListener {
            startActivity(Intent(context, GeneratedHoursActivity.newInstance(hourSleep, minutesSleep, getWakeUpHourSelected(), getWakeUpMinutesSelected())))
        }
    }
}