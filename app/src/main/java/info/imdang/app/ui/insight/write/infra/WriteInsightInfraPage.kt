package info.imdang.app.ui.insight.write.infra

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.ImdangTheme

@Composable
fun WriteInsightInfraPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
    ) {
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightInfraPagePreview() {
    ImdangTheme {
        WriteInsightInfraPage()
    }
}
