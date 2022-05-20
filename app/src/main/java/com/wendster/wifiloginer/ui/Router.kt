package com.wendster.wifiloginer.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wendster.wifiloginer.ui.model.MainViewModel
import com.wendster.wifiloginer.ui.screens.Home
import com.wendster.wifiloginer.ui.screens.Preference

@Composable
fun Router(innerPadding: PaddingValues, viewModel: MainViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Home", modifier = Modifier.padding(innerPadding)) {
        composable("Home") { Home(viewModel) }
        composable("Preference") { Preference(viewModel) }
    }
}