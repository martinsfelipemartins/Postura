package br.com.postura.views.generatedhours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.postura.R

class GeneratedHoursActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.generated_hours_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, GeneratedHoursFragment.newInstance())
                    .commitNow()
        }
    }
}