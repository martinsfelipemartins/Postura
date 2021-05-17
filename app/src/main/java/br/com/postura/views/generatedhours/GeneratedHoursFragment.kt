package br.com.postura.views.generatedhours

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
import kotlinx.android.synthetic.main.generated_hours_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class GeneratedHoursFragment : Fragment() {

    private val hours = ArrayList<HourCalculed>()
    private val adapter = GeneratedHourAdapter(hours)
    private val calendar = Calendar.getInstance()

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

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        configTimeSelected()
    }

    private fun configTimeSelected() {

        calendar.set(Calendar.HOUR_OF_DAY, hourWakeUp.toInt())
        calendar.set(Calendar.MINUTE, minuteWakeUp.toInt())

        val wakeUpTimeHour = calendar.get(Calendar.HOUR_OF_DAY)
        val wakeUpTimeMinute = calendar.get(Calendar.MINUTE)

        calendar.set(Calendar.HOUR_OF_DAY, hourSleep.toInt())
        calendar.set(Calendar.MINUTE, minuteSleep.toInt())

        val sleepTimeHour = calendar.get(Calendar.HOUR_OF_DAY)
        val sleepTimeMinute = calendar.get(Calendar.MINUTE)

        val timeWakeUpInMinutes = (wakeUpTimeHour * 60).plus(wakeUpTimeMinute)
        val timeSleepInMinutes = (sleepTimeHour * 60).plus(sleepTimeMinute)

        if (timeWakeUpInMinutes > timeSleepInMinutes) {
            for (i in timeSleepInMinutes until timeWakeUpInMinutes step 30) {
                addListAssembledHour(i)
            }
        } else {
            for (i in timeSleepInMinutes downTo timeWakeUpInMinutes step 30) {
                addListAssembledHour(i)
            }
        }
    }

    private fun addListAssembledHour(i: Int) {
        val hour = i.div(60)
        val minute = i % 60

        hours.add(HourCalculed(hour.toString(), formatMinutesWithZero(minute.toString())))
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
}