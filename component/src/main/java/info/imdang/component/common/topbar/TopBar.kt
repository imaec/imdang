package info.imdang.component.common.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.common.image.Icon
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray25
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T600_14_19_6
import info.imdang.component.theme.T600_18_25_2
import info.imdang.resource.R

@Composable
fun TopBar(
    title: String = "",
    rightWidget: (@Composable () -> Unit)? = null,
    onClickBack: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .height(65.dp)
                .background(color = Gray25)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onClickBack)
                    .padding(8.dp),
                iconResource = R.drawable.ic_back,
                tint = Gray900
            )
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = T600_18_25_2,
                color = Gray900
            )
            rightWidget?.invoke()
        }
        HorizontalDivider(color = Gray100)
    }
}

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    ImdangTheme {
        Column(
            modifier = Modifier
                .background(color = Gray50)
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TopBar()
            TopBar(title = "제목")
            TopBar(
                title = "제목",
                rightWidget = {
                    Text(
                        modifier = Modifier
                            .background(color = Orange50, shape = CircleShape)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        text = "00%",
                        style = T600_14_19_6,
                        color = Orange500
                    )
                }
            )
        }
    }
}
