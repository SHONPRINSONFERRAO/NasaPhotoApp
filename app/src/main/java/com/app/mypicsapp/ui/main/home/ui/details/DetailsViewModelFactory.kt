package com.app.mypicsapp.ui.main.home.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.mypicsapp.data.model.ListOfPhotos

class DetailsViewModelFactory(private val data: ListOfPhotos) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(data) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}