package info.imdang.app.ui.insight.write.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50

@Composable
fun WriteInsightBasicInfoPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Orange50)
    ) {
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightBasicInfoPreview() {
    ImdangTheme {
        WriteInsightBasicInfoPage()
    }
}
