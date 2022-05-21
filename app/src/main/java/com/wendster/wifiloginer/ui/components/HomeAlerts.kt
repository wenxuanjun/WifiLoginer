package com.wendster.wifiloginer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wendster.wifiloginer.R
import com.wendster.wifiloginer.ui.model.MainViewModel
import kotlinx.coroutines.delay

data class AlertContent(
    val text: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun HomeAlerts(viewModel: MainViewModel) {
    val alertContent = when (viewModel.wifiLoginStatus.value) {
        MainViewModel.WifiLoginStatus.SUCCESS -> AlertContent(
            text = stringResource(R.string.ui_login_success),
            icon = Icons.Rounded.Check,
            color = MaterialTheme.colorScheme.primary
        )
        MainViewModel.WifiLoginStatus.IDENTITY_FAILED -> AlertContent(
            text = stringResource(R.string.ui_login_failed_identity),
            icon = Icons.Rounded.AccountCircle,
            color = MaterialTheme.colorScheme.error
        )
        MainViewModel.WifiLoginStatus.OTHER_ERRORS -> AlertContent(
            text = viewModel.errorLoginMessage.value,
            icon = Icons.Rounded.Info,
            color = MaterialTheme.colorScheme.error
        )
        MainViewModel.WifiLoginStatus.EMPTY_USERNAME -> AlertContent(
            text = stringResource(R.string.ui_login_empty_username),
            icon = Icons.Rounded.Info,
            color = MaterialTheme.colorScheme.error
        )
        MainViewModel.WifiLoginStatus.EMPTY_PASSWORD -> AlertContent(
            text = stringResource(R.string.ui_login_empty_password),
            icon = Icons.Rounded.Info,
            color = MaterialTheme.colorScheme.error
        )
        else -> null
    }
    alertContent?.let {
        HomeAlert(alertContent = alertContent, viewModel = viewModel)
    }
}

@Composable
fun HomeAlert(alertContent: AlertContent, viewModel: MainViewModel) {
    LaunchedEffect(true) {
        delay(3000L)
        viewModel.wifiLoginStatus.value = MainViewModel.WifiLoginStatus.IDLE
    }
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = alertContent.color,
        modifier = Modifier.padding(bottom = 16.dp).height(48.dp).border(BorderStroke(1.dp, Color.Unspecified)),
    ) {
        Row {
            Box(Modifier.align(Alignment.CenterVertically).padding(start = 16.dp)) { Icon(imageVector = alertContent.icon, contentDescription = null) }
            Box(Modifier.weight(1f).align(Alignment.CenterVertically).padding(horizontal = 16.dp)) { Text(alertContent.text) }
        }
    }
}