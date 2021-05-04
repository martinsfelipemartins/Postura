package br.com.postura.utils

import android.content.Context
import android.os.Build
import android.widget.TimePicker
import android.widget.Toast
import java.util.*

var currentTime: Date = Calendar.getInstance().time
var currenthorinha: String= currentTime.toString()
var hourListener: String= ""
var minuteListener: String= ""
fun configTimePickerUtil(timePicker: TimePicker, context: Context) {
    timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
        hourListener = hourOfDay.toString()
        minuteListener = minute.toString()
     /*   currenthorinha = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour.toString()
        } else {
            timePicker.currentHour.toString()
        }*/
        Toast.makeText(context, "hour: $hourOfDay minute: $minute", Toast.LENGTH_SHORT).show()
    }
}