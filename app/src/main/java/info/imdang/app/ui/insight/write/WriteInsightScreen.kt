package info.imdang.app.ui.insight.write

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.insight.write.basic.WriteInsightBasicInfoPage
import info.imdang.app.ui.insight.write.environment.WriteInsightComplexEnvironmentPage
import info.imdang.app.ui.insight.write.facility.WriteInsightComplexFacilityPage
import info.imdang.app.ui.insight.write.goodnews.WriteInsightGoodNewsPage
import info.imdang.app.ui.insight.write.infra.WriteInsightInfraPage
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.gradient.ButtonGradient
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

const val WRITE_INSIGHT_SCREEN = "writeInsight"

fun NavGraphBuilder.writeInsightScreen(navController: NavController) {
    composable(route = WRITE_INSIGHT_SCREEN) {
        WriteInsightScreen(navController = navController)
    }
}

@Composable
private fun WriteInsightScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            WriteInsightTopBar(navController = navController)
        },
        content = { contentPadding ->
            WriteInsightContent(contentPadding)
        },
        bottomBar = {
            CommonButton(buttonText = stringResource(R.string.next))
        }
    )
}

@Composable
private fun WriteInsightContent(contentPadding: PaddingValues) {
    val viewModel = hiltViewModel<WriteInsightViewModel>()
    val pagerState = rememberPagerState { 5 }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.updateSelectedPage(pagerState.currentPage)
    }

    Box(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> WriteInsightBasicInfoPage()
                1 -> WriteInsightInfraPage()
                2 -> WriteInsightComplexEnvironmentPage()
                3 -> WriteInsightComplexFacilityPage()
                4 -> WriteInsightGoodNewsPage()
            }
        }
        ButtonGradient(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Preview
@Composable
private fun WriteInsightScreenPreview() {
    ImdangTheme {
        WriteInsightScreen(navController = rememberNavController())
    }
}
