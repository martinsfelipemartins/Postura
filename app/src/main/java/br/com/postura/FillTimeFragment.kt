package br.com.postura

import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_fill_time.button_next
import kotlinx.android.synthetic.main.fragment_fill_time.constraintLayout
import kotlinx.android.synthetic.main.fragment_fill_time.hour_filled
import kotlinx.android.synthetic.main.fragment_fill_time.minute_filled
import kotlinx.android.synthetic.main.fragment_fill_time_wake_up.view.cardViewWakeUp
import kotlinx.android.synthetic.main.toolbar_fill_time.button_back
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter
import kotlin.coroutines.coroutineContext


/**
 * A simple [Fragment] subclass.
 */

class FillTimeFragment : Fragment(){

    companion object {
        fun newInstance() = FillTimeFragment()
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
        addHourMinuteToSleep()
        configToolbar()
        goToFillWakeUp()

    }


    fun updateTime(hourOfDay: Int, minuteOfHour: Int, timePicker: TimePicker) {
        timePicker.currentHour = hourOfDay
        timePicker.currentMinute = minuteOfHour


    }

    private fun configTimePicker(){

        //val date = timeFormat.parse(hour_filled.text.toString())
        /*  val now =  Calendar.getInstance()
          timePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
              val selectedTime = Calendar.getInstance()
              selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
              selectedTime.set(Calendar.MINUTE, minute)
              hour_filled.text = SimpleDateFormat("HH").format(now.time)
          },

          now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false)
          timePicker.show()
  */

    }


    /* override fun onSaveInstanceState(): Bundle {
         val state = super.onSaveInstanceState()
         state.putInt(HOUR, timePicker.currentHour)
         state.putInt(MINUTE, timePicker.currentMinute)
         state.putBoolean(IS_24_HOUR, timePicker.is24HourView)
         return state
     }

     override fun onRestoreInstanceState(savedInstanceState: Bundle) {
         super.onRestoreInstanceState(savedInstanceState)
         val hour = savedInstanceState.getInt(HOUR)
         val minute = savedInstanceState.getInt(MINUTE)
         timePicker.setIs24HourView(savedInstanceState.getBoolean(IS_24_HOUR))
         timePicker.currentHour = hour
         timePicker.currentMinute = minute
     }*/

    /* private fun configSharedPreferences(){
         val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
         sharedPreferences.apply {
             val hours = getString("HOUR", "")
             val minutes = getString("MINUTE", "")
             hour_filled.setText(hours)
             minute_filled.setText(minutes)
         }
     }

     private fun saveDatasHourminute(){
         val preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)
         val editor = preferenceManager.edit()
         editor.putString("HOUR", hour_filled.text.toString())
         editor.putString("MINUTE", minute_filled.text.toString()).apply()
     }
 */
    private fun addHourMinuteToSleep() {
        val calendar = Calendar.getInstance()
        constraintLayout.setOnClickListener {
            context?.let { configTimePickerUtil(hour_filled, minute_filled, it)  }
            //  Toast.makeText(context, a, Toast.LENGTH_LONG).show()

        }
    }


    private fun goToFillWakeUp() {


        button_next.setOnClickListener {

            timePicker = TimePicker(context)
            timePicker.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { view, hourOfDay, minute ->
                timePicker.currentHour
            })

            activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
                R.anim.enter_left_to_right, R.anim.enter_right_to_left
            )
                ?.replace(
                    R.id.container_fill_time,
                    FillTimeWakeUpFragment.newInstance(),
                    VALUE_FRAGMENT
                )
                ?.commitNow()

        }
    }

    private fun configToolbar() {
        activity?.button_back?.visibility = View.INVISIBLE
    }

}