package br.com.postura.views.generatedhours

import android.icu.lang.UCharacter
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.postura.R
import br.com.postura.adapter.GeneratedHourAdapter
import br.com.postura.data.model.HourCalculed
import kotlinx.android.synthetic.main.generated_hours_fragment.*

class GeneratedHoursFragment : Fragment() {

    private val hours = ArrayList<HourCalculed>()
    private val adapter = GeneratedHourAdapter(hours)

    companion object {
        fun newInstance() =
            GeneratedHoursFragment()
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
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        mockList()
    }

    private fun mockList() {
        val houracalculada = HourCalculed("23", "43")
        val houracalculada2 = HourCalculed("11", "23")
        val houracalculada3 = HourCalculed("13", "24")
        hours.add(houracalculada)
        hours.add(houracalculada2)
        hours.add(houracalculada3)
    }

    private fun initRecyclerView() {
        with(recyclerViewHours) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        recyclerViewHours.adapter = adapter
    }
}