package com.example.geofencing.ui.main

import androidx.lifecycle.ViewModel
import com.example.geofencing.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    val logs = repository.logs;

}