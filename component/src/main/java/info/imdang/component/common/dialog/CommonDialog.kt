package info.imdang.component.common.dialog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import info.imdang.component.common.image.Icon
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.button.CommonButtonType
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray600
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T400_16_22_4
import info.imdang.component.theme.T600_14_19_6
import info.imdang.component.theme.T600_18_25_2
import info.imdang.component.theme.White
import info.imdang.resource.R

@Composable
fun CommonDialog(
    message: String,
    positiveButtonText: String = stringResource(R.string.confirm),
    @DrawableRes iconResource: Int? = null,
    subMessage: String? = null,
    negativeButtonText: String? = null,
    subButtonText: String? = null,
    onClickPositiveButton: () -> Unit = {},
    onClickNegativeButton: () -> Unit = {},
    onClickSubButton: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        CommonDialog(
            message = message,
            positiveButtonText = positiveButtonText,
            iconResource = iconResource,
            subMessage = subMessage,
            negativeButtonText = negativeButtonText,
            subButtonText = subButtonText,
            onClickPositiveButton = onClickPositiveButton,
            onClickNegativeButton = onClickNegativeButton,
            onClickSubButton = onClickSubButton
        )
    }
}

@Composable
private fun CommonDialog(
    message: String,
    positiveButtonText: String = stringResource(R.string.confirm),
    @DrawableRes iconResource: Int? = null,
    subMessage: String? = null,
    negativeButtonText: String? = null,
    subButtonText: String? = null,
    onClickPositiveButton: () -> Unit = {},
    onClickNegativeButton: () -> Unit = {},
    onClickSubButton: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(20.dp))
            .padding(all = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(iconResource = iconResource ?: R.drawable.ic_check_for_dialog)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                style = T600_18_25_2,
                color = Gray900,
                textAlign = TextAlign.Center
            )
            if (subMessage != null) {
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = subMessage,
                    style = T400_16_22_4,
                    color = Gray600,
                    textAlign = TextAlign.Center
                )
            }
            if (subButtonText != null) {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .height(42.dp)
                        .border(width = 1.dp, color = Gray100, shape = CircleShape)
                        .clip(CircleShape)
                        .clickable(onClick = onClickSubButton)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = subButtonText,
                        style = T600_14_19_6,
                        color = Gray700
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (negativeButtonText != null) {
                    CommonButton(
                        modifier = Modifier.weight(1f),
                        buttonType = CommonButtonType.GHOST,
                        horizontalPadding = 0.dp,
                        bottomPadding = 0.dp,
                        buttonText = negativeButtonText,
                        onClick = onClickNegativeButton
                    )
                }
                CommonButton(
                    modifier = Modifier.weight(1f),
                    horizontalPadding = 0.dp,
                    bottomPadding = 0.dp,
                    buttonText = positiveButtonText,
                    onClick = onClickPositiveButton
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonDialogDefaultPreview() {
    ImdangTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CommonDialog(
                message = "다이얼로그 타이틀",
                onDismiss = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonDialogSubMessagePreview() {
    ImdangTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CommonDialog(
                message = "다이얼로그 타이틀",
                subMessage = "다이얼로그 내용",
                onDismiss = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonDialogSubButtonPreview() {
    ImdangTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CommonDialog(
                message = "다이얼로그 타이틀",
                subMessage = "다이얼로그 내용",
                subButtonText = "확인하기",
                onDismiss = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonDialogNegativeButtonPreview() {
    ImdangTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CommonDialog(
                message = "다이얼로그 타이틀",
                negativeButtonText = stringResource(R.string.cancel),
                onDismiss = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonDialogAllPreview() {
    ImdangTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CommonDialog(
                message = "다이얼로그 타이틀",
                subMessage = "다이얼로그 내용",
                subButtonText = "확인하기",
                negativeButtonText = stringResource(R.string.cancel),
                onDismiss = {}
            )
        }
    }
}
