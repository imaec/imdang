package info.imdang.app.ui.insight.write.facility

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import info.imdang.component.theme.Gray300
import info.imdang.component.theme.ImdangTheme

@Composable
fun WriteInsightComplexFacilityPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray300)
    ) {
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightComplexFacilityPreview() {
    ImdangTheme {
        WriteInsightComplexFacilityPage()
    }
}
