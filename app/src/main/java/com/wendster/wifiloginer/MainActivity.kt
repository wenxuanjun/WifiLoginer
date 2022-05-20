package com.wendster.wifiloginer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.wendster.wifiloginer.ui.AppUi
import com.wendster.wifiloginer.ui.model.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppUi(ViewModelProvider(this)[MainViewModel::class.java]) }
    }
}