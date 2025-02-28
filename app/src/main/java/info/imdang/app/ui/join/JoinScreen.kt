package info.imdang.app.ui.join

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.common.ext.encodeUtf8
import info.imdang.app.const.MARKETING_URL
import info.imdang.app.const.PRIVACY_URL
import info.imdang.app.const.SERVICE_TERM_URL
import info.imdang.app.ui.join.complete.JOIN_COMPLETE_SCREEN
import info.imdang.app.ui.onboarding.ONBOARDING_SCREEN
import info.imdang.app.util.KeyboardCallback
import info.imdang.component.common.image.Icon
import info.imdang.component.common.modifier.visible
import info.imdang.component.common.webview.COMMON_WEB_SCREEN
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.button.SelectionButtons
import info.imdang.component.system.textfield.CommonTextField
import info.imdang.component.theme.Gray200
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T600_14_19_6
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.T600_20_28
import info.imdang.component.theme.T700_26_36_4
import info.imdang.component.theme.White
import info.imdang.resource.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val JOIN_SCREEN = "join"

fun NavGraphBuilder.joinScreen(navController: NavController) {
    composable(route = JOIN_SCREEN) {
        JoinScreen(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JoinScreen(navController: NavController) {
    val sheetState = rememberModalBottomSheetState(confirmValueChange = { false })
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        showBottomSheet = true
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            containerColor = White,
            dragHandle = null,
            onDismissRequest = { showBottomSheet = false }
        ) {
            ServiceTermBottomSheet(
                navController = navController,
                sheetState = sheetState,
                onClickClose = { isBack ->
                    showBottomSheet = false
                    if (isBack) {
                        navController.popBackStack(route = ONBOARDING_SCREEN, inclusive = true)
                    }
                }
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            Icon(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 12.dp, top = 12.dp, bottom = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.popBackStack()
                    }
                    .padding(8.dp),
                iconResource = R.drawable.ic_back,
                tint = Gray900
            )
        },
        content = { contentPadding ->
            JoinContent(
                navController = navController,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun JoinContent(
    navController: NavController,
    contentPadding: PaddingValues
) {
    val viewModel = hiltViewModel<JoinViewModel>()
    val focusManager = LocalFocusManager.current
    val nicknameFocusRequest = remember { FocusRequester() }
    val birthDateFocusRequester = remember { FocusRequester() }
    var isNicknameFocused by remember { mutableStateOf(false) }
    var isBirthDateFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(R.string.join_title),
                        style = T700_26_36_4,
                        color = Gray900
                    )
                    Text(
                        text = stringResource(R.string.join_description),
                        style = T500_16_22_4,
                        color = Gray700
                    )
                }
            }
            item {
                CommonTextField(
                    modifier = Modifier
                        .padding(top = 57.dp)
                        .focusRequester(nicknameFocusRequest)
                        .onFocusChanged {
                            isNicknameFocused = it.isFocused
                        },
                    text = viewModel.nickname.collectAsStateWithLifecycle().value,
                    hintText = stringResource(R.string.nickname_hint),
                    errorText = viewModel.nicknameErrorMessage
                        .collectAsStateWithLifecycle().value,
                    imeAction = ImeAction.Next,
                    title = "닉네임",
                    minLength = 2,
                    maxLength = 10,
                    onTextChanged = {
                        viewModel.updateNickname(nickname = it)
                    }
                )
            }
            item {
                CommonTextField(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .focusRequester(birthDateFocusRequester)
                        .onFocusChanged {
                            isBirthDateFocused = it.isFocused
                        },
                    text = viewModel.birthDate.collectAsStateWithLifecycle().value,
                    hintText = stringResource(R.string.birth_date_hint),
                    errorText = viewModel.birthDateErrorMessage
                        .collectAsStateWithLifecycle().value,
                    title = "생년월일",
                    maxLength = 10,
                    keyboardType = KeyboardType.Number,
                    onTextChanged = {
                        viewModel.updateBirthDate(birthDate = it)
                    }
                )
            }
            item {
                val genders by viewModel.genders.collectAsStateWithLifecycle()

                Column(
                    modifier = Modifier.padding(vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.height(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.gender),
                            style = T600_14_19_6,
                            color = Gray700
                        )
                        Icon(
                            modifier = Modifier
                                .visible(genders.any { it.isSelected })
                                .size(20.dp),
                            iconResource = R.drawable.ic_check
                        )
                    }
                    SelectionButtons(
                        items = genders,
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.updateGender(it)
                        }
                    )
                }
            }
        }
        CompleteButton(
            navController = navController,
            viewModel = viewModel,
            isNicknameFocused = isNicknameFocused,
            isBirthDateFocused = isBirthDateFocused
        )
    }
}

