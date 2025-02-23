package info.imdang.component.system.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.model.SelectionVo
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray400
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.White

@Composable
fun SelectionButtons(
    items: List<SelectionVo>,
    onClick: (SelectionVo) -> Unit
) {
    val height = (((items.size + 1) / 2) * 52).dp
    val space = (((items.size - 1) / 2) * 8).dp
    LazyVerticalGrid(
        modifier = Modifier.height(height + space),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false,
        content = {
            items(items.size) { index ->
                val item = items[index]
                Box(
                    modifier = Modifier
                        .height(52.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = if (item.isSelected) Orange50 else White)
                        .border(
                            width = 1.dp,
                            color = if (item.isSelected) Orange500 else Gray100,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            onClick(item)
                        }
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = item.name,
                        style = if (item.isSelected) T600_16_22_4 else T500_16_22_4,
                        color = if (item.isSelected) Orange500 else Gray400
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SelectionButtonPreview() {
    ImdangTheme {
        SelectionButtons(items = SelectionVo.getSamples(), onClick = {})
    }
}
