package com.wendster.wifiloginer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wendster.wifiloginer.ui.components.HomeAlerts
import com.wendster.wifiloginer.ui.components.HomeDialogs
import com.wendster.wifiloginer.ui.components.HomeLoginCard
import com.wendster.wifiloginer.ui.model.MainViewModel

@Composable
fun Home(viewModel: MainViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(Modifier.width(300.dp)) {
            HomeAlerts(viewModel)
            HomeLoginCard(viewModel)
            HomeDialogs(viewModel)
        }
    }
}
