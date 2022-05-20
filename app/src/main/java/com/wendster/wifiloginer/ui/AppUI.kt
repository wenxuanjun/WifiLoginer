package com.wendster.wifiloginer.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Router
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.wendster.wifiloginer.R
import com.wendster.wifiloginer.ui.model.MainViewModel
import lantian.nolitter.view.theme.ApplicationTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppUi(viewModel: MainViewModel) {
    ApplicationTheme(viewModel.appTheme.value) {
        val navController = rememberNavController()
        var selectedScreen by remember { mutableStateOf("Home") }
        Scaffold(
            topBar = { SmallTopAppBar({ Text(stringResource(R.string.app_name)) }) },
            content = { innerPadding -> Router(innerPadding, viewModel, navController) },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Rounded.Router, contentDescription = null) },
                        label = { Text(stringResource(R.string.ui_bottomBar_login)) },
                        selected = selectedScreen == "Home",
                        onClick = { selectedScreen = "Home"; navController.navigate("Home") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
                        label = { Text(stringResource(R.string.ui_bottomBar_preference)) },
                        selected = selectedScreen == "Preference",
                        onClick = { selectedScreen = "Preference"; navController.navigate("Preference") }
                    )
                }
            }
        )
    }
}