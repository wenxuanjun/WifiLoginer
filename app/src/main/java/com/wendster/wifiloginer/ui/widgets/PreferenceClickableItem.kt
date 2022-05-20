package lantian.nolitter.view.widgets

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PreferenceClickableItem(
    text: String, secondaryText: String? = null, modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null, icon: (@Composable () -> Unit)? = null
) {
    PreferenceBase(
        text = text, icon = icon, secondaryText = secondaryText,
        modifier = modifier.clickable { if (onClick != null) onClick() }
    )
}