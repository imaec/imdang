package info.imdang.app.ui.main.storage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.app.model.myinsight.MyInsightAddressVo
import info.imdang.component.common.image.Icon
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.T600_18_25_2
import info.imdang.component.theme.White
import info.imdang.resource.R

@Composable
fun StorageAddressView(
    pagerState: PagerState,
    addresses: List<MyInsightAddressVo>
) {
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 20.dp),
        pageSpacing = 12.dp
    ) { page ->
        StorageAddressPage(addressVo = addresses[page])
    }
}

@Composable
private fun StorageAddressPage(addressVo: MyInsightAddressVo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (addressVo.isSelected) Orange500 else Gray100,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(all = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                iconResource = R.drawable.ic_pin_gray,
                tint = White
            )
            Text(
                text = addressVo.toSiGuDong(),
                style = T600_18_25_2,
                color = White
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .background(
                        color = White.copy(alpha = 0.16f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.complex),
                    style = T500_14_19_6,
                    color = White
                )
                Text(
                    text = "${addressVo.complexCount}개",
                    style = T600_16_22_4,
                    color = White
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .background(
                        color = White.copy(alpha = 0.16f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.insight),
                    style = T500_14_19_6,
                    color = White
                )
                Text(
                    text = "${addressVo.insightCount}개",
                    style = T600_16_22_4,
                    color = White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StorageAddressViewPreview() {
    ImdangTheme {
        Box(modifier = Modifier.padding(vertical = 20.dp)) {
            StorageAddressView(
                pagerState = rememberPagerState { 4 },
                addresses = MyInsightAddressVo.getSamples(4)
            )
        }
    }
}
