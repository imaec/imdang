package info.imdang.app.ui.insight.write.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.imdang.app.ui.insight.write.WriteInsightInit
import info.imdang.app.ui.insight.write.WriteInsightSelectionItems
import info.imdang.app.ui.insight.write.WriteInsightViewModel
import info.imdang.component.common.dialog.CommonDialog
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
    selectionItems: WriteInsightSelectionItems,
    selectionExample: String? = null,
    onClick: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var isShowResetDialog by remember { mutableStateOf(false) }
    var resetItem by remember { mutableStateOf("") }

    if (isShowResetDialog) {
        CommonDialog(
            iconResource = R.drawable.ic_sign_for_dialog,
            message = stringResource(R.string.write_insight_unselect_message),
            positiveButtonText = stringResource(R.string.yes_its_ok),
            negativeButtonText = stringResource(R.string.cancel),
            onClickPositiveButton = {
                isShowResetDialog = false
                selectionItems.confirmReset(resetItem)
            },
            onClickNegativeButton = {
                isShowResetDialog = false
            }
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WriteInsightTitle(
                title = title,
                isRequired = true,
                isValid = selectionItems.isValid.collectAsStateWithLifecycle(false).value
            )
            Text(
                text = stringResource(
                    if (selectionItems.isSingleSelection) {
                        R.string.single_selection
                    } else {
                        R.string.multiple_selection
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
            items = selectionItems.items.collectAsStateWithLifecycle().value,
            onClick = { item ->
                focusManager.clearFocus()
                selectionItems.selectItem(
                    item = item,
                    onShowResetDialog = {
                        resetItem = it
                        isShowResetDialog = true
                    }
                )
                onClick()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightSelectionButtonsPreview() {
    val viewModel = hiltViewModel<WriteInsightViewModel>()
    WriteInsightInit()
    ImdangTheme {
        Column(verticalArrangement = Arrangement.spacedBy(40.dp)) {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.building),
                selectionItems = viewModel.buildings,
                selectionExample = "ex. 단지 외관 상태"
            )
            WriteInsightSelectionButtons(
                title = stringResource(R.string.traffic),
                selectionItems = viewModel.infraTraffics
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResetDialogPreview() {
    ImdangTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            CommonDialog(
                iconResource = R.drawable.ic_sign_for_dialog,
                message = stringResource(R.string.write_insight_unselect_message),
                positiveButtonText = stringResource(R.string.yes_its_ok),
                negativeButtonText = stringResource(R.string.cancel),
                onClickPositiveButton = {},
                onClickNegativeButton = {}
            )
        }
    }
}
