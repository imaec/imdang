@file:Suppress("DEPRECATION")

package info.imdang.app.ui.login

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import info.imdang.app.common.util.googleLogin
import info.imdang.app.common.util.kakaoLogin
import info.imdang.app.ui.login.preview.FakeLoginViewModel
import info.imdang.app.ui.main.MAIN_SCREEN
import info.imdang.app.ui.onboarding.ONBOARDING_SCREEN
import info.imdang.component.common.dialog.CommonDialog
import info.imdang.component.common.image.Icon
import info.imdang.component.common.snackbar.showSnackbar
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.White
import info.imdang.resource.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val LOGIN_SCREEN = "login"

fun NavGraphBuilder.loginScreen(navController: NavController) {
    composable(route = "$LOGIN_SCREEN?isLogout={isLogout}&isWithdraw={isWithdraw}") {
        val isLogout = it.arguments?.getString("isLogout")?.toBoolean() ?: false
        val isWithdraw = it.arguments?.getString("isWithdraw")?.toBoolean() ?: false

        LoginScreen(
            navController = navController,
            viewModel = hiltViewModel(),
            isLogout = isLogout,
            isWithdraw = isWithdraw
        )
    }
}

@Composable
private fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    isLogout: Boolean,
    isWithdraw: Boolean
) {
    var isShowLogoutDialog by rememberSaveable { mutableStateOf(isLogout) }
    var isShowWithdrawDialog by rememberSaveable { mutableStateOf(isWithdraw) }

    if (isShowLogoutDialog) {
        CommonDialog(
            message = stringResource(R.string.logout_success_message),
            onClickPositiveButton = { isShowLogoutDialog = false },
            onDismiss = { isShowLogoutDialog = false }
        )
    }

    if (isShowWithdrawDialog) {
        CommonDialog(
            message = stringResource(R.string.withdraw_success_message),
            onClickPositiveButton = { isShowWithdrawDialog = false },
            onDismiss = { isShowWithdrawDialog = false }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { contentPadding ->
            LoginContent(
                navController = navController,
                viewModel = viewModel,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun LoginContent(
    navController: NavController,
    viewModel: LoginViewModel,
    contentPadding: PaddingValues
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val kakaoLoginFailureMessage = stringResource(R.string.kakao_login_failure)
    val googleLoginFailureMessage = stringResource(R.string.google_login_failure)
    val googleLoginLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val googleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            googleSignInAccount.getResult(ApiException::class.java).serverAuthCode?.let {
                viewModel.getGoogleAccessToken(
                    authCode = it,
                    onSuccess = { accessToken ->
                        viewModel.googleLogin(token = accessToken)
                    }
                )
            }
        } else {
            coroutineScope.launch {
                showSnackbar(googleLoginFailureMessage)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest {
            when (it) {
                LoginEvent.MoveMainScreen -> navController.navigate(MAIN_SCREEN)
                LoginEvent.MoveOnboardingScreen -> navController.navigate(ONBOARDING_SCREEN)
            }
        }
    }

    Column(
        modifier = Modifier.padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_imdang),
                contentDescription = null
            )
        }
        Column {
            LoginButton(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                    .background(
                        color = Color(0xFFFAE64D),
                        shape = RoundedCornerShape(8.dp)
                    ),
                buttonIcon = R.drawable.ic_kakao,
                buttonText = stringResource(id = R.string.kakao_login),
                onClick = {
                    kakaoLogin(
                        context = context,
                        onSuccess = {
                            viewModel.kakaoLogin(it)
                        },
                        onFailure = {
                            coroutineScope.launch {
                                showSnackbar(it.message ?: kakaoLoginFailureMessage)
                            }
                        }
                    )
                }
            )
            LoginButton(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Gray100,
                        shape = RoundedCornerShape(8.dp)
                    ),
                buttonIcon = R.drawable.ic_google,
                buttonText = stringResource(id = R.string.google_login),
                onClick = {
                    googleLogin(
                        context = context,
                        googleLoginLauncher = googleLoginLauncher,
                        onFailure = {
                            coroutineScope.launch {
                                showSnackbar(it.message ?: googleLoginFailureMessage)
                            }
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun LoginButton(
    modifier: Modifier,
    @DrawableRes buttonIcon: Int,
    buttonText: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .size(24.dp),
            iconResource = buttonIcon
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = buttonText,
            style = T600_16_22_4.copy(textAlign = TextAlign.Center),
            color = Gray900
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    ImdangTheme {
        LoginScreen(
            navController = rememberNavController(),
            viewModel = FakeLoginViewModel(),
            isLogout = false,
            isWithdraw = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LogoutDialogPreview() {
    ImdangTheme {
        CommonDialog(
            message = stringResource(R.string.logout_success_message)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WithdrawDialogPreview() {
    ImdangTheme {
        CommonDialog(
            message = stringResource(R.string.withdraw_success_message)
        )
    }
}
