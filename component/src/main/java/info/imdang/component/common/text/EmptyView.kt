package info.imdang.component.common.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.common.modifier.dashedBorder
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_14_19_6
import info.imdang.resource.R

@Composable
fun EmptyView(emptyMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp)
            .background(
                color = Gray50,
                shape = RoundedCornerShape(8.dp)
            )
            .dashedBorder(
                width = 1.dp,
                radius = 8.dp,
                color = Gray100
            )
            .padding(vertical = 16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = emptyMessage,
            style = T500_14_19_6,
            color = Gray500,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyViewPreview() {
    ImdangTheme {
        Box(modifier = Modifier.padding(all = 20.dp)) {
            EmptyView(emptyMessage = stringResource(R.string.empty_home_insight_message))
        }
    }
}
