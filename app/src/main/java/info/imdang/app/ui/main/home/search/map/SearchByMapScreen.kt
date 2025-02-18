package info.imdang.app.ui.main.home.search.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.component.common.image.Icon
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T600_18_25_2
import info.imdang.component.theme.White
import info.imdang.resource.R

const val SEARCH_BY_MAP_SCREEN = "searchByMap"

fun NavGraphBuilder.searchByMapScreen(navController: NavController) {
    composable(route = SEARCH_BY_MAP_SCREEN) {
        SearchByMapScreen(navController = navController)
    }
}

@Composable
private fun SearchByMapScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.search_by_map),
                onClickBack = {
                    navController.popBackStack()
                }
            )
        },
        content = { contentPadding ->
            SearchByMapContent(contentPadding)
        }
    )
}

@Composable
private fun SearchByMapContent(contentPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .background(
                        color = Orange50,
                        shape = CircleShape
                    )
                    .padding(all = 12.dp)
                    .background(
                        color = Orange500,
                        shape = CircleShape
                    )
                    .padding(all = 14.dp)
                    .size(24.dp),
                iconResource = R.drawable.ic_map,
                tint = White
            )
            Text(
                text = stringResource(R.string.map_service_info_message),
                style = T600_18_25_2,
                color = Gray500,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun SearchByMapScreenPreview() {
    ImdangTheme {
        SearchByMapScreen(navController = rememberNavController())
    }
}
