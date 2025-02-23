package info.imdang.app.ui.insight.write.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.app.ui.insight.write.common.WriteInsightDetailContentView
import info.imdang.app.ui.insight.write.common.WriteInsightSelectionButtons
import info.imdang.app.ui.insight.write.common.WriteInsightTitle
import info.imdang.component.common.image.Icon
import info.imdang.component.model.SelectionVo
import info.imdang.component.system.textfield.CommonTextField
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray200
import info.imdang.component.theme.Gray400
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T600_12_16_8
import info.imdang.component.theme.White
import info.imdang.resource.R
import kotlinx.coroutines.launch

@Composable
fun WriteInsightBasicInfoPage() {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var isTitleFocused by remember { mutableStateOf(false) }
    var isVisitDateFocused by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect {
                if (isTitleFocused || isVisitDateFocused) {
                    focusManager.clearFocus()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 24.dp,
            bottom = 40.dp
        ),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        item {
            CoverImageView()
        }
        item {
            TitleView(
                onFocusChanged = { isFocused ->
                    coroutineScope.launch {
                        if (isFocused) listState.animateScrollToItem(index = 1)
                        isTitleFocused = isFocused
                    }
                }
            )
        }
        item {
            AddressView()
        }
        item {
            VisitDateView(
                onFocusChanged = { isFocused ->
                    coroutineScope.launch {
                        if (isFocused) listState.animateScrollToItem(index = 3)
                        isVisitDateFocused = isFocused
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.visit_time),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.morning)),
                    SelectionVo(name = stringResource(R.string.day)),
                    SelectionVo(name = stringResource(R.string.evening)),
                    SelectionVo(name = stringResource(R.string.night))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.traffic_method),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.car)),
                    SelectionVo(name = stringResource(R.string.public_traffic)),
                    SelectionVo(name = stringResource(R.string.walk))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.access_limit),
                isMultipleSelection = false,
                items = listOf(
                    SelectionVo(name = stringResource(R.string.limited)),
                    SelectionVo(name = stringResource(R.string.need_permission)),
                    SelectionVo(name = stringResource(R.string.free_access))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightDetailContentView(
                title = stringResource(R.string.insight_summary),
                hintText = stringResource(R.string.insight_summary_hint),
                isRequired = true,
                onClick = {
                    // todo : 인사이트 요약 작성 화면 이동
                }
            )
        }
    }
}

@Composable
private fun CoverImageView() {
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        WriteInsightTitle(
            title = stringResource(R.string.cover_image),
            isRequired = true
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(100.dp)
                    .background(color = Gray50, shape = RoundedCornerShape(4.dp))
                    .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(4.dp))
                    .clip(RoundedCornerShape(4.dp))
                    .clickable {
                        focusManager.clearFocus()
                        // todo : 이미지 선택 바텀시트 노출
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(20.dp),
                    iconResource = R.drawable.ic_image,
                    tint = Gray200
                )
            }
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .background(color = White, shape = RoundedCornerShape(6.dp))
                    .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(6.dp))
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {
                        focusManager.clearFocus()
                        // todo : 이미지 선택 바텀시트 노출
                    }
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    iconResource = R.drawable.ic_plus,
                    tint = Gray700
                )
                Text(
                    text = stringResource(R.string.image_add),
                    style = T600_12_16_8,
                    color = Gray700
                )
            }
        }
    }
}

@Composable
private fun TitleView(onFocusChanged: (isFocused: Boolean) -> Unit) {
    CommonTextField(
        modifier = Modifier
            .focusRequester(FocusRequester())
            .onFocusChanged {
                onFocusChanged(it.isFocused)
            },
        text = "",
        imeAction = ImeAction.Next,
        title = stringResource(R.string.title),
        isRequired = true,
        minLength = 1,
        maxLength = 10,
        onTextChanged = {
            // viewModel.updateTitle(title = it)
        }
    )
}

@Composable
private fun AddressView() {
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        WriteInsightTitle(title = stringResource(R.string.complex_address), isRequired = true)
        Box(
            modifier = Modifier
                .height(52.dp)
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    focusManager.clearFocus()
                    // todo : 지번 검색
                }
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                text = stringResource(R.string.jibun_address),
                style = T500_16_22_4,
                color = Gray400
            )
        }
        Box(
            modifier = Modifier
                .height(52.dp)
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                text = stringResource(R.string.complex_name),
                style = T500_16_22_4,
                color = Gray400
            )
        }
    }
}

@Composable
private fun VisitDateView(onFocusChanged: (isFocused: Boolean) -> Unit) {
    CommonTextField(
        modifier = Modifier
            .focusRequester(FocusRequester())
            .onFocusChanged {
                onFocusChanged(it.isFocused)
            },
        text = "",
        hintText = stringResource(R.string.visit_date_hint),
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done,
        title = stringResource(R.string.visit_date),
        maxLength = 10,
        isRequired = true,
        onTextChanged = {
            // viewModel.updateTitle(title = it)
        }
    )
}

@Preview(showBackground = true, heightDp = 1410)
@Composable
private fun WriteInsightBasicInfoPreview() {
    ImdangTheme {
        WriteInsightBasicInfoPage()
    }
}
