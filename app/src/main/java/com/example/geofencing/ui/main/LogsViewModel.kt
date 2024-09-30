package com.example.geofencing.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geofencing.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    val logs = repository.logs;

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertAllDataIntoDB()
            }
        }
    }
}
