package info.imdang.app.ui.insight.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.insight.InsightItem
import info.imdang.app.ui.insight.InsightItemType
import info.imdang.component.common.image.Icon
import info.imdang.component.common.topbar.CollapsingScaffold
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.common.topbar.exitUntilCollapsedScrollBehavior
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_14_19_6
import info.imdang.resource.R

const val INSIGHT_DETAIL_SCREEN = "insightDetail"

fun NavGraphBuilder.insightDetailScreen(navController: NavController) {
    composable(route = INSIGHT_DETAIL_SCREEN) {
        InsightDetailScreen(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InsightDetailScreen(navController: NavController) {
    val scrollBehavior = exitUntilCollapsedScrollBehavior()

    CollapsingScaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        scrollBehavior = scrollBehavior,
        topBar = {
            InsightDetailTopBar(navController = navController)
        },
        collapsingContent = {
            InsightDetailCollapsingContent()
        },
        content = {
            InsightDetailContent()
        }
    )
}

@Composable
private fun InsightDetailTopBar(navController: NavController) {
    TopBar(
        hasStatusBarsPadding = false,
        rightWidget = {
            Row {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable {
                            // todo : 신고
                        }
                        .padding(6.dp),
                    iconResource = R.drawable.ic_report,
                    tint = Gray900
                )
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable {
                            // todo : 공유
                        }
                        .padding(6.dp),
                    iconResource = R.drawable.ic_share,
                    tint = Gray900
                )
            }
        },
        onClickBack = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun InsightDetailContent() {
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.basic_info),
            style = T600_14_19_6,
            color = Gray900
        )
        HorizontalDivider(color = Gray100)
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(20) {
                InsightItem(
                    itemType = InsightItemType.HORIZONTAL,
                    coverImage = "",
                    region = "강남구 신논현동",
                    recommendCount = 24,
                    title = "123",
                    nickname = "홍길동",
                    onClick = {
                        // todo : 인사이트 상세로 이동
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun InsightDetailScreenPreview() {
    ImdangTheme {
        InsightDetailScreen(navController = rememberNavController())
    }
}
