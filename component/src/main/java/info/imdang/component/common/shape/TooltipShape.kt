package info.imdang.component.common.shape

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import info.imdang.component.common.tooltip.Tooltip
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

enum class TooltipDirection {
    UP,
    DOWN
}

class TooltipShape(
    val radius: Dp,
    val tailWidth: Dp,
    val tailHeight: Dp,
    val direction: TooltipDirection = TooltipDirection.UP,
    val tailStartPadding: Dp? = null
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
        val height = size.height
        val corner = with(density) { radius.toPx() }
        val tailWidth = with(density) { tailWidth.toPx() }
        val tailHeight = with(density) { tailHeight.toPx() }
        val tailCenter = with(density) {
            tailStartPadding?.run {
                when {
                    toPx() < corner -> corner + tailWidth / 2
                    toPx() + tailWidth > width - corner -> width - corner - tailWidth / 2
                    else -> toPx() + tailWidth / 2
                }
            } ?: (width / 2)
        }
        val tailStart = tailCenter - tailWidth / 2
        val tailEnd = tailCenter + tailWidth / 2

        return Outline.Generic(
            path = when (direction) {
                TooltipDirection.UP -> upTooltipPath(
                    width = width,
                    height = height,
                    corner = corner,
                    tailHeight = tailHeight,
                    tailCenter = tailCenter,
                    tailStart = tailStart,
                    tailEnd = tailEnd
                )
                TooltipDirection.DOWN -> downTooltipPath(
                    width = width,
                    height = height,
                    corner = corner,
                    tailHeight = tailHeight,
                    tailCenter = tailCenter,
                    tailStart = tailStart,
                    tailEnd = tailEnd
                )
            }
        )
    }
}

private fun upTooltipPath(
    width: Float,
    height: Float,
    corner: Float,
    tailHeight: Float,
    tailCenter: Float,
    tailStart: Float,
    tailEnd: Float
) = Path().apply {
    // 왼쪽 상단 부터 시작
    moveTo(0f + corner, tailHeight)
    lineTo(tailStart, tailHeight)

    // 꼬리 (위쪽 삼각형)
    lineTo(tailCenter, 0f)
    lineTo(tailEnd, tailHeight)

    // 오른쪽 상단 둥근 모서리
    lineTo(width - corner, tailHeight)
    arcTo(
        rect = Rect(
            left = width - 2 * corner,
            right = width,
            top = tailHeight,
            bottom = tailHeight + 2 * corner
        ),
        startAngleDegrees = -90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // 오른쪽 하단 둥근 모서리
    lineTo(width, height - corner)
    arcTo(
        rect = Rect(
            left = width - 2 * corner,
            right = width,
            top = height - 2 * corner,
            bottom = height
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // 왼쪽 하단 둥근 모서리
    lineTo(corner, height)
    arcTo(
        rect = Rect(
            left = 0f,
            right = 2 * corner,
            top = height - 2 * corner,
            bottom = height
        ),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // 왼쪽 상단 둥근 모서리
    lineTo(0f, height + corner)
    arcTo(
        rect = Rect(
            left = 0f,
            right = 2 * corner,
            top = tailHeight,
            bottom = tailHeight + 2 * corner
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
}

private fun downTooltipPath(
    width: Float,
    height: Float,
    corner: Float,
    tailHeight: Float,
    tailCenter: Float,
    tailStart: Float,
    tailEnd: Float
) = Path().apply {
    // 왼쪽 상단 부터 시작
    moveTo(0f + corner, 0f)

    // 오른쪽 상단 둥근 모서리
    lineTo(width - corner, 0f)
    arcTo(
        rect = Rect(
            left = width - 2 * corner,
            right = width,
            top = 0f,
            bottom = 2 * corner
        ),
        startAngleDegrees = -90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // 오른쪽 하단 둥근 모서리
    lineTo(width, (height - tailHeight) - corner)
    arcTo(
        rect = Rect(
            left = width - 2 * corner,
            right = width,
            top = (height - tailHeight) - 2 * corner,
            bottom = (height - tailHeight)
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // 꼬리 (위쪽 삼각형)
    lineTo(tailEnd, height - tailHeight)
    lineTo(tailCenter, height)
    lineTo(tailStart, height - tailHeight)

    // 왼쪽 하단 둥근 모서리
    lineTo(corner, (height - tailHeight))
    arcTo(
        rect = Rect(
            left = 0f,
            right = 2 * corner,
            top = (height - tailHeight) - 2 * corner,
            bottom = (height - tailHeight)
        ),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // 왼쪽 상단 둥근 모서리
    lineTo(0f, (height - tailHeight) + corner)
    arcTo(
        rect = Rect(
            left = 0f,
            right = 2 * corner,
            top = 0f,
            bottom = 2 * corner
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
}

@Preview(showBackground = true)
@Composable
fun TooltipPreview() {
    ImdangTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Tooltip(
                direction = TooltipDirection.UP,
                tailStartPadding = 0.dp,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
            Tooltip(
                direction = TooltipDirection.UP,
                tailStartPadding = 80.dp,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
            Tooltip(
                direction = TooltipDirection.UP,
                tailStartPadding = null,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
            Tooltip(
                direction = TooltipDirection.UP,
                tailStartPadding = 160.dp,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
            Tooltip(
                direction = TooltipDirection.UP,
                tailStartPadding = 300.dp,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
            Tooltip(
                direction = TooltipDirection.DOWN,
                tailStartPadding = 0.dp,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
            Tooltip(
                direction = TooltipDirection.DOWN,
                tailStartPadding = 80.dp,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
            Tooltip(
                direction = TooltipDirection.DOWN,
                tailStartPadding = null,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
            Tooltip(
                direction = TooltipDirection.DOWN,
                tailStartPadding = 160.dp,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
            Tooltip(
                direction = TooltipDirection.DOWN,
                tailStartPadding = 300.dp,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
        }
    }
}
