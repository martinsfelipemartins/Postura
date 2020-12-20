package br.com.postura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_fill_time.*
import kotlinx.android.synthetic.main.toolbar_fill_time.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */

class FillTimeFragment : Fragment(){

    companion object {
        fun newInstance() = FillTimeFragment()
        const val VALUE_FRAGMENT = "FillTimeWakeUpFragment"
        var formate = SimpleDateFormat("dd MMM, YYYY")
        var timeFormat = SimpleDateFormat("hh mm a", Locale.US)
        lateinit var timePicker: TimePicker
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fill_time, container, false)

    }

    override fun onStart() {
        super.onStart()
         configToolbar()
        goToFillWakeUp()
        getTimePickerInfo()


    }

    private fun getTimePickerInfo(){
        configTimePickerUtil(timePickerSleep, context!!)
    }

    private fun goToFillWakeUp() {

     button_next.setOnClickListener {

            activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
                R.anim.enter_left_to_right, R.anim.enter_right_to_left
            )
                ?.replace(
                    R.id.container_fill_time,
                    FillTimeWakeUpFragment.newInstance(),
                    VALUE_FRAGMENT
                )
                ?.commitNow()

        }
    }

    private fun configToolbar() {
        activity?.button_back?.visibility = View.INVISIBLE
    }

}