package br.com.postura.views.generatedhours

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.postura.service.AlarmService

class GeneratedHoursViewModelFactory(private val alarmService: AlarmService?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return alarmService?.let { GeneratedHourViewModel(it) } as T
    }
}