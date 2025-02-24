package info.imdang.app.ui.insight.write

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import info.imdang.component.common.dialog.CommonDialog
import info.imdang.component.common.image.Icon
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray200
import info.imdang.component.theme.Gray300
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T600_12_16_8
import info.imdang.component.theme.T600_14_19_6
import info.imdang.resource.R

@Composable
fun WriteInsightTopBar(navController: NavController) {
    val viewModel = hiltViewModel<WriteInsightViewModel>()
    val selectedPage by viewModel.selectedPage.collectAsStateWithLifecycle()
    val pages = listOf(
        stringResource(R.string.basic_info),
        stringResource(R.string.infra),
        stringResource(R.string.complex_environment),
        stringResource(R.string.complex_facility),
        stringResource(R.string.good_news)
    )
    var isShowDialog by remember { mutableStateOf(false) }
    val progress by viewModel.progress.collectAsStateWithLifecycle()

    if (isShowDialog) {
        CommonDialog(
            iconResource = R.drawable.ic_sign_for_dialog,
            message = stringResource(R.string.write_insight_back_message),
            positiveButtonText = stringResource(R.string.yes_its_ok),
            negativeButtonText = stringResource(R.string.cancel),
            onClickPositiveButton = {
                isShowDialog = false
                navController.popBackStack()
            },
            onClickNegativeButton = { isShowDialog = false },
            onDismiss = { isShowDialog = false }
        )
    }

    BackHandler { isShowDialog = true }

    Column {
        TopBar(
            title = stringResource(R.string.write_insight_title),
            hasDivider = false,
            onClickBack = { isShowDialog = true },
            rightWidget = {
                Text(
                    modifier = Modifier
                        .background(color = Orange50, shape = CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = progress,
                    style = T600_14_19_6,
                    color = Orange500
                )
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                pages.forEachIndexed { page, pageName ->
                    WriteInsightPageItem(
                        pageName = pageName,
                        currentPage = page,
                        selectedPage = selectedPage
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                pages.forEachIndexed { page, _ ->
                    Spacer(modifier = Modifier.size(16.dp))
                    if (page < 4) {
                        HorizontalDivider(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp),
                            color = if (selectedPage > page) Orange500 else Gray200
                        )
                    }
                }
            }
        }
        HorizontalDivider(color = Gray100)
    }
}

@Composable
private fun WriteInsightPageItem(
    pageName: String,
    currentPage: Int,
    selectedPage: Int
) {
    Column(
        modifier = Modifier.padding(
            start = if (currentPage == 3) 17.dp else 0.dp,
            end = if (currentPage == 1) 17.dp else 0.dp
        ),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = when (currentPage) {
            0 -> Alignment.Start
            4 -> Alignment.End
            else -> Alignment.CenterHorizontally
        }
    ) {
        if (selectedPage > currentPage) {
            Icon(
                modifier = Modifier.size(16.dp),
                iconResource = R.drawable.ic_check
            )
        } else {
            val isSelected = currentPage == selectedPage
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Orange500 else Gray200,
                        shape = CircleShape
                    )
                    .padding(all = if (isSelected) 4.dp else 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = if (isSelected) Orange500 else Gray200,
                            shape = CircleShape
                        )
                )
            }
        }
        Text(
            modifier = Modifier.width(50.dp),
            text = pageName,
            style = T600_12_16_8,
            color = if (selectedPage == currentPage) Orange500 else Gray300,
            textAlign = when (currentPage) {
                0 -> TextAlign.Start
                4 -> TextAlign.End
                else -> TextAlign.Center
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightTopBarPreview() {
    ImdangTheme {
        WriteInsightTopBar(navController = rememberNavController())
    }
}
