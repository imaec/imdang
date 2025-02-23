package info.imdang.component.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Orange500,
    secondary = Orange500,
    tertiary = Orange500,
    background = Gray25
)

@Composable
fun ImdangTheme(
    content: @Composable BoxScope.() -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = {
            Box { content() }
        }
    )
}
