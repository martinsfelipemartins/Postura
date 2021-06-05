package br.com.postura.views.generatedhours

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.postura.data.model.HourCalculed
import br.com.postura.service.AlarmService
import br.com.postura.utils.RandomUtil
import java.text.SimpleDateFormat
import java.util.*

class GeneratedHourViewModel(private val alarmService: AlarmService) : ViewModel() {
    private var _hours = MutableLiveData<MutableList<HourCalculed>>()
    val hours: LiveData<MutableList<HourCalculed>> = _hours
    private val hourList = mutableListOf<HourCalculed>()


    private val calendar: Calendar = Calendar.getInstance()

    init {
      configTimeSelected()
    }

    fun configAlarmNotifications() {

        hourList.forEach {
            val hour = it.generatedHour
            val minute = it.generatedMinute
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour.toInt())
            calendar.set(Calendar.MINUTE, minute.toInt())
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val now = Calendar.getInstance()
            if (now.before(calendar)) {
                alarmService.setExactAlarm(calendar.timeInMillis, RandomUtil.getRandomInt())

            } else {
                calendar.add(Calendar.DATE, 1)
                alarmService.setExactAlarm(calendar.timeInMillis, RandomUtil.getRandomInt())
            }
        }
    }

    private fun differenceBetweenHours(): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        val startDate = simpleDateFormat.parse(formatHour(GeneratedHoursFragment.hourSleep, GeneratedHoursFragment.minuteSleep))
        val endDate = simpleDateFormat.parse(formatHour(GeneratedHoursFragment.hourWakeUp, GeneratedHoursFragment.minuteWakeUp))

        var difference = endDate.time - startDate.time
        if (difference < 0) {
            val dateMax = simpleDateFormat.parse("24:00")
            val dateMin = simpleDateFormat.parse("00:00")
            difference =
                    dateMax.time - startDate.time + (endDate.time - dateMin.time)
        }
        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
        val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()

        return hours.toString()
    }

    private fun configTimeSelected() {

        calendar.set(Calendar.HOUR_OF_DAY, GeneratedHoursFragment.hourSleep.toInt())
        calendar.set(Calendar.MINUTE, GeneratedHoursFragment.minuteSleep.toInt())

        val sleepTimeHour = calendar.get(Calendar.HOUR_OF_DAY)
        val sleepTimeMinute = calendar.get(Calendar.MINUTE)

        calendar.set(Calendar.HOUR_OF_DAY, GeneratedHoursFragment.hourWakeUp.toInt())
        calendar.set(Calendar.MINUTE, GeneratedHoursFragment.minuteWakeUp.toInt())

        val wakeUpTimeHour = calendar.get(Calendar.HOUR_OF_DAY)
        val wakeUpTimeMinute = calendar.get(Calendar.MINUTE)

        val timeWakeUpInMinutes = (wakeUpTimeHour * 60).plus(wakeUpTimeMinute)
        val timeSleepInMinutes = (sleepTimeHour * 60).plus(sleepTimeMinute)

        if (timeWakeUpInMinutes > timeSleepInMinutes) {
            for (i in 1 until numberAlarms()) {
                calendar.add(Calendar.MINUTE, 1)
                val minute = calendar.get(Calendar.MINUTE)
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val sumInMinutes = minute.plus(hour * 60)
                addListAssembledHour(sumInMinutes)
            }
        } else {
            for (i in timeSleepInMinutes downTo timeWakeUpInMinutes step 1) {
                addListAssembledHour(i)
            }
        }
    }

    private fun numberAlarms() = (24 - differenceBetweenHours().toInt()) * 2

    private fun formatHour(hourSleep: String, minuteSleep: String) = "$hourSleep:$minuteSleep"

    private fun addListAssembledHour(i: Int): MutableList<HourCalculed> {
        val hour = i.div(60)
        val minute = i % 60

        hourList.add(HourCalculed(formatHourWithZero(hour.toString()), formatMinutesWithZero(minute.toString())))
        _hours.value = hourList
        return hourList
    }

    private fun formatMinutesWithZero(minute: String): String {
        return if (minute.toInt() < 10) {
            minute.padStart(2, '0')
        } else minute
    }

    private fun formatHourWithZero(hour: String): String {
        return if (hour.toInt() == 0) {
            hour.padStart(2, '0')
        } else hour
    }
}