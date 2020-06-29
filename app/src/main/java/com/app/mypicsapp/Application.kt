package com.app.mypicsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application: Application() {

    private var apiFlavor: String = "MOCK"

    fun getApiFlavor(): String? {
        return apiFlavor
    }

    fun setApiFlavor(apiFlavor: String) {
        this.apiFlavor = apiFlavor
    }
}