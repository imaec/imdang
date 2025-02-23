package info.imdang.app.ui.insight.write.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.model.SelectionVo
import info.imdang.component.system.button.SelectionButtons
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray600
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_12_16_8
import info.imdang.component.theme.T500_14_19_6
import info.imdang.resource.R

@Composable
fun WriteInsightSelectionButtons(
    title: String,
    items: List<SelectionVo>,
    onClickItem: (item: SelectionVo) -> Unit,
    isMultipleSelection: Boolean = true,
    selectionExample: String? = null
) {
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WriteInsightTitle(title = title, isRequired = true)
            Text(
                text = stringResource(
                    if (isMultipleSelection) {
                        R.string.multiple_selection
                    } else {
                        R.string.single_selection
                    }
                ),
                style = T500_12_16_8,
                color = Gray500
            )
        }
        if (selectionExample != null) {
            Text(
                modifier = Modifier
                    .background(color = Gray50, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                text = selectionExample,
                style = T500_14_19_6,
                color = Gray600
            )
        }
        SelectionButtons(
            items = items,
            onClick = {
                focusManager.clearFocus()
                onClickItem(it)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightSelectionButtonsPreview() {
    ImdangTheme {
        Column(verticalArrangement = Arrangement.spacedBy(40.dp)) {
            WriteInsightSelectionButtons(
                title = "교통",
                items = listOf(
                    SelectionVo(name = stringResource(R.string.morning)),
                    SelectionVo(name = stringResource(R.string.day)),
                    SelectionVo(name = stringResource(R.string.evening)),
                    SelectionVo(name = stringResource(R.string.night))
                ),
                selectionExample = "ex. 단지 외관 상태",
                onClickItem = {}
            )
            WriteInsightSelectionButtons(
                title = "교통",
                items = listOf(
                    SelectionVo(name = stringResource(R.string.morning)),
                    SelectionVo(name = stringResource(R.string.day)),
                    SelectionVo(name = stringResource(R.string.evening)),
                    SelectionVo(name = stringResource(R.string.night))
                ),
                onClickItem = {}
            )
            WriteInsightSelectionButtons(
                title = "교통",
                items = listOf(
                    SelectionVo(name = stringResource(R.string.morning)),
                    SelectionVo(name = stringResource(R.string.day)),
                    SelectionVo(name = stringResource(R.string.evening)),
                    SelectionVo(name = stringResource(R.string.night))
                ),
                isMultipleSelection = false,
                onClickItem = {}
            )
        }
    }
}
