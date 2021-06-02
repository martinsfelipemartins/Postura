package br.com.postura.views.generatedhours

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.postura.R
import br.com.postura.adapter.GeneratedHourAdapter
import br.com.postura.data.model.HourCalculed
import br.com.postura.service.AlarmService
import br.com.postura.utils.RandomUtil
import kotlinx.android.synthetic.main.generated_hours_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GeneratedHoursFragment : Fragment() {

    private val hours = ArrayList<HourCalculed>()
    private val adapter = GeneratedHourAdapter(hours)
    private val calendar = Calendar.getInstance()
    private val alarmService = context?.let { AlarmService(it) }


    companion object {
        lateinit var hourSleep: String
        lateinit var minuteSleep: String
        lateinit var hourWakeUp: String
        lateinit var minuteWakeUp: String

        fun newInstance(
            hour: String,
            minute: String,
            hourWakeUp: String,
            minutesWakeUp: String
        ): GeneratedHoursFragment {
            this.hourSleep = hour
            this.minuteSleep = minute
            this.hourWakeUp = hourWakeUp
            this.minuteWakeUp = minutesWakeUp
            return GeneratedHoursFragment()
        }
    }

    private lateinit var viewModel: GeneratedHourViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.generated_hours_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GeneratedHourViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        configTimeSelected()

        alarmService?.let { configAlarmNotifications(it) }
    }

    private fun configAlarmNotifications(alarmService: AlarmService) {
        for (i in hours) {
            val hour = i.generatedHour
            val minute = i.generatedMinute
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

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notif_channel_description)

            val notificationManager = requireContext().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun configTimeSelected() {

        calendar.set(Calendar.HOUR_OF_DAY, hourSleep.toInt())
        calendar.set(Calendar.MINUTE, minuteSleep.toInt())

        val sleepTimeHour = calendar.get(Calendar.HOUR_OF_DAY)
        val sleepTimeMinute = calendar.get(Calendar.MINUTE)

        calendar.set(Calendar.HOUR_OF_DAY, hourWakeUp.toInt())
        calendar.set(Calendar.MINUTE, minuteWakeUp.toInt())

        val wakeUpTimeHour = calendar.get(Calendar.HOUR_OF_DAY)
        val wakeUpTimeMinute = calendar.get(Calendar.MINUTE)

        val timeWakeUpInMinutes = (wakeUpTimeHour * 60).plus(wakeUpTimeMinute)
        val timeSleepInMinutes = (sleepTimeHour * 60).plus(sleepTimeMinute)

        if (timeWakeUpInMinutes > timeSleepInMinutes) {
            for (i in 1 until numberAlarms()) {
                calendar.add(Calendar.MINUTE, 30)
                val minute = calendar.get(Calendar.MINUTE)
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val sumInMinutes = minute.plus(hour* 60)
                addListAssembledHour(sumInMinutes)
            }
        } else {
            for (i in timeSleepInMinutes downTo timeWakeUpInMinutes step 30) {
                addListAssembledHour(i)
            }
        }
    }

    private fun differenceBetweenHours(): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        val startDate = simpleDateFormat.parse(formatHour(hourSleep, minuteSleep))
        val endDate = simpleDateFormat.parse(formatHour(hourWakeUp, minuteWakeUp))

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

    private fun numberAlarms() = (24 - differenceBetweenHours().toInt())* 2

    private fun formatHour(hourSleep: String, minuteSleep: String) = "$hourSleep:$minuteSleep"

    private fun addListAssembledHour(i: Int) {
        val hour = i.div(60)
        val minute = i % 60

        hours.add(HourCalculed(formatHourWithZero(hour.toString()), formatMinutesWithZero(minute.toString())))
    }

    private fun initRecyclerView() {
        with(recyclerViewHours) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        recyclerViewHours.adapter = adapter
    }

    override fun onStop() {
        super.onStop()
        hours.clear()
    }

    private fun formatMinutesWithZero(minute: String): String {
        var number = ""
        number = if (minute.toInt() < 10) {
            minute.padStart(2, '0')
        } else minute
        return number
    }

    private fun formatHourWithZero(hour: String): String {
        var number = ""
        number = if (hour.toInt() == 0) {
            hour.padStart(2, '0')
        } else hour
        return number
    }
}