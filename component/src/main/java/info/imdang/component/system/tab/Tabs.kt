package info.imdang.component.system.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray25
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_16_22_4
import info.imdang.resource.R

@Composable
fun Tabs(
    tabs: List<String>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = Gray25,
    height: Dp = 46.dp,
    divider: @Composable () -> Unit = { HorizontalDivider(color = Gray100) },
    onTabSelected: (selectedIndex: Int) -> Unit = {}
) {
    Column(modifier = modifier.then(Modifier.fillMaxWidth())) {
        TabRow(
            modifier = Modifier.padding(horizontal = 8.dp),
            selectedTabIndex = selectedTabIndex,
            containerColor = containerColor,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(it[selectedTabIndex])
                        .padding(horizontal = 12.dp),
                    height = 2.dp,
                    color = Gray900
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { tabIndex, tab ->
                val isSelected = tabIndex == selectedTabIndex
                ImdangTab(
                    selected = isSelected,
                    height = height,
                    onClick = { onTabSelected(tabIndex) },
                    tab = {
                        Text(
                            text = tab,
                            style = T600_16_22_4,
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
private fun TabsPreview() {
    ImdangTheme {
        Tabs(
            modifier = Modifier.padding(vertical = 20.dp),
            tabs = listOf(
                stringResource(R.string.request_exchange_history),
                stringResource(R.string.requested_exchange_history)
            ),
            selectedTabIndex = 0
        )
    }
}
