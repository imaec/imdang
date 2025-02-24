package info.imdang.component.common.modifier

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.visible(isVisible: Boolean): Modifier = composed {
    if (isVisible) this else size(0.dp)
}

/**
 * ripple 효과가 없는 clickable
 */
fun Modifier.clickableWithoutRipple(
    onClick: () -> Unit
): Modifier = composed {
    clickable(
        enabled = true,
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = { onClick() }
    )
}

/**
 * 점선 Stroke
 */
fun Modifier.dashedBorder(width: Dp, radius: Dp, color: Color) =
    drawBehind {
        drawRoundRect(
            color = color,
            style = Stroke(
                width = width.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(4.dp.toPx(), 2.dp.toPx()),
                    0f
                )
            ),
            cornerRadius = CornerRadius(radius.toPx())
        )
    }
