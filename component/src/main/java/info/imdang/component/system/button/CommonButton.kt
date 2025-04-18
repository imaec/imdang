package info.imdang.component.system.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray200
import info.imdang.component.theme.Gray400
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange200
import info.imdang.component.theme.Orange300
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.White

enum class CommonButtonType {
    MAIN,
    SUB,
    GHOST
}

@Composable
fun CommonButton(
    buttonText: String,
    modifier: Modifier = Modifier,
    buttonType: CommonButtonType = CommonButtonType.MAIN,
    horizontalPadding: Dp = 20.dp,
    bottomPadding: Dp = 40.dp,
    isEnabled: Boolean = true,
    isFloating: Boolean = true,
    isClickable: Boolean = isEnabled,
    onClick: () -> Unit = {}
) {
    val animatedHorizontalPadding by animateDpAsState(
        targetValue = if (isFloating) horizontalPadding else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedBottomPadding by animateDpAsState(
        targetValue = if (isFloating) bottomPadding else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedCornerSize by animateDpAsState(
        targetValue = when (buttonType) {
            CommonButtonType.MAIN -> if (isFloating) 8.dp else 0.dp
            CommonButtonType.SUB -> 8.dp
            CommonButtonType.GHOST -> 8.dp
        },
        animationSpec = tween(durationMillis = 300)
    )
    val animatedBackgroundColor by animateColorAsState(
        targetValue = when (buttonType) {
            CommonButtonType.MAIN -> if (isEnabled) Orange500 else Gray100
            CommonButtonType.SUB -> White
            CommonButtonType.GHOST -> White
        },
        animationSpec = tween(durationMillis = 200)
    )
    val animatedBorderColor by animateColorAsState(
        targetValue = when (buttonType) {
            CommonButtonType.MAIN -> if (isEnabled) Orange500 else Gray100
            CommonButtonType.SUB -> if (isEnabled) Orange500 else Orange200
            CommonButtonType.GHOST -> if (isEnabled) Gray200 else Gray100
        },
        animationSpec = tween(durationMillis = 200)
    )
    val animatedTextColor by animateColorAsState(
        targetValue = when (buttonType) {
            CommonButtonType.MAIN -> if (isEnabled) White else Gray500
            CommonButtonType.SUB -> if (isEnabled) Orange500 else Orange300
            CommonButtonType.GHOST -> if (isEnabled) Gray700 else Gray400
        },
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier = modifier
            .padding(
                start = animatedHorizontalPadding,
                end = animatedHorizontalPadding,
                bottom = animatedBottomPadding
            )
            .background(
                color = animatedBackgroundColor,
                shape = RoundedCornerShape(animatedCornerSize)
            )
            .border(
                width = 1.dp,
                color = animatedBorderColor,
                shape = RoundedCornerShape(animatedCornerSize)
            )
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(animatedCornerSize))
            .clickable(enabled = isClickable, onClick = onClick)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = buttonText,
            style = T600_16_22_4,
            color = animatedTextColor,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, heightDp = 1100)
@Composable
private fun CommonButtonPreview() {
    ImdangTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CommonButton(
                buttonText = "Main Enabled",
                isEnabled = true,
                isFloating = false
            )
            CommonButton(
                buttonText = "Main Disabled",
                isEnabled = false,
                isFloating = false
            )
            CommonButton(
                buttonText = "Main Enabled",
                isEnabled = true
            )
            CommonButton(
                buttonText = "Main Disabled",
                isEnabled = false
            )
            CommonButton(
                buttonType = CommonButtonType.SUB,
                buttonText = "Sub Enabled",
                isEnabled = true
            )
            CommonButton(
                buttonType = CommonButtonType.SUB,
                buttonText = "Sub Disabled",
                isEnabled = false
            )
            CommonButton(
                buttonType = CommonButtonType.GHOST,
                buttonText = "Ghost Enabled",
                isEnabled = true
            )
            CommonButton(
                buttonType = CommonButtonType.GHOST,
                buttonText = "Ghost Disabled",
                isEnabled = false
            )
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CommonButton(
                    modifier = Modifier.weight(1f),
                    horizontalPadding = 0.dp,
                    buttonText = "Main Enabled",
                    isEnabled = true
                )
                CommonButton(
                    modifier = Modifier.weight(1f),
                    buttonType = CommonButtonType.SUB,
                    horizontalPadding = 0.dp,
                    buttonText = "Sub Enabled",
                    isEnabled = true
                )
            }
        }
    }
}
