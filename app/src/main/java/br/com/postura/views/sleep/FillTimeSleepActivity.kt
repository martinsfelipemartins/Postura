package br.com.postura.views.sleep

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.postura.R
import br.com.postura.views.WelcomeDialog
import kotlinx.android.synthetic.main.toolbar_fill_time.tittle_toolbar
import kotlinx.android.synthetic.main.toolbar_fill_time.toolbar

class FillTimeSleepActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_time)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container_fill_time,
                        FillTimeSleepFragment.newInstance()
                    )
                    .commitNow()
        }
        configToolbar()
        WelcomeDialog().show(supportFragmentManager, "welcomeFragment")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val tagFragment = supportFragmentManager.findFragmentByTag(VALUE_FRAGMENT)
        if (tagFragment != null && tagFragment.isVisible) {
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.back_right_to_left, R.anim.back_left_to_right)
                    .replace(
                            R.id.container_fill_time,
                        FillTimeSleepFragment.newInstance()
                    )
                    .commitNow()
        } else {
            super.onBackPressed()
        }
    }

    private fun configToolbar() {
        tittle_toolbar.text = "Coloque horário"
        toolbar.background = this.getDrawable(R.color.white)

    }

    companion object {
        const val VALUE_FRAGMENT = "FillTimeWakeUpFragment"
    }
}