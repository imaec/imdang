package info.imdang.app.ui.insight.detail.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.app.ui.insight.write.ComplexEnvironmentItems
import info.imdang.app.ui.insight.write.InfraItems
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray600
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T600_14_19_6
import info.imdang.resource.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InsightDetailSelectionItem(
    title: String,
    items: List<String>,
    example: String? = null
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        InsightDetailTitle(title = title)
        if (example != null) {
            Text(
                modifier = Modifier
                    .background(color = Gray50, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                text = example,
                style = T500_14_19_6,
                color = Gray600
            )
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach {
                val isSelected = it != stringResource(R.string.not_applicable) &&
                    it != stringResource(R.string.unknown)
                Box(
                    modifier = Modifier
                        .height(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = if (isSelected) Orange50 else Gray50)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Orange500 else Gray100,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        style = T600_14_19_6,
                        color = if (isSelected) Orange500 else Gray500
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InsightDetailSelectionItemPreview() {
    ImdangTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            InsightDetailSelectionItem(
                title = stringResource(R.string.traffic),
                items = InfraItems.traffics().map { it.name }
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.building),
                example = stringResource(R.string.building_example),
                items = ComplexEnvironmentItems.buildings().map { it.name }
            )
        }
    }
}
