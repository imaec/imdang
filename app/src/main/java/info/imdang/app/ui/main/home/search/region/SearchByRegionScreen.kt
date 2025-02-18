package info.imdang.app.ui.main.home.search.region

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.list.region.SEARCH_BY_REGION_INSIGHT_LIST_SCREEN
import info.imdang.app.ui.main.home.search.map.SEARCH_BY_MAP_SCREEN
import info.imdang.component.common.image.Icon
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray200
import info.imdang.component.theme.Gray25
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_12_16_8
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.White
import info.imdang.resource.R

const val SEARCH_BY_REGION_SCREEN = "searchByRegion"

fun NavGraphBuilder.searchByRegionScreen(navController: NavController) {
    composable(route = SEARCH_BY_REGION_SCREEN) {
        SearchByRegionScreen(navController = navController)
    }
}

@Composable
private fun SearchByRegionScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.search_by_region),
                rightWidget = {
                    Row(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Gray200,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clip(RoundedCornerShape(4.dp))
                            .clickable {
                                navController.navigate(SEARCH_BY_MAP_SCREEN)
                            }
                            .padding(all = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            iconResource = R.drawable.ic_map,
                            tint = Gray700
                        )
                        Text(
                            text = stringResource(R.string.map),
                            style = T500_12_16_8,
                            color = Gray700
                        )
                    }
                },
                onClickBack = {
                    navController.popBackStack()
                }
            )
        },
        content = { contentPadding ->
            SearchByRegionContent(
                navController = navController,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun SearchByRegionContent(
    navController: NavController,
    contentPadding: PaddingValues
) {
    val viewModel = hiltViewModel<SearchByRegionViewModel>()
    val guList = viewModel.guList.collectAsStateWithLifecycle().value
    val dongList = viewModel.dongList.collectAsStateWithLifecycle().value
    Row(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.width(100.dp)) {
            itemsIndexed(guList) { index, gu ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                        .background(color = if (gu.isSelected) Orange500 else Gray25)
                        .clickable {
                            viewModel.selectGu(index)
                        }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = gu.name,
                        style = T600_16_22_4,
                        color = if (gu.isSelected) White else Gray500
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(Gray100)
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(dongList) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                        .background(color = Gray25)
                        .clickable {
                            navController.navigate(
                                route = SEARCH_BY_REGION_INSIGHT_LIST_SCREEN +
                                    "?siGunGu=${viewModel.getSelectedGu()}" +
                                    "&eupMyeonDong=$it"
                            )
                        }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = it,
                        style = T600_16_22_4,
                        color = Color(0xFF333333)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchByRegionScreenPreview() {
    ImdangTheme {
        SearchByRegionScreen(navController = rememberNavController())
    }
}
