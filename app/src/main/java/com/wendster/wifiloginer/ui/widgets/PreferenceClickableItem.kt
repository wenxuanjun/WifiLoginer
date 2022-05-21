package com.wendster.wifiloginer.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import lantian.nolitter.view.widgets.PreferenceBase

@Composable
fun PreferenceClickableItem(
    text: String, secondaryText: String? = null, enabled: Boolean = true,
    modifier: Modifier = Modifier, onClick: (() -> Unit)? = null, icon: (@Composable () -> Unit)? = null
) {
    if (enabled) {
        PreferenceBase(
            text = text, icon = icon, secondaryText = secondaryText,
            modifier = modifier.clickable { if (onClick != null) onClick() }
        )
    }
    else {
        PreferenceBase(
            text = text, icon = icon, secondaryText = secondaryText,
            modifier = modifier.alpha(0.5f)
        )
    }
}