package info.imdang.app.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.main.home.HOME_SCREEN
import info.imdang.app.ui.main.home.homeScreen
import info.imdang.app.ui.main.storage.storageScreen
import info.imdang.component.theme.ImdangTheme

const val MAIN_SCREEN = "main"

fun NavGraphBuilder.mainScreen(navController: NavController) {
    composable(route = MAIN_SCREEN) {
        MainScreen(navController = navController)
    }
}

@Composable
private fun MainScreen(navController: NavController) {
    val mainNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { contentPadding ->
            NavHost(
                navController = mainNavController,
                startDestination = HOME_SCREEN,
                modifier = Modifier.padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = 92.dp
                )
            ) {
                homeScreen(
                    navController = navController,
                    mainNavController = mainNavController
                )
                storageScreen(
                    navController = navController,
                    mainNavController = mainNavController
                )
            }
        },
        bottomBar = {
            MainBottomBar(
                navController = navController,
                mainNavController = mainNavController
            )
        }
    )
}

@Preview
@Composable
private fun MainScreenPreview() {
    ImdangTheme {
        MainScreen(navController = rememberNavController())
    }
}
