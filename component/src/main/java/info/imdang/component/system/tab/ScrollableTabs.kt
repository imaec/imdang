package info.imdang.component.system.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray25
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_14_19_6
import info.imdang.resource.R

@Composable
fun ScrollableTabs(
    tabs: List<String>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = Gray25,
    divider: @Composable () -> Unit = { HorizontalDivider(color = Gray100) },
    onTabSelected: (selectedIndex: Int) -> Unit = {}
) {
    Column(
        modifier = modifier.then(Modifier.fillMaxWidth())
    ) {
        ImdangScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = containerColor,
            divider = {}
        ) {
            tabs.forEachIndexed { tabIndex, tab ->
                val isSelected = selectedTabIndex == tabIndex
                ImdangTab(
                    selected = isSelected,
                    selectedContentColor = Gray900,
                    unselectedContentColor = Gray500,
                    onClick = { onTabSelected(tabIndex) },
                    tab = {
                        Text(
                            text = tab,
                            style = T600_14_19_6,
                            color = if (isSelected) Gray900 else Gray500
                        )
                    }
                )
            }
        }
        divider()
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrollableTabsPreview() {
    ImdangTheme {
        ScrollableTabs(
            modifier = Modifier.padding(vertical = 20.dp),
            tabs = listOf(
                stringResource(R.string.basic_info),
                stringResource(R.string.infra),
                stringResource(R.string.complex_environment),
                stringResource(R.string.complex_facility),
                stringResource(R.string.good)
            ),
            selectedTabIndex = 0
        )
    }
}
