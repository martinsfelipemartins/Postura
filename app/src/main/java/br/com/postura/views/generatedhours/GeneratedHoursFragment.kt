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
import kotlinx.android.synthetic.main.generated_hours_fragment.*
import java.util.*

class GeneratedHoursFragment : Fragment() {

    private lateinit var viewModel: GeneratedHourViewModel

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

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.generated_hours_fragment, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,
                GeneratedHoursViewModelFactory(AlarmService(requireContext())))
                .get(GeneratedHourViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createChannel(
                getString(R.string.notification_channel_id),
                getString(R.string.notification_channel_name)
        )
        viewModel.configAlarmNotifications()
        initRecyclerView()

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

    private fun observerData() {
        viewModel.hours.observe(this, androidx.lifecycle.Observer { hourCalc ->
            val adapter = GeneratedHourAdapter(hourCalc as ArrayList<HourCalculed>)

            recyclerViewHours.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {

        with(recyclerViewHours) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        observerData()
    }
}