package com.wendster.wifiloginer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.wendster.wifiloginer.R
import com.wendster.wifiloginer.ui.model.MainViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeLoginCard(viewModel: MainViewModel) {
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
}
