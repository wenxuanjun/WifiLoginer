package com.wendster.wifiloginer.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wendster.wifiloginer.R
import com.wendster.wifiloginer.ui.model.MainViewModel

@Composable
fun HomeDialogs(viewModel: MainViewModel) {
    if (viewModel.wifiLoginStatus.value == MainViewModel.WifiLoginStatus.WIFI_NOT_CONNECTED) {
        HomeDialog(
            viewModel = viewModel,
            title = stringResource(R.string.ui_login_failed_wifiNotConnected),
            content = stringResource(R.string.ui_login_failed_wifiNotConnected_description)
        )
    }
    if (viewModel.wifiLoginStatus.value == MainViewModel.WifiLoginStatus.GET_NETWORK_STATUS_FAILED) {
        HomeDialog(
            viewModel = viewModel,
            title = stringResource(R.string.ui_login_failed_getNetworkStatus),
            content = stringResource(R.string.ui_login_failed_getNetworkStatus_description)
        )
    }
}

@Composable
fun HomeDialog(title: String, content: String, viewModel: MainViewModel) {
    AlertDialog(
        title = { Text(title) }, text = { Text(content) },
        onDismissRequest = { viewModel.wifiLoginStatus.value = MainViewModel.WifiLoginStatus.IDLE },
        confirmButton = {
            TextButton({ viewModel.wifiLoginStatus.value = MainViewModel.WifiLoginStatus.IDLE }) {
                Text(stringResource(R.string.dialog_confirm))
            }
        }
    )
}