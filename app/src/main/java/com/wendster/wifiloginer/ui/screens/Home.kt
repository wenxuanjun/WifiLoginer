package com.wendster.wifiloginer.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.wendster.wifiloginer.R
import com.wendster.wifiloginer.ui.model.MainViewModel
import kotlinx.coroutines.delay

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Home(viewModel: MainViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(Modifier.width(300.dp)) {
            HomeAlert(
                text = stringResource(R.string.ui_login_success),
                icon = Icons.Rounded.Check,
                color = MaterialTheme.colorScheme.primary,
                viewModel = viewModel,
                visible = viewModel.wifiLoginStatus.value == MainViewModel.WifiLoginStatus.SUCCESS
            )
            HomeAlert(
                text = stringResource(R.string.ui_login_failed_identity),
                icon = Icons.Rounded.AccountCircle,
                color = MaterialTheme.colorScheme.error,
                viewModel = viewModel,
                visible = viewModel.wifiLoginStatus.value == MainViewModel.WifiLoginStatus.IDENTITY_FAILED
            )
            HomeAlert(
                text = stringResource(R.string.ui_login_failed_otherErrors),
                icon = Icons.Rounded.Info,
                color = MaterialTheme.colorScheme.error,
                viewModel = viewModel,
                visible = viewModel.wifiLoginStatus.value == MainViewModel.WifiLoginStatus.OTHER_ERRORS
            )
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = viewModel.appUsername.value,
                        singleLine = true,
                        onValueChange = { viewModel.appUsername.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(R.string.ui_username)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.AccountCircle,
                                contentDescription = stringResource(R.string.ui_username)
                            )
                        }
                    )
                    OutlinedTextField(
                        value = viewModel.appPassword.value,
                        singleLine = true,
                        onValueChange = { viewModel.appPassword.value = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        label = { Text(stringResource(R.string.ui_password)) },
                        visualTransformation = if (viewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Password,
                                contentDescription = stringResource(R.string.ui_password)
                            )
                        },
                        trailingIcon = {
                            IconButton({ viewModel.passwordVisible.value = !viewModel.passwordVisible.value }) {
                                Icon(
                                    imageVector = if (viewModel.passwordVisible.value) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                                    contentDescription = if (viewModel.passwordVisible.value) "Hide password" else "Show password"
                                )
                            }
                        }
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        onClick = { viewModel.doWifiLogin() }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ExitToApp,
                            contentDescription = stringResource(R.string.ui_login),
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(stringResource(R.string.ui_login))
                    }
                }
            }
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
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun HomeAlert(text: String, icon: ImageVector, color: Color, viewModel: MainViewModel, visible: Boolean) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn() + fadeIn(initialAlpha = 0.3f),
        exit = scaleOut() + fadeOut()
    ) {
        LaunchedEffect(true) {
            delay(3000L)
            viewModel.wifiLoginStatus.value = MainViewModel.WifiLoginStatus.IDLE
        }
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = color,
            modifier = Modifier.padding(bottom = 16.dp).height(48.dp).border(BorderStroke(1.dp, Color.Unspecified)),
        ) {
            Row {
                Box(Modifier.align(Alignment.CenterVertically).padding(start = 16.dp)) { Icon(imageVector = icon, contentDescription = null) }
                Box(Modifier.weight(1f).align(Alignment.CenterVertically).padding(horizontal = 16.dp)) { Text(text) }
            }
        }
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