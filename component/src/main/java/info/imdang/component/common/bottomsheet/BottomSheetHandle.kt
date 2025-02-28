package info.imdang.component.common.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.ImdangTheme

@Composable
fun BottomSheetHandle() {
    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .width(52.dp)
            .height(6.dp)
            .background(
                color = Color(0xFFD9D9D9),
                shape = CircleShape
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetHandlePreview() {
    ImdangTheme {
        BottomSheetHandle()
    }
}
