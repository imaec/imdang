package info.imdang.app.ui.insight.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import info.imdang.app.ui.insight.detail.preview.FakeInsightDetailViewModel
import info.imdang.component.common.dialog.CommonDialog
import info.imdang.component.common.image.Icon
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T700_22_30_8
import info.imdang.component.theme.White
import info.imdang.resource.R

@Composable
fun InsightDetailCollapsingContent(viewModel: InsightDetailViewModel) {
    var isShowRecommendInfoDialog by remember { mutableStateOf(false) }
    val insight by viewModel.insight.collectAsStateWithLifecycle()

    if (isShowRecommendInfoDialog) {
        CommonDialog(
            iconResource = R.drawable.ic_sign_for_dialog,
            message = stringResource(R.string.recommend_info_message),
            onClickPositiveButton = {
                isShowRecommendInfoDialog = false
            },
            onDismiss = {
                isShowRecommendInfoDialog = false
            }
        )
    }

    Column {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            painter = rememberAsyncImagePainter(
                model = insight.mainImage,
                contentScale = ContentScale.Crop
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        iconResource = R.drawable.ic_profile
                    )
                    Text(
                        text = insight.nickname,
                        style = T500_16_22_4,
                        color = Gray700
                    )
                }
                Row(
                    modifier = Modifier
                        .height(36.dp)
                        .background(color = White, shape = RoundedCornerShape(4.dp))
                        .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                            // todo : 추천
                            isShowRecommendInfoDialog = true
                        }
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        iconResource = R.drawable.ic_good,
                        tint = Gray700
                    )
                    Text(
                        text = "추천 ${insight.recommendedCount}",
                        style = T500_14_19_6,
                        color = Gray700
                    )
                }
            }
            Text(
                text = insight.title,
                style = T700_22_30_8,
                color = Gray900
            )
        }
        HorizontalDivider(color = Gray100)
    }
}

@Preview(showBackground = true)
@Composable
private fun InsightDetailCollapsingContentPreview() {
    ImdangTheme {
        InsightDetailCollapsingContent(viewModel = FakeInsightDetailViewModel())
    }
}

@Preview(showBackground = true)
@Composable
private fun RecommendInfoDialogPreview() {
    ImdangTheme {
        CommonDialog(
            iconResource = R.drawable.ic_sign_for_dialog,
            message = stringResource(R.string.recommend_info_message)
        )
    }
}
