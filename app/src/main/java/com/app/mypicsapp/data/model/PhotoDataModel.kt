package com.app.mypicsapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PhotoDataModel(
    val hits: ArrayList<ListOfPhotos> = arrayListOf()
)

@Parcelize
data class ListOfPhotos(
    val id: Int,
    val pageURL: String,
    val tags: String,
    val largeImageURL: String,
    val fullHDURL: String,
    val previewURL: String,
    val views: String,
    val webformatURL: String,
    val downloads: String,
    val favorites: String,
    val likes: String,
    val comments: String,
    val user: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val type: String


): Parcelable
