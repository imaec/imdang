package info.imdang.ui.main.home.exchange

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import info.imdang.component.theme.ImdangTheme

@Composable
fun HomeExchangePage() {
    Box {
        Text("exchange")
    }
}

@Preview
@Composable
private fun HomeExchangePagePreview() {
    ImdangTheme {
        HomeExchangePage()
    }
}
