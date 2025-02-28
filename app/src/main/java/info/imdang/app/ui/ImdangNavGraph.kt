package info.imdang.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.insight.detail.insightDetailScreen
import info.imdang.app.ui.insight.write.basic.kakaoAddressWebScreen
import info.imdang.app.ui.insight.write.basic.writeInsightSummaryScreen
import info.imdang.app.ui.insight.write.common.writeInsightOverallReviewScreen
import info.imdang.app.ui.insight.write.writeInsightScreen
import info.imdang.app.ui.join.complete.joinCompleteScreen
import info.imdang.app.ui.join.joinScreen
import info.imdang.app.ui.list.new.newInsightScreen
import info.imdang.app.ui.list.region.searchByRegionInsightListScreen
import info.imdang.app.ui.list.visitcomplex.visitComplexInsightScreen
import info.imdang.app.ui.login.loginScreen
import info.imdang.app.ui.main.MAIN_SCREEN
import info.imdang.app.ui.main.home.search.map.searchByMapScreen
import info.imdang.app.ui.main.home.search.region.searchByRegionScreen
import info.imdang.app.ui.main.mainScreen
import info.imdang.app.ui.main.storage.address.storageAddressScreen
import info.imdang.app.ui.main.storage.map.storageByMapScreen
import info.imdang.app.ui.my.myScreen
import info.imdang.app.ui.my.term.serviceTermScreen
import info.imdang.app.ui.my.withdraw.withdrawScreen
import info.imdang.app.ui.onboarding.onboardingScreen
import info.imdang.app.ui.splash.splashScreen
import info.imdang.component.common.snackbar.Snackbar
import info.imdang.component.common.webview.commonWebScreen
import info.imdang.component.theme.ImdangTheme

@Composable
fun ImdangNavGraph() {
    val navController = rememberNavController()

    ImdangTheme {
        NavHost(
            navController = navController,
            startDestination = MAIN_SCREEN
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
            storageByMapScreen(navController = navController)
            storageAddressScreen(navController = navController)
            writeInsightScreen(navController = navController)
            writeInsightSummaryScreen(navController = navController)
            writeInsightOverallReviewScreen(navController = navController)
            kakaoAddressWebScreen(navController = navController)
            insightDetailScreen(navController = navController)
            myScreen(navController = navController)
            serviceTermScreen(navController = navController)
            withdrawScreen(navController = navController)

            commonWebScreen(navController = navController)
        }
        Snackbar()
    }
}
