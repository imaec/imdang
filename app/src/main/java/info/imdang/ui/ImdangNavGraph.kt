package info.imdang.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import info.imdang.component.common.webview.commonWebScreen
import info.imdang.component.theme.ImdangTheme
import info.imdang.ui.join.complete.joinCompleteScreen
import info.imdang.ui.join.joinScreen
import info.imdang.ui.list.new.newInsightScreen
import info.imdang.ui.list.region.searchByRegionInsightListScreen
import info.imdang.ui.list.visitcomplex.visitComplexInsightScreen
import info.imdang.ui.login.loginScreen
import info.imdang.ui.main.MAIN_SCREEN
import info.imdang.ui.main.home.search.map.searchByMapScreen
import info.imdang.ui.main.home.search.region.searchByRegionScreen
import info.imdang.ui.main.mainScreen
import info.imdang.ui.onboarding.onboardingScreen
import info.imdang.ui.splash.SPLASH_SCREEN
import info.imdang.ui.splash.splashScreen

@Composable
fun ImdangNavGraph() {
    val navController = rememberNavController()

    ImdangTheme {
        NavHost(
            navController = navController,
            startDestination = SPLASH_SCREEN
        ) {
            splashScreen(navController = navController)
            loginScreen(navController = navController)
            onboardingScreen(navController = navController)
            joinScreen(navController = navController)
            joinCompleteScreen(navController = navController)
            mainScreen(navController = navController)
            visitComplexInsightScreen(navController = navController)
            newInsightScreen(navController = navController)
            searchByMapScreen(navController = navController)
            searchByRegionScreen(navController = navController)
            searchByRegionInsightListScreen(navController = navController)

            commonWebScreen(navController = navController)
        }
    }
}
