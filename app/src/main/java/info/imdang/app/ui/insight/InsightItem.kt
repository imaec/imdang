package info.imdang.app.ui.insight

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import info.imdang.component.common.image.Icon
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_12_16_8
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T500_16_22_4
import info.imdang.resource.R

enum class InsightItemType {
    HORIZONTAL,
    VERTICAL
}

@Composable
fun InsightItem(
    itemType: InsightItemType,
    coverImage: String?,
    region: String,
    recommendCount: Int,
    title: String,
    nickname: String,
    onClick: () -> Unit
) {
    when (itemType) {
        InsightItemType.HORIZONTAL -> InsightHorizontalItem(
            coverImage = coverImage,
            region = region,
            recommendCount = recommendCount,
            title = title,
            nickname = nickname,
            onClick = onClick
        )
        InsightItemType.VERTICAL -> InsightVerticalItem(
            coverImage = coverImage,
            region = region,
            recommendCount = recommendCount,
            title = title,
            nickname = nickname,
            onClick = onClick
        )
    }
}

@Composable
private fun InsightHorizontalItem(
    coverImage: String?,
    region: String,
    recommendCount: Int,
    title: String,
    nickname: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InsightImageView(coverImage = coverImage, width = 100.dp, height = 100.dp)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            InsightRegionRecommendView(region = region, recommendCount = recommendCount)
            InsightTitleView(title = title)
            InsightMemberView(nickname = nickname)
        }
    }
}

@Composable
private fun InsightVerticalItem(
    coverImage: String?,
    region: String,
    recommendCount: Int,
    title: String,
    nickname: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InsightImageView(coverImage = coverImage, width = 200.dp, height = 160.dp)
        InsightRegionRecommendView(region = region, recommendCount = recommendCount)
        InsightTitleView(title = title)
        InsightMemberView(nickname = nickname)
    }
}

@Composable
private fun InsightImageView(coverImage: String?, width: Dp, height: Dp) {
    Image(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(4.dp))
            .background(
                color = Gray50,
                shape = RoundedCornerShape(4.dp)
            ),
        painter = rememberAsyncImagePainter(
            model = coverImage,
            contentScale = ContentScale.Crop
        ),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Composable
private fun InsightRegionRecommendView(region: String, recommendCount: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier
                .height(25.dp)
                .background(color = Gray50, shape = RoundedCornerShape(2.dp))
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(12.dp),
                iconResource = R.drawable.ic_pin_gray,
                tint = Gray700
            )
            Text(text = region, style = T500_12_16_8, color = Gray700)
        }
        Box(
            modifier = Modifier
                .height(25.dp)
                .background(color = Orange50, shape = RoundedCornerShape(2.dp))
                .padding(horizontal = 8.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "추천 $recommendCount",
                style = T500_12_16_8,
                color = Orange500
            )
        }
    }
}

@Composable
private fun InsightTitleView(title: String) {
    Text(text = title, style = T500_16_22_4, color = Gray900)
}

@Composable
private fun InsightMemberView(nickname: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(modifier = Modifier.size(24.dp), iconResource = R.drawable.ic_profile)
        Text(text = nickname, style = T500_14_19_6, color = Gray700)
    }
}

@Preview(showBackground = true)
@Composable
private fun InsightItemPreview() {
    ImdangTheme {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            InsightItem(
                itemType = InsightItemType.HORIZONTAL,
                coverImage = "",
                region = "강남구 신논현동",
                recommendCount = 24,
                title = "초역세권 대단지 아파트 후기",
                nickname = "홍길동",
                onClick = {}
            )
            InsightItem(
                itemType = InsightItemType.VERTICAL,
                coverImage = "",
                region = "강남구 신논현동",
                recommendCount = 24,
                title = "초역세권 대단지 아파트 후기",
                nickname = "홍길동",
                onClick = {}
            )
        }
    }
}
