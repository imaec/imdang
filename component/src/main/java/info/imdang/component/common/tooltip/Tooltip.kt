package info.imdang.component.common.tooltip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import info.imdang.component.common.shape.TooltipDirection
import info.imdang.component.common.shape.TooltipShape
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.White
import info.imdang.resource.R

@Composable
fun Tooltip(
    direction: TooltipDirection,
    text: String,
    modifier: Modifier = Modifier,
    elevation: Dp = 8.dp,
    radius: Dp = 8.dp,
    tailWidth: Dp = 16.dp,
    tailHeight: Dp = 10.dp,
    tailStartPadding: Dp? = null
) {
    val tooltipShape = TooltipShape(
        direction = direction,
        radius = radius,
        tailWidth = tailWidth,
        tailHeight = tailHeight,
        tailStartPadding = tailStartPadding
    )
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = tooltipShape
            )
            .clip(tooltipShape)
            .background(Orange500)
    ) {
        Box(
            modifier = Modifier
                .padding(
                    top = when (direction) {
                        TooltipDirection.UP -> tooltipShape.tailHeight
                        TooltipDirection.DOWN -> 0.dp
                    },
                    bottom = when (direction) {
                        TooltipDirection.UP -> 0.dp
                        TooltipDirection.DOWN -> tooltipShape.tailHeight
                    }
                )
                .defaultMinSize(minHeight = 48.dp)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                style = T500_14_19_6,
                color = White
            )
        }
    }
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
