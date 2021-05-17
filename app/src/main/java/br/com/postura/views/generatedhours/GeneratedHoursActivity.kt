package br.com.postura.views.generatedhours

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.postura.R
import kotlinx.android.synthetic.main.generated_hours_fragment.*


class GeneratedHoursActivity : AppCompatActivity() {

    companion object{
        lateinit var minutes:String
        lateinit var hours: String
        lateinit var hoursWakeUp: String
        lateinit var minutesWakeUp: String

        fun newInstance(hour: String, minute: String, hourWakeUp: String, minuteWakeUp: String): Class<GeneratedHoursActivity>{
            minutes = minute
            hours =  hour
            hoursWakeUp = hourWakeUp
            minutesWakeUp = minuteWakeUp

            return GeneratedHoursActivity::class.java
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.generated_hours_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, GeneratedHoursFragment.newInstance(hours, minutes, hoursWakeUp, minutesWakeUp))
                    .commitNow()
        }
    }
}