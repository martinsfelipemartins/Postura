
package br.com.postura

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TextView
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.*

lateinit var a:String

fun configTimePickerUtil( hourView: TextView, minuteView: TextView, context: Context) {
    val calendar = Calendar.getInstance()
    val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hourOfDay, minute ->
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        hourView.text = SimpleDateFormat("HH").format(calendar.time)
        minuteView.text = SimpleDateFormat("mm").format(calendar.time)

    }

    TimePickerDialog(
        context,
        timeSetListener,
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    ).show()
}