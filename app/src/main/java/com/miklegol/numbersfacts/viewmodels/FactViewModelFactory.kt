package com.miklegol.numbersfacts.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miklegol.numbersfacts.database.FactDatabase

class FactViewModelFactory(private val factDatabase: FactDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FactViewModel(factDatabase) as T
    }
}