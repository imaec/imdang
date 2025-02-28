package info.imdang.app.ui.my.term

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.common.ext.encodeUtf8
import info.imdang.app.const.PRIVACY_URL
import info.imdang.app.const.SERVICE_TERM_URL
import info.imdang.component.common.image.Icon
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.common.webview.COMMON_WEB_SCREEN
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_16_22_4
import info.imdang.resource.R

const val SERVICE_TERM_SCREEN = "serviceTerm"

fun NavGraphBuilder.serviceTermScreen(navController: NavController) {
    composable(route = SERVICE_TERM_SCREEN) {
        ServiceTermScreen(navController = navController)
    }
}

@Composable
private fun ServiceTermScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.service_term),
                onClickBack = { navController.popBackStack() }
            )
        },
        content = { contentPadding ->
            ServiceTermContent(
                navController = navController,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun ServiceTermContent(
    navController: NavController,
    contentPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clickable {
                    navController.navigate("$COMMON_WEB_SCREEN?url=${PRIVACY_URL.encodeUtf8()}")
                }
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.privacy_term),
                style = T600_16_22_4,
                color = Gray900
            )
            Icon(
                modifier = Modifier.size(16.dp),
                iconResource = R.drawable.ic_right,
                tint = Gray900
            )
        }
        HorizontalDivider(color = Gray100)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clickable {
                    navController.navigate(
                        "$COMMON_WEB_SCREEN?url=${SERVICE_TERM_URL.encodeUtf8()}"
                    )
                }
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.use_term),
                style = T600_16_22_4,
                color = Gray900
            )
            Icon(
                modifier = Modifier.size(16.dp),
                iconResource = R.drawable.ic_right,
                tint = Gray900
            )
        }
        HorizontalDivider(color = Gray100)
    }
}

@Preview
@Composable
private fun ServiceTermScreenPreview() {
    ImdangTheme {
        ServiceTermScreen(navController = rememberNavController())
    }
}
