package info.imdang.component.system.gradient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import info.imdang.component.system.button.CommonButton
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray25
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.T500_14_19_6

@Composable
fun ButtonGradient(
    modifier: Modifier = Modifier,
    height: Dp = 40.dp,
    isReverse: Boolean = false,
    startColor: Color = Gray25.copy(alpha = 0f),
    endColor: Color = Gray25.copy(alpha = 1f)
) {
    val colors = listOf(startColor, endColor).run {
        if (isReverse) reversed() else this
    }

    Spacer(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(colors = colors)
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun ButtonGradientPreview() {
    ImdangTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { contentPadding ->
                Box(modifier = Modifier.padding(contentPadding)) {
                    LazyColumn {
                        items(30) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = Orange50)
                            ) {
                                if (it > 0) {
                                    HorizontalDivider(color = Gray100)
                                }
                                Text(
                                    modifier = Modifier.padding(all = 20.dp),
                                    text = "item $it",
                                    style = T500_14_19_6,
                                    color = Gray900
                                )
                            }
                        }
                    }
                    ButtonGradient(
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            },
            bottomBar = {
                CommonButton(buttonText = "완료")
            }
        )
    }
}
