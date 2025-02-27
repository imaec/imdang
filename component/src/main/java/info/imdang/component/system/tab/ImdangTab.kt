package info.imdang.component.system.tab

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_14_19_6
import kotlin.math.max

@Composable
internal fun ImdangTab(
    modifier: Modifier = Modifier,
    selected: Boolean,
    enabled: Boolean = true,
    selectedContentColor: Color = Gray900,
    unselectedContentColor: Color = Gray500,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    tab: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null
) {
    ImdangTab(
        modifier = modifier,
        selected = selected,
        enabled = enabled,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        interactionSource = interactionSource,
        onClick = onClick,
        content = {
            TabBaselineLayout(
                icon = icon,
                text = tab
            )
        }
    )
}

@Composable
internal fun ImdangTab(
    modifier: Modifier = Modifier,
    selected: Boolean,
    enabled: Boolean = true,
    selectedContentColor: Color = Gray900,
    unselectedContentColor: Color = Gray500,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    TabTransition(
        activeColor = selectedContentColor,
        inactiveColor = unselectedContentColor,
        selected = selected
    ) {
        Column(
            modifier = modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Tab,
                    interactionSource = interactionSource,
                    indication = ripple(true, Dp.Unspecified, Color.Unspecified)
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = content
        )
    }
}

@Composable
private fun TabTransition(
    activeColor: Color,
    inactiveColor: Color,
    selected: Boolean,
    content: @Composable () -> Unit
) {
    val transition = updateTransition(selected)
    val color by transition.animateColor(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = TAB_FADE_IN_ANIMATION_DURATION,
                    delayMillis = TAB_FADE_IN_ANIMATION_DELAY,
                    easing = LinearEasing
                )
            } else {
                tween(
                    durationMillis = TAB_FADE_OUT_ANIMATION_DURATION,
                    easing = LinearEasing
                )
            }
        }
    ) {
        if (it) activeColor else inactiveColor
    }
    CompositionLocalProvider(LocalContentColor provides color, content = content)
}

@Composable
private fun TabBaselineLayout(
    text: @Composable (() -> Unit)?,
    icon: @Composable (() -> Unit)?
) {
    Layout(
        modifier = Modifier.padding(horizontal = HorizontalTabPadding),
        content = {
            if (text != null) {
                Box(
                    Modifier
                        .layoutId("text")
                        .padding(horizontal = HorizontalTextPadding)
                ) { text() }
            }
            if (icon != null) {
                Box(Modifier.layoutId("icon")) { icon() }
            }
        }
    ) { measurables, constraints ->
        val textPlaceable = text?.let {
            measurables.first { it.layoutId == "text" }.measure(
                constraints.copy(minHeight = 0)
            )
        }

        val iconPlaceable = icon?.let {
            measurables.first { it.layoutId == "icon" }.measure(constraints)
        }

        val tabWidth = max(textPlaceable?.width ?: 0, iconPlaceable?.width ?: 0)

        val tabHeight = if (textPlaceable != null && iconPlaceable != null) {
            LargeTabHeight
        } else {
            SmallTabHeight
        }.roundToPx()
        val indicatorGap = IndicatorGap.roundToPx()

        val firstBaseline = textPlaceable?.get(FirstBaseline)
        val lastBaseline = textPlaceable?.get(LastBaseline)

        layout(tabWidth, tabHeight) {
            when {
                textPlaceable != null && iconPlaceable != null -> placeTextAndIcon(
                    density = this@Layout,
                    textPlaceable = textPlaceable,
                    iconPlaceable = iconPlaceable,
                    tabWidth = tabWidth,
                    tabHeight = tabHeight,
                    firstBaseline = firstBaseline!!,
                    lastBaseline = lastBaseline!!
                )

                textPlaceable != null -> placeTextOrIcon(textPlaceable, tabHeight, indicatorGap)
                iconPlaceable != null -> placeTextOrIcon(iconPlaceable, tabHeight, indicatorGap)
                else -> {
                }
            }
        }
    }
}

private fun Placeable.PlacementScope.placeTextOrIcon(
    textOrIconPlaceable: Placeable,
    tabHeight: Int,
    indicatorGap: Int = 0
) {
    val contentY = if (indicatorGap == 0) {
        (tabHeight - textOrIconPlaceable.height) / 2
    } else {
        tabHeight - textOrIconPlaceable.height - indicatorGap
    }
    textOrIconPlaceable.placeRelative(0, contentY)
}

private fun Placeable.PlacementScope.placeTextAndIcon(
    density: Density,
    textPlaceable: Placeable,
    iconPlaceable: Placeable,
    tabWidth: Int,
    tabHeight: Int,
    firstBaseline: Int,
    lastBaseline: Int
) {
    val baselineOffset = if (firstBaseline == lastBaseline) {
        SingleLineTextBaselineWithIcon
    } else {
        DoubleLineTextBaselineWithIcon
    }
    val textOffset = with(density) {
        baselineOffset.roundToPx() + TabRowDefaults.IndicatorHeight.roundToPx()
    }
    val iconOffset = with(density) {
        iconPlaceable.height + IconDistanceFromBaseline.roundToPx() - firstBaseline
    }

    val textPlaceableX = (tabWidth - textPlaceable.width) / 2
    val textPlaceableY = tabHeight - lastBaseline - textOffset
    textPlaceable.placeRelative(textPlaceableX, textPlaceableY)

    val iconPlaceableX = (tabWidth - iconPlaceable.width) / 2
    val iconPlaceableY = textPlaceableY - iconOffset
    iconPlaceable.placeRelative(iconPlaceableX, iconPlaceableY)
}

private val SmallTabHeight = 44.dp
private val LargeTabHeight = 72.dp
private val IndicatorGap = 11.dp

private const val TAB_FADE_IN_ANIMATION_DURATION = 150
private const val TAB_FADE_IN_ANIMATION_DELAY = 100
private const val TAB_FADE_OUT_ANIMATION_DURATION = 100

internal val HorizontalTabPadding = 12.dp
private val HorizontalTextPadding = 0.dp

private val SingleLineTextBaselineWithIcon = 14.dp
private val DoubleLineTextBaselineWithIcon = 6.dp
private val IconDistanceFromBaseline = 20.sp

@Preview(showBackground = true)
@Composable
private fun ImdangTabPreview() {
    ImdangTheme {
        ImdangTab(
            modifier = Modifier.padding(vertical = 20.dp),
            selected = true,
            selectedContentColor = Gray900,
            unselectedContentColor = Gray500,
            onClick = {},
            tab = {
                Text(text = "Text", style = T600_14_19_6)
            }
        )
    }
}
