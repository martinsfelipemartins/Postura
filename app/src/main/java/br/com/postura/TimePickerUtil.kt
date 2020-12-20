
package br.com.postura

import android.content.Context
import android.widget.TimePicker
import android.widget.Toast

fun configTimePickerUtil(timePicker: TimePicker, context: Context) {
    timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->

        Toast.makeText(context, "hour: $hourOfDay minute: $minute", Toast.LENGTH_SHORT).show()
    }

}