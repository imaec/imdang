package info.imdang.app.ui.insight.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.common.image.Icon
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

@Composable
fun InsightDetailInvisibleItem(
    @DrawableRes icon: Int
) {
    Icon(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 32.dp)
            .fillMaxWidth()
            .aspectRatio(335f / 280f),
        iconResource = icon
    )
}

@Preview(showBackground = true)
@Composable
private fun InsightDetailInvisibleItemPreview() {
    ImdangTheme {
        InsightDetailInvisibleItem(
            icon = R.drawable.ic_insight_detail_invisible_request
        )
    }
}