@Composable
private fun CompleteButton(
    navController: NavController,
    viewModel: JoinViewModel,
    isNicknameFocused: Boolean,
    isBirthDateFocused: Boolean
) {
    val focusManager = LocalFocusManager.current
    var isShowKeyboard by remember { mutableStateOf(false) }
    val isButtonEnabled by if (isShowKeyboard) {
        if (isNicknameFocused) {
            viewModel.isNicknameValid.collectAsStateWithLifecycle()
        } else if (isBirthDateFocused) {
            viewModel.isBirthDateValid.collectAsStateWithLifecycle()
        } else {
            viewModel.isButtonEnabled.collectAsStateWithLifecycle()
        }
    } else {
        viewModel.isButtonEnabled.collectAsStateWithLifecycle()
    }

    KeyboardCallback(
        onShowKeyboard = { isShowKeyboard = true },
        onHideKeyboard = { isShowKeyboard = false }
    )

    CommonButton(
        buttonText = stringResource(
            if (isShowKeyboard) R.string.confirm else R.string.complete
        ),
        isEnabled = isButtonEnabled,
        isFloating = !isShowKeyboard,
        onClick = {
            if (isShowKeyboard) {
                if (isNicknameFocused && isButtonEnabled) {
                    focusManager.clearFocus()
                }
                if (isBirthDateFocused && isButtonEnabled) {
                    focusManager.clearFocus()
                }
            } else {
                navController.navigate(JOIN_COMPLETE_SCREEN) {
                    popUpTo(ONBOARDING_SCREEN) { inclusive = true }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServiceTermBottomSheet(
    navController: NavController,
    sheetState: SheetState,
    onClickClose: (isBack: Boolean) -> Unit
) {
    val viewModel = hiltViewModel<JoinViewModel>()
    val coroutineScope = rememberCoroutineScope()

    val isAgreeAndContinueButtonEnabled by viewModel.isAgreeAndContinueButtonEnabled
        .collectAsStateWithLifecycle()
    val isAgreeTerm by viewModel.isAgreeTerm.collectAsStateWithLifecycle()
    val isAgreePrivacy by viewModel.isAgreePrivacy.collectAsStateWithLifecycle()
    val isAgreeMarketing by viewModel.isAgreeMarketing.collectAsStateWithLifecycle()
    val isAgreeAll = isAgreeTerm && isAgreePrivacy && isAgreeMarketing

    BackHandler {
    }

    Column(modifier = Modifier.padding(top = 24.dp, bottom = 40.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.service_term),
                style = T600_20_28,
                color = Gray900
            )
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable {
                        onClickClose(true)
                    }
                    .padding(8.dp),
                iconResource = R.drawable.ic_close,
                tint = Gray900
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 32.dp)
                .fillMaxWidth()
                .background(
                    color = if (isAgreeAll) Orange50 else Gray50,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable {
                        viewModel.updateAllAgree()
                    },
                iconResource = if (isAgreeAll) R.drawable.ic_check else R.drawable.ic_check_gray,
                tint = if (isAgreeAll) Color.Unspecified else Gray200
            )
            Text(
                text = stringResource(R.string.all_agree),
                style = T600_16_22_4,
                color = Gray900
            )
        }
        TermItem(
            navController = navController,
            sheetState = sheetState,
            isAgree = isAgreeTerm,
            termText = stringResource(R.string.required_term),
            termDetailUrl = SERVICE_TERM_URL,
            onClickCheck = {
                viewModel.updateAgreeTerm()
            }
        )
        TermItem(
            navController = navController,
            sheetState = sheetState,
            isAgree = isAgreePrivacy,
            termText = stringResource(R.string.required_privacy_term),
            termDetailUrl = PRIVACY_URL,
            onClickCheck = {
                viewModel.updateAgreePrivacy()
            }
        )
        TermItem(
            navController = navController,
            sheetState = sheetState,
            isAgree = isAgreeMarketing,
            termText = stringResource(R.string.optional_marketing_and_notifications),
            termDetailUrl = MARKETING_URL,
            onClickCheck = {
                viewModel.updateAgreeMarketing()
            }
        )
        CommonButton(
            modifier = Modifier.padding(top = 32.dp),
            bottomPadding = 0.dp,
            buttonText = stringResource(R.string.agree_and_continue),
            isEnabled = isAgreeAndContinueButtonEnabled,
            onClick = {
                coroutineScope.launch {
                    sheetState.hide()
                    delay(100)
                    onClickClose(false)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TermItem(
    navController: NavController,
    sheetState: SheetState,
    isAgree: Boolean,
    termText: String,
    termDetailUrl: String,
    onClickCheck: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 16.dp)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .clickable(onClick = onClickCheck),
            iconResource = if (isAgree) R.drawable.ic_check else R.drawable.ic_check_gray,
            tint = if (isAgree) Color.Unspecified else Gray200
        )
        Text(
            modifier = Modifier.weight(1f),
            text = termText,
            style = T500_14_19_6,
            color = Gray900
        )
        Icon(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .clickable {
                    coroutineScope.launch {
                        sheetState.hide()
                        navController.navigate(
                            "$COMMON_WEB_SCREEN?url=${termDetailUrl.encodeUtf8()}"
                        )
                    }
                }
                .padding(4.dp),
            iconResource = R.drawable.ic_right,
            tint = Gray900
        )
    }
}

@Preview
@Composable
private fun JoinScreenPreview() {
    ImdangTheme {
        JoinScreen(navController = rememberNavController())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ServiceTermBottomSheetPreview() {
    ImdangTheme {
        ServiceTermBottomSheet(
            navController = rememberNavController(),
            sheetState = rememberModalBottomSheetState(),
            onClickClose = {}
        )
    }
}
