package info.imdang.app.ui.insight.detail

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.imdang.app.model.insight.InsightDetailStatus
import info.imdang.app.ui.insight.detail.preview.FakeInsightDetailViewModel
import info.imdang.component.common.image.Icon
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

@Composable
fun InsightDetailInvisibleItem(
    viewModel: InsightDetailViewModel
) {
    val insightDetailStatus =
        viewModel.insight.collectAsStateWithLifecycle().value.insightDetailStatus
    Icon(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 32.dp)
            .fillMaxWidth()
            .aspectRatio(335f / 280f),
        iconResource = when (insightDetailStatus) {
            InsightDetailStatus.EXCHANGE_REQUEST -> R.drawable.ic_insight_detail_invisible_request
            InsightDetailStatus.EXCHANGE_REQUESTED -> {
                R.drawable.ic_insight_detail_invisible_requested
            }
            InsightDetailStatus.EXCHANGE_WAITING -> R.drawable.ic_insight_detail_invisible_waiting
            else -> 0
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun InsightDetailInvisibleItemPreview() {
    ImdangTheme {
        InsightDetailInvisibleItem(viewModel = FakeInsightDetailViewModel())
    }
}
