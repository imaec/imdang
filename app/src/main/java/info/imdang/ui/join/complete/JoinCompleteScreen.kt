package info.imdang.ui.join.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import info.imdang.component.system.button.CommonButton
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T700_24_33_6
import info.imdang.resource.R
import info.imdang.ui.login.LOGIN_SCREEN
import info.imdang.ui.main.MAIN_SCREEN

const val JOIN_COMPLETE_SCREEN = "joinComplete"

fun NavGraphBuilder.joinCompleteScreen(navController: NavController) {
    composable(route = JOIN_COMPLETE_SCREEN) {
        JoinCompleteScreen(navController = navController)
    }
}

@Composable
private fun JoinCompleteScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { contentPadding ->
            JoinContent(
                navController = navController,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun JoinContent(
    navController: NavController,
    contentPadding: PaddingValues
) {
    Column(modifier = Modifier.padding(contentPadding)) {
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    iconResource = R.drawable.ic_check_for_join
                )
                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = stringResource(R.string.join_complete_title),
                    style = T700_24_33_6,
                    color = Gray900
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.join_complete_sub_title),
                    style = T500_16_22_4,
                    color = Gray700,
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier.padding(top = 40.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        stringResource(R.string.join_complete_description1),
                        stringResource(R.string.join_complete_description2),
                        stringResource(R.string.join_complete_description3)
                    ).forEach { stringResource ->
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 38.dp)
                                .fillMaxWidth()
                                .background(
                                    color = Gray50,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(20.dp),
                            text = stringResource,
                            style = T500_14_19_6,
                            color = Gray900,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        CommonButton(
            buttonText = stringResource(R.string.start),
            onClick = {
                navController.navigate(MAIN_SCREEN) {
                    popUpTo(LOGIN_SCREEN) { inclusive = true }
                }
            }
        )
    }
}

@Preview
@Composable
private fun JoinCompleteScreenPreview() {
    ImdangTheme {
        JoinCompleteScreen(navController = rememberNavController())
    }
}
