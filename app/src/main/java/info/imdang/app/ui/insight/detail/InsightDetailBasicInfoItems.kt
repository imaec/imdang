package info.imdang.app.ui.insight.detail

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.overlay.OverlayImage
import info.imdang.app.common.util.launchNaverMap
import info.imdang.app.model.insight.InsightDetailItem
import info.imdang.app.ui.insight.detail.common.InsightDetailItem
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

@Composable
fun InsightDetailBasicInfoItems(
    basicInfo: InsightDetailItem.BasicInfo,
    isVisibleMap: Boolean,
    onClickBack: () -> Unit
) {
    Column {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            InsightDetailItem(
                icon = R.drawable.ic_pin_gray,
                title = stringResource(R.string.complex_address),
                content = basicInfo.address
            )
            InsightDetailMapView(
                basicInfo = basicInfo,
                isVisibleMap = isVisibleMap,
                onClickBack = onClickBack
            )
            InsightDetailItem(
                icon = R.drawable.ic_calendar,
                title = stringResource(R.string.visit_date_and_time),
                content = "${basicInfo.visitAt} / ${basicInfo.visitTimes}"
            )
            InsightDetailItem(
                icon = R.drawable.ic_car,
                title = stringResource(R.string.traffic_method),
                content = basicInfo.visitMethods
            )
            InsightDetailItem(
                icon = R.drawable.ic_info,
                title = stringResource(R.string.access_limit),
                content = basicInfo.access
            )
            InsightDetailItem(
                title = stringResource(R.string.insight_summary),
                content = basicInfo.summary
            )
        }
        HorizontalDivider(thickness = 8.dp, color = Gray50)
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun InsightDetailMapView(
    basicInfo: InsightDetailItem.BasicInfo,
    isVisibleMap: Boolean,
    onClickBack: () -> Unit
) {
    BackHandler {
        onClickBack()
    }

    if (basicInfo.latitude != 0.0) {
        val context = LocalContext.current
        val alpha by animateFloatAsState(
            targetValue = if (isVisibleMap) 1f else 0f,
            animationSpec = tween(durationMillis = 700)
        )
        val mapUiSettings by remember {
            mutableStateOf(
                MapUiSettings(
                    isScrollGesturesEnabled = false,
                    isZoomGesturesEnabled = false,
                    isTiltGesturesEnabled = false,
                    isRotateGesturesEnabled = false,
                    isStopGesturesEnabled = false,
                    isCompassEnabled = false,
                    isScaleBarEnabled = false,
                    isZoomControlEnabled = false,
                    isLogoClickEnabled = false
                )
            )
        }
        val cameraPositionState = rememberSaveable(saver = CameraPositionState.Saver) {
            CameraPositionState().apply {
                position = CameraPosition(
                    LatLng(basicInfo.latitude + 0.0003, basicInfo.longitude),
                    14.5
                )
            }
        }
        NaverMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
                .alpha(alpha),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState,
            onMapClick = { _, _ ->
                val name = basicInfo.address
                    .split("\n")
                    .last()
                    .replace("(", "")
                    .replace(")", "")
                launchNaverMap(
                    context = context,
                    name = name,
                    latitude = basicInfo.latitude,
                    longitude = basicInfo.longitude
                )
            }
        ) {
            Marker(
                state = MarkerState(position = LatLng(basicInfo.latitude, basicInfo.longitude)),
                icon = OverlayImage.fromResource(R.drawable.ic_pin),
                width = 32.dp,
                height = 32.dp
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InsightDetailBasicInfoItemsPreview() {
    ImdangTheme {
        InsightDetailBasicInfoItems(
            basicInfo = InsightDetailItem.BasicInfo(
                mainImage = null,
                title = "초역세권 대단지 아파트 후기",
                address = "서울특별시 영등포구 당산 2동 123-467\n당산아이파크1차",
                latitude = 0.0,
                longitude = 0.0,
                visitAt = "2024.01.01",
                visitTimes = "아침, 저녁",
                visitMethods = "자차, 도보",
                access = "자유로움",
                summary = "단지는 전반적으로 관리 상태가 양호했으며," +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
            ),
            isVisibleMap = true,
            onClickBack = {}
        )
    }
}
