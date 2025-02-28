package info.imdang.app.ui.insight.detail.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.common.image.Icon
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.button.RadioButton
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_12_16_8
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T600_20_28
import info.imdang.resource.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeBottomSheet(
    sheetState: SheetState,
    onCloseBottomSheet: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedIndex by remember { mutableIntStateOf(-1) }

    Box(
        modifier = Modifier
            .height(456.dp)
            .padding(top = 16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(R.string.exchange_select_message),
                style = T600_20_28,
                color = Gray900
            )
            LazyColumn(
                modifier = Modifier.padding(bottom = 96.dp),
                contentPadding = PaddingValues(bottom = 27.dp)
            ) {
                items(10) { index ->
                    val isSelected = index == selectedIndex
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(91.dp)
                            .clickable {
                                selectedIndex = index
                            }
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (index == 0) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Row(
                                    modifier = Modifier
                                        .height(25.dp)
                                        .background(
                                            color = Orange50,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                        .padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        iconResource = R.drawable.ic_ticket
                                    )
                                    Text(
                                        text = "3개 보유",
                                        style = T500_12_16_8,
                                        color = Orange500
                                    )
                                }
                                Text(
                                    text = "아파트임당 패스권 사용",
                                    style = T500_16_22_4,
                                    color = Gray900
                                )
                            }
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Row(
                                        modifier = Modifier
                                            .height(25.dp)
                                            .background(
                                                color = Gray50,
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                            .padding(horizontal = 8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(12.dp),
                                            iconResource = R.drawable.ic_pin_gray,
                                            tint = Gray700
                                        )
                                        Text(
                                            text = "강남구 신논현동",
                                            style = T500_12_16_8,
                                            color = Gray700
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .height(25.dp)
                                            .background(
                                                color = Orange50,
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                            .padding(horizontal = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "추천 24",
                                            style = T500_12_16_8,
                                            color = Orange500
                                        )
                                    }
                                }
                                Text(
                                    text = "초역세권 대단지 아파트 후기",
                                    style = T500_16_22_4,
                                    color = Gray900
                                )
                            }
                        }
                        RadioButton(isSelected = isSelected, isEnabled = false)
                    }
                    HorizontalDivider(color = Gray100)
                }
            }
        }
        CommonButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            buttonText = stringResource(R.string.confirm),
            onClick = {
                coroutineScope.launch {
                    sheetState.hide()
                    onCloseBottomSheet()
                    delay(100)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ExchangeBottomSheetPreview() {
    ImdangTheme {
        ExchangeBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            onCloseBottomSheet = {}
        )
    }
}
