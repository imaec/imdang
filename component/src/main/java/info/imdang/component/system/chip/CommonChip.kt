package info.imdang.component.system.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.White

@Composable
fun CommonChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .background(
                color = if (isSelected) Orange500 else White,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Orange500 else Gray100,
                shape = CircleShape
            )
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        text = text,
        style = T500_14_19_6,
        color = if (isSelected) White else Gray500
    )
}

@Preview(showBackground = true)
@Composable
private fun CommonChipPreview() {
    ImdangTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CommonChip(
                text = "선택",
                isSelected = true,
                onClick = {}
            )
            CommonChip(
                text = "미선택1",
                isSelected = false,
                onClick = {}
            )
            CommonChip(
                text = "미선택2",
                isSelected = false,
                onClick = {}
            )
            CommonChip(
                text = "미선택3",
                isSelected = false,
                onClick = {}
            )
        }
    }
}
