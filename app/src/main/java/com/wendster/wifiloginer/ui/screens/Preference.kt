package com.wendster.wifiloginer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Router
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.wendster.wifiloginer.R
import com.wendster.wifiloginer.ui.model.MainViewModel
import com.wendster.wifiloginer.ui.widgets.PreferenceClickableItem
import com.wendster.wifiloginer.ui.widgets.PreferenceEditText
import com.wendster.wifiloginer.ui.widgets.PreferenceSwitch

@Composable
fun Preference(viewModel: MainViewModel) {
    Column {
        PreferenceSwitch(
            text = stringResource(R.string.ui_remember_password),
            secondaryText = stringResource(R.string.ui_remember_password_description),
            defaultValue = viewModel.rememberPassword.value,
            onChange = { viewModel.onRememberPasswordChange() },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = stringResource(R.string.ui_remember_password)
                )
            }
        )
        PreferenceSwitch(
            text = stringResource(R.string.ui_customize_loginUrl),
            secondaryText = stringResource(R.string.ui_customize_loginUrl_description),
            defaultValue = viewModel.customLoginUri.value,
            onChange = { viewModel.customLoginUri.value = !viewModel.customLoginUri.value },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Router,
                    contentDescription = stringResource(R.string.ui_customize_loginUrl)
                )
            }
        )
        PreferenceEditText(
            text = stringResource(R.string.ui_loginUrl),
            secondaryText = viewModel.loginUri.value,
            onSubmit = { viewModel.loginUri.value = it },
            dialogTitle = stringResource(R.string.ui_loginUrl),
            dialogDefaultContent = viewModel.loginUri.value,
            enabled = viewModel.customLoginUri.value,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = stringResource(R.string.ui_customize_loginUrl)
                )
            }
        )
        Divider(modifier = Modifier.alpha(0.2f))
        PreferenceClickableItem(
            text = stringResource(R.string.ui_source_code),
            secondaryText = "https://github.com/wenxuanjun/WifiLoginer",
            onClick = { viewModel.intentToWebsite("https://github.com/wenxuanjun/WifiLoginer") },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Code,
                    contentDescription = stringResource(R.string.ui_source_code)
                )
            }
        )
    }
}