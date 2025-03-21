package info.imdang.app.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.login.LOGIN_SCREEN
import info.imdang.app.ui.main.MAIN_SCREEN
import info.imdang.app.ui.splash.preview.FakeSplashViewModel
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R
import kotlinx.coroutines.flow.collectLatest

const val SPLASH_SCREEN = "splash"

fun NavGraphBuilder.splashScreen(navController: NavController) {
    composable(route = SPLASH_SCREEN) {
        SplashScreen(
            navController = navController,
            viewModel = hiltViewModel()
        )
    }
}

@Composable
private fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.event.collectLatest {
            when (it) {
                SplashEvent.MoveLoginScreen -> navController.navigate(route = LOGIN_SCREEN) {
                    popUpTo(SPLASH_SCREEN) { inclusive = true }
                }
                SplashEvent.MoveMainScreen -> navController.navigate(route = MAIN_SCREEN) {
                    popUpTo(SPLASH_SCREEN) { inclusive = true }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { contentPadding ->
            SplashContent(contentPadding)
        }
    )
}

@Composable
private fun SplashContent(contentPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_imdang),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    ImdangTheme {
        SplashScreen(
            navController = rememberNavController(),
            viewModel = FakeSplashViewModel()
        )
    }
}
