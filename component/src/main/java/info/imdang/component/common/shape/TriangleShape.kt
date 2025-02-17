package info.imdang.component.common.shape

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange500

enum class TriangleDirection {
    UP,
    DOWN
}

class TriangleShape(
    private val direction: TriangleDirection,
    private val width: Dp,
    private val height: Dp
) : Shape {

    constructor(direction: TriangleDirection, size: Dp) : this(
        direction = direction,
        width = size,
        height = size
    )

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = width.value * density.density
        val height = height.value * density.density
        val trianglePath = Path().apply {
            if (direction == TriangleDirection.UP) {
                moveTo(0f, height)
                lineTo(width, height)
                lineTo(width / 2, 0f)
            } else {
                moveTo(0f, 0f)
                lineTo(width, 0f)
                lineTo(width / 2, height)
            }
        }
        return Outline.Generic(trianglePath)
    }
}

@Composable
private fun TriangleBox(direction: TriangleDirection, size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(
                TriangleShape(
                    direction = direction,
                    size = size
                )
            )
            .background(Orange500)
    )
}

@Composable
private fun TriangleBox(direction: TriangleDirection, width: Dp, height: Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(
                TriangleShape(
                    direction = direction,
                    width = width,
                    height = height
                )
            )
            .background(Orange500)
    )
}

@Preview(showBackground = true)
@Composable
private fun TriangleBoxPreview() {
    ImdangTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TriangleBox(direction = TriangleDirection.UP, width = 16.dp, height = 10.dp)
            TriangleBox(direction = TriangleDirection.UP, width = 30.dp, height = 20.dp)
            TriangleBox(direction = TriangleDirection.UP, size = 50.dp)
            TriangleBox(direction = TriangleDirection.DOWN, size = 50.dp)
            TriangleBox(direction = TriangleDirection.DOWN, width = 30.dp, height = 20.dp)
            TriangleBox(direction = TriangleDirection.DOWN, width = 16.dp, height = 10.dp)
        }
    }
}
