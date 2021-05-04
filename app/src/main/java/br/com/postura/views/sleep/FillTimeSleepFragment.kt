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
import br.com.postura.utils.configTimePickerUtil
import br.com.postura.utils.hourListener
import br.com.postura.utils.minuteListener
import br.com.postura.views.wakeup.FillTimeWakeUpFragment
import kotlinx.android.synthetic.main.fragment_fill_time.*
import kotlinx.android.synthetic.main.toolbar_fill_time.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */

class FillTimeSleepFragment : Fragment(){
     companion object {
        fun newInstance() = FillTimeSleepFragment()
        const val VALUE_FRAGMENT = "FillTimeWakeUpFragment"
        var formate = SimpleDateFormat("dd MMM, YYYY")
        var timeFormat = SimpleDateFormat("hh mm a", Locale.US)
        lateinit var timePicker: TimePicker
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


    }

    private fun getTimePickerInfo(){
        Toast.makeText(context, "hour: $hourListener minute: $minuteListener", Toast.LENGTH_SHORT).show()
        configTimePickerUtil(timePickerSleep, context!!)

    }

    private fun goToFillWakeUp() {
val cal = Calendar.getInstance()
     button_next.setOnClickListener {
         differenceBetweenHours()

         activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
                    R.anim.enter_left_to_right, R.anim.enter_right_to_left
            )
                ?.replace(
                        R.id.container_fill_time,
                        FillTimeWakeUpFragment.newInstance(),
                    VALUE_FRAGMENT
                )
                ?.commitNow()
           Toast.makeText(context, cal.get(Calendar.HOUR_OF_DAY).toString()+":"+cal.get(Calendar.MINUTE).toString(), Toast.LENGTH_LONG).show()
              }
    }

    private fun differenceBetweenHours() {
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        val startDate = simpleDateFormat.parse("01:00")
        val endDate = simpleDateFormat.parse("17:00")

        var difference = endDate.time - startDate.time
        if (difference < 0) {
            val dateMax = simpleDateFormat.parse("24:00")
            val dateMin = simpleDateFormat.parse("00:00")
            difference =
                dateMax.time - startDate.time + (endDate.time - dateMin.time)
        }
        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
        val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
        val min =
            (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
        Log.i("log_tag", "Hours: $hours, Mins: $min")
    }

    private fun configToolbar() {
        activity?.button_back?.visibility = View.INVISIBLE
    }

}