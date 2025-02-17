package info.imdang.component.system.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import info.imdang.component.common.image.Icon
import info.imdang.component.common.modifier.isVisible
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray400
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.RedE93528
import info.imdang.component.theme.T500_12_16_8
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T600_14_19_6
import info.imdang.component.theme.White
import info.imdang.resource.R

@Composable
fun CommonTextField(
    text: String,
    modifier: Modifier = Modifier,
    hintText: String = "",
    errorText: String = "",
    isSingleLine: Boolean = true,
    multiLineHeight: Dp = 208.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    title: String? = null,
    minLength: Int? = null,
    maxLength: Int? = null,
    onTextChanged: (String) -> Unit = {}
) {
    var textState by if (keyboardType == KeyboardType.Number) {
        remember(text) {
            mutableStateOf(TextFieldValue(text = text, selection = TextRange(text.length)))
        }
    } else {
        remember { mutableStateOf(TextFieldValue(text = text)) }
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (title != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = T600_14_19_6,
                        color = Gray700
                    )
                    Icon(
                        modifier = Modifier
                            .isVisible(textState.text.isNotEmpty() || errorText.isNotEmpty())
                            .size(20.dp),
                        iconResource = if (errorText.isNotEmpty()) {
                            R.drawable.ic_info_red
                        } else {
                            R.drawable.ic_check
                        }
                    )
                }
                if (minLength != null && maxLength != null) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = stringResource(
                                R.string.input_min_max_message,
                                minLength,
                                maxLength
                            ),
                            style = T500_12_16_8,
                            color = Gray500
                        )
                        if (textState.text.isNotEmpty()) {
                            Text(
                                text = "(${textState.text.length}/$maxLength)",
                                style = T500_12_16_8,
                                color = Orange500
                            )
                        }
                    }
                }
            }
        }
        BasicTextField(
            modifier = Modifier
                .height(if (isSingleLine) 52.dp else multiLineHeight)
                .fillMaxSize()
                .background(
                    color = White,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (errorText.isNotEmpty()) {
                        RedE93528
                    } else if (isFocused) {
                        Orange500
                    } else {
                        Gray100
                    },
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp),
            value = textState,
            onValueChange = {
                if (it.text.length <= (maxLength ?: Int.MAX_VALUE)) {
                    textState = it
                    onTextChanged(it.text)
                }
            },
            textStyle = T500_16_22_4.copy(color = Gray900),
            singleLine = isSingleLine,
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            cursorBrush = SolidColor(if (errorText.isNotEmpty()) RedE93528 else Orange500),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (textState.text.isEmpty()) {
                            Text(
                                text = hintText,
                                style = T500_16_22_4,
                                color = Gray400
                            )
                        }
                        innerTextField()
                    }

                    if (textState.text.isNotEmpty()) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .clickable {
                                    onTextChanged("")
                                    textState = TextFieldValue("")
                                },
                            iconResource = R.drawable.ic_x_circle
                        )
                    }
                }
            }
        )
        Text(
            modifier = Modifier.isVisible(errorText.isNotEmpty()),
            text = errorText,
            style = T500_12_16_8,
            color = RedE93528
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonTextFieldPreview() {
    ImdangTheme {
        Column(
            modifier = Modifier.padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CommonTextField(
                text = "",
                hintText = "플레이스 홀더"
            )
            CommonTextField(
                text = "입력",
                hintText = "플레이스 홀더"
            )
            CommonTextField(
                text = "",
                hintText = "플레이스 홀더",
                title = "닉네임",
                minLength = 2,
                maxLength = 10
            )
            CommonTextField(
                text = "입력",
                hintText = "플레이스 홀더",
                errorText = "에러 메세지",
                title = "닉네임",
                minLength = 2,
                maxLength = 10
            )
            CommonTextField(
                text = "입력",
                hintText = "플레이스 홀더",
                title = "닉네임",
                minLength = 2,
                maxLength = 10
            )
        }
    }
}
