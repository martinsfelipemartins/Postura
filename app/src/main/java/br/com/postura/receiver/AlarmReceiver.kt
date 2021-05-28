package br.com.postura.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import br.com.postura.R
import br.com.postura.utils.Constants

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)
        when (intent.action) {
            Constants.ACTION_SET_EXACT -> {
                notificationManager.buildNotification(context, "Set Exact Time", convertDate(timeInMillis))
            }

          /*  Constants.ACTION_SET_REPETITIVE_EXACT -> {
                setRepetitiveAlarm(AlarmService(context))
                notificationManager.buildNotification(context, "Set Repetitive Exact Time", convertDate(timeInMillis))
            }*/
        }
    }

    private fun NotificationManager.buildNotification(context: Context, title: String, message: String) {
        val builder = NotificationCompat.Builder(
            context,
            context.getString(R.string.notification_channel_id))
            .setSmallIcon(R.drawable.ic_alarm_grey_24dp)
            .setContentTitle(context.getString(R.string.fab_transformation_scrim_behavior))
            .setContentText(message)
        notify(0, builder.build())
    }
    private fun convertDate(timeInMillis: Long): String =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()

}