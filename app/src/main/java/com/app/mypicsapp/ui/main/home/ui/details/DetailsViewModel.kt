package com.app.mypicsapp.ui.main.home.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.mypicsapp.data.model.ListOfPhotos
import com.app.mypicsapp.data.model.PhotoDataModel

class DetailsViewModel(data: ListOfPhotos) : ViewModel() {

    private val _photoDetail = MutableLiveData<ListOfPhotos>()

    val photoDetail: LiveData<ListOfPhotos>
        get() = _photoDetail


    init {
        _photoDetail.value = data
    }

}