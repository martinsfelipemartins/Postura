package br.com.postura.utils

import android.content.Context
import android.widget.TimePicker
import android.widget.Toast
import java.util.*

var hourSleepListener: String= ""
var minuteSleepListener: String= ""
var hourWakeUpListener: String= ""
var minuteWakeUpListener: String= ""

fun configTimePickerUtil(timePicker: TimePicker, context: Context, isSleep: Boolean) {
    timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
       if (isSleep) {
           hourSleepListener = hourOfDay.toString()
           minuteSleepListener = minute.toString()
       }else{
           hourWakeUpListener = hourOfDay.toString()
           minuteWakeUpListener = minute.toString()
       }
        Toast.makeText(context, "hour: $hourOfDay minute: $minute", Toast.LENGTH_SHORT).show()
    }
}

fun getSleepMinutesSelected(): String {
    return if (minuteSleepListener.isEmpty()) {
        Calendar.getInstance().get(Calendar.MINUTE).toString()
    } else {
        minuteSleepListener
    }
}

fun getSleepHourSelected(): String {
    return if (hourSleepListener.isEmpty()) {
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
    } else {
        hourSleepListener
    }
}

fun getWakeUpMinutesSelected(): String {
    return if (minuteWakeUpListener.isEmpty()) {
        Calendar.getInstance().get(Calendar.MINUTE).toString()
    } else {
        minuteWakeUpListener
    }
}

fun getWakeUpHourSelected(): String {
    return if (hourWakeUpListener.isEmpty()) {
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
    } else {
        hourWakeUpListener
    }

    fun randomNumber(): Int{
        return (0..100).random()
    }
}