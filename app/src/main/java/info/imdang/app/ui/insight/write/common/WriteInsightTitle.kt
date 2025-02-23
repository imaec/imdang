package info.imdang.app.ui.insight.write.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.RedE93528
import info.imdang.component.theme.T600_14_19_6

@Composable
fun WriteInsightTitle(title: String, isRequired: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            style = T600_14_19_6,
            color = Gray700
        )
        if (isRequired) {
            Text(
                text = "*",
                style = T600_14_19_6,
                color = RedE93528
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightTitlePreview() {
    ImdangTheme {
        Column(
            modifier = Modifier.padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WriteInsightTitle(title = "제목")
            WriteInsightTitle(title = "제목", isRequired = true)
        }
    }
}
