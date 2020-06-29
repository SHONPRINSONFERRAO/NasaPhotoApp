package com.app.mypicsapp.ui.main.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.mypicsapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
    }
}