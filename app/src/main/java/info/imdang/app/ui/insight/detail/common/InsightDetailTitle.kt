package info.imdang.app.ui.insight.detail.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.common.image.Icon
import info.imdang.component.theme.Gray600
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_14_19_6
import info.imdang.resource.R

@Composable
fun InsightDetailTitle(
    title: String,
    @DrawableRes icon: Int? = null
) {
    Row(
        modifier = Modifier.height(20.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                modifier = Modifier.size(16.dp),
                iconResource = icon,
                tint = Gray600
            )
        }
        Text(
            text = title,
            style = T500_14_19_6,
            color = Gray600
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InsightDetailTitlePreview() {
    ImdangTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            InsightDetailTitle(
                title = stringResource(R.string.complex_address),
                icon = R.drawable.ic_pin_gray
            )
            InsightDetailTitle(
                title = stringResource(R.string.visit_date_and_time),
                icon = R.drawable.ic_calendar
            )
            InsightDetailTitle(
                title = stringResource(R.string.traffic_method),
                icon = R.drawable.ic_car
            )
            InsightDetailTitle(
                title = stringResource(R.string.building)
            )
            InsightDetailTitle(
                title = stringResource(R.string.safety)
            )
        }
    }
}
