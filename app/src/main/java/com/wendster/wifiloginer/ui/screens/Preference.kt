package com.wendster.wifiloginer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wendster.wifiloginer.R
import com.wendster.wifiloginer.ui.model.MainViewModel
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
    }
}