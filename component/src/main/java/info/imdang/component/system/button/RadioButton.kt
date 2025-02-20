package info.imdang.component.system.button

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange500

@Composable
fun RadioButton(
    isSelected: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit = {}
) {
    val radioButtonSize = 24.dp
    val radioButtonDotSize = 10.dp
    val radioButtonStokeWidth = 2.dp
    val dotRadius = animateDpAsState(
        targetValue = radioButtonDotSize / 2,
        animationSpec = tween(durationMillis = 100)
    )
    val selectableModifier = if (isEnabled) {
        Modifier.selectable(
            selected = isSelected,
            onClick = onClick,
            role = Role.RadioButton,
            interactionSource = null,
            indication = ripple(
                bounded = false,
                radius = radioButtonDotSize
            )
        )
    } else {
        Modifier
    }

    Canvas(
        modifier = Modifier
            .then(selectableModifier)
            .requiredSize(radioButtonSize)
    ) {
        val strokeWidth = radioButtonStokeWidth.toPx()
        drawCircle(
            color = Gray100,
            radius = (radioButtonSize / 2).toPx() - strokeWidth / 2,
            style = Stroke(strokeWidth)
        )
        drawCircle(
            color = if (isSelected) Orange500 else Gray100,
            radius = dotRadius.value.toPx(),
            style = Fill
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RadioButtonPreview() {
    ImdangTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            RadioButton(
                isSelected = true,
                isEnabled = true,
                onClick = {}
            )
            RadioButton(
                isSelected = false,
                isEnabled = true,
                onClick = {}
            )
        }
    }
}
