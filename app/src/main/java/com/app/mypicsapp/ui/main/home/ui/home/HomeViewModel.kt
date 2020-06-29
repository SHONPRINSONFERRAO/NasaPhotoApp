package com.app.mypicsapp.ui.main.home.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mypicsapp.data.model.ListOfPhotos
import com.app.mypicsapp.data.model.PhotoDataModel
import com.app.mypicsapp.data.repository.Repository
import com.app.mypicsapp.util.Resource
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    private val photos = MutableLiveData<Resource<ArrayList<ListOfPhotos>>>()
    private val photoList = ArrayList<ListOfPhotos>()
    private var _page = MutableLiveData<Int>()

    val page : LiveData<Int>
        get() = _page

    init {
        _page.value = 1
        getPhotos(1)
    }

    fun setPage()
    {
        _page.value = _page.value?.plus(1)
    }

    fun getPhotos(page: Int) = viewModelScope.launch {
        photos.postValue(Resource.loading(data = null))
        try {
            val photosFromApi = repository.fetchPhotos(page)
            addPhotos(photosFromApi)
            photos.postValue(Resource.success(photoList))
        } catch (exception: Exception) {
                photos.postValue(Resource.error(exception.message ?: "Error Occurred!", null))
        }
    }

    private fun addPhotos(photosFromApi: PhotoDataModel) {
        photoList.addAll(photosFromApi.hits)
    }

    fun getPhotos(): LiveData<Resource<ArrayList<ListOfPhotos>>> = photos
}