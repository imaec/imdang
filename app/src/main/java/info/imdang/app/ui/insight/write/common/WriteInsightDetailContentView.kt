package info.imdang.app.ui.insight.write.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray400
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_12_16_8
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.White
import info.imdang.resource.R

@Composable
fun WriteInsightDetailContentView(
    title: String,
    text: String,
    onClick: () -> Unit,
    isRequired: Boolean = false,
    isValid: Boolean = false,
    hintText: String = ""
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
            WriteInsightTitle(title = title, isRequired = isRequired, isValid = isValid)
            Text(
                text = stringResource(R.string.input_min_max_message, 30, 200),
                style = T500_12_16_8,
                color = Gray500
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    focusManager.clearFocus()
                    onClick()
                }
                .padding(all = 16.dp)
        ) {
            Text(
                text = text.ifBlank { hintText },
                style = T500_16_22_4,
                color = if (text.isNotBlank()) Gray900 else Gray400
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightDetailContentViewPreview() {
    ImdangTheme {
        Column(verticalArrangement = Arrangement.spacedBy(40.dp)) {
            WriteInsightDetailContentView(
                title = stringResource(R.string.insight_summary),
                text = "",
                hintText = stringResource(R.string.insight_summary_hint),
                isRequired = true,
                onClick = {}
            )
            WriteInsightDetailContentView(
                title = stringResource(R.string.infra_overall_review),
                text = "내용",
                onClick = {}
            )
        }
    }
}
