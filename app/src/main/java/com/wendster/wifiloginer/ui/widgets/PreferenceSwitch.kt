package com.wendster.wifiloginer.ui.widgets

import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import lantian.nolitter.view.widgets.PreferenceBase

@Composable
fun PreferenceSwitch(
    text: String, secondaryText: String? = null, modifier: Modifier = Modifier,
    disabled: Boolean = false, defaultValue: Boolean = false,
    onChange: ((Boolean) -> Unit)? = null, icon: (@Composable () -> Unit)? = null
) {
    var checked by remember { mutableStateOf(defaultValue) }
    val disabledModifier = modifier.alpha(0.5f)
    val toggleableModifier = modifier.toggleable(value = checked, onValueChange = { checked = it; if (onChange != null) onChange(it) })
    PreferenceBase(
        text = text, icon = icon,
        secondaryText = secondaryText,
        trailing = { Switch(checked = checked, onCheckedChange = null) },
        modifier = if (disabled) disabledModifier else toggleableModifier
    )
}