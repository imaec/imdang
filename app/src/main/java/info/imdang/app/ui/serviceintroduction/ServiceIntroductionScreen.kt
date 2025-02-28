package info.imdang.app.ui.serviceintroduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

const val SERVICE_INTRODUCTION_SCREEN = "serviceIntroduction"

fun NavGraphBuilder.serviceIntroductionScreen(navController: NavController) {
    composable(route = "$SERVICE_INTRODUCTION_SCREEN?selectedIndex={selectedIndex}") {
        val selectedIndex = it.arguments?.getString("selectedIndex")?.toInt() ?: 0

        ServiceIntroductionScreen(
            navController = navController,
            selectedIndex = selectedIndex
        )
    }
}

@Composable
private fun ServiceIntroductionScreen(
    navController: NavController,
    selectedIndex: Int
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.service_introduction),
                onClickBack = { navController.popBackStack() }
            )
        },
        content = { contentPadding ->
            ServiceIntroductionContent(
                contentPadding = contentPadding,
                selectedIndex = selectedIndex
            )
        }
    )
}

@Composable
private fun ServiceIntroductionContent(
    contentPadding: PaddingValues,
    selectedIndex: Int
) {
    val listState = rememberLazyListState(selectedIndex)
    val serviceIntroductionImages = listOf(
        R.drawable.img_service_introduction_1,
        R.drawable.img_service_introduction_2,
        R.drawable.img_service_introduction_3,
        R.drawable.img_service_introduction_4,
        R.drawable.img_service_introduction_5
    )
    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        items(serviceIntroductionImages) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = it),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun ServiceIntroductionScreenPreview() {
    ImdangTheme {
        ServiceIntroductionScreen(
            navController = rememberNavController(),
            selectedIndex = 0
        )
    }
}
