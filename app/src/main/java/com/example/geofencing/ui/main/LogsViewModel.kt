package com.example.geofencing.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geofencing.data.model.Log
import com.example.geofencing.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    val logs = repository.logs;

    init {
        val log = Log(1, 1, 1, "dkflm", "skdvlj")
        viewModelScope.launch {
            repository.insertLog(log)
        }
    }
}
