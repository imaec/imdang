package info.imdang.app.ui.login

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.onboarding.ONBOARDING_SCREEN
import info.imdang.component.common.image.Icon
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.White
import info.imdang.resource.R

const val LOGIN_SCREEN = "login"

fun NavGraphBuilder.loginScreen(navController: NavController) {
    composable(route = LOGIN_SCREEN) {
        LoginScreen(navController = navController)
    }
}

@Composable
private fun LoginScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { contentPadding ->
            LoginContent(
                navController = navController,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun LoginContent(
    navController: NavController,
    contentPadding: PaddingValues
) {
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
                    navController.navigate(route = ONBOARDING_SCREEN)
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
                    navController.navigate(route = ONBOARDING_SCREEN)
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
        LoginScreen(navController = rememberNavController())
    }
}
