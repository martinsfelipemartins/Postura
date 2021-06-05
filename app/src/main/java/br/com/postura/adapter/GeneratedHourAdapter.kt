package br.com.postura.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.postura.R
import br.com.postura.data.model.HourCalculed
import kotlinx.android.synthetic.main.hour__minute_selected_item.view.*


class GeneratedHourAdapter(
        private val chosenHours: ArrayList<HourCalculed>
) : RecyclerView.Adapter<GeneratedHourAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hour__minute_selected_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = chosenHours.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(chosenHours[position])
        holder.itemView.switchButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, bChecked ->
           val context = compoundButton.context
            if (bChecked) {
                Toast.makeText(context, "Checked", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Unchecked", Toast.LENGTH_LONG).show()
            }
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wakeHour: TextView = itemView.hoursCalculed
        private val wakeMinute: TextView = itemView.minutesCalculed

        fun bindView(hour: HourCalculed) {
            wakeHour.text = hour.generatedHour
            wakeMinute.text = hour.generatedMinute
        }
    }
}