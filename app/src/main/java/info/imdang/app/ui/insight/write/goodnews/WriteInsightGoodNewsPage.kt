package info.imdang.app.ui.insight.write.goodnews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import info.imdang.component.theme.ImdangTheme

@Composable
fun WriteInsightGoodNewsPage() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightGoodNewsPagePreview() {
    ImdangTheme {
        WriteInsightGoodNewsPage()
    }
}
