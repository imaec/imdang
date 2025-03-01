package info.imdang.app.ui.my

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.login.LOGIN_SCREEN
import info.imdang.app.ui.my.fake.FakeMyVieModel
import info.imdang.app.ui.my.term.SERVICE_TERM_SCREEN
import info.imdang.app.ui.my.withdraw.WITHDRAW_SCREEN
import info.imdang.app.ui.serviceintroduction.SERVICE_INTRODUCTION_SCREEN
import info.imdang.component.common.dialog.CommonDialog
import info.imdang.component.common.image.Icon
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray600
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T600_12_16_8
import info.imdang.component.theme.T600_14_19_6
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.T600_18_25_2
import info.imdang.component.theme.White
import info.imdang.resource.R
import kotlinx.coroutines.flow.collectLatest

const val MY_SCREEN = "my"

fun NavGraphBuilder.myScreen(navController: NavController) {
    composable(route = MY_SCREEN) {
        MyScreen(
            navController = navController,
            viewModel = hiltViewModel()
        )
    }
}

@Composable
private fun MyScreen(
    navController: NavController,
    viewModel: MyViewModel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.my_page),
                onClickBack = { navController.popBackStack() }
            )
        },
        content = { contentPadding ->
            MyContent(
                navController = navController,
                viewModel = viewModel,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun MyContent(
    navController: NavController,
    viewModel: MyViewModel,
    contentPadding: PaddingValues
) {
    var isShowLogoutDialog by remember { mutableStateOf(false) }

    if (isShowLogoutDialog) {
        CommonDialog(
            iconResource = R.drawable.ic_sign_for_dialog,
            message = stringResource(R.string.logout_info_message),
            positiveButtonText = stringResource(R.string.logout),
            negativeButtonText = stringResource(R.string.cancel),
            onClickPositiveButton = {
                isShowLogoutDialog = false
                viewModel.logout()
            },
            onClickNegativeButton = { isShowLogoutDialog = false },
            onDismiss = { isShowLogoutDialog = false }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest {
            when (it) {
                MyEvent.MoveLoginScreen -> navController.navigate("$LOGIN_SCREEN?isLogout=true") {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    iconResource = R.drawable.ic_profile
                )
                Text(
                    text = "홍길동",
                    style = T600_18_25_2,
                    color = Gray900
                )
            }
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .background(color = White, shape = RoundedCornerShape(6.dp))
                    .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(6.dp))
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { isShowLogoutDialog = true }
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    iconResource = R.drawable.ic_logout,
                    tint = Gray700
                )
                Text(
                    text = stringResource(R.string.logout),
                    style = T600_12_16_8,
                    color = Gray700
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(54.dp)
                .background(color = Orange50, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.written_insight),
                style = T600_16_22_4,
                color = Gray900
            )
            Text(
                text = "16개",
                style = T600_16_22_4,
                color = Orange500
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(54.dp)
                .background(color = Orange50, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.total_exchange),
                style = T600_16_22_4,
                color = Gray900
            )
            Text(
                text = "8건",
                style = T600_16_22_4,
                color = Orange500
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(thickness = 8.dp, color = Gray50)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clickable {
                    navController.navigate(SERVICE_INTRODUCTION_SCREEN)
                }
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.service_introduction),
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
                    navController.navigate(SERVICE_TERM_SCREEN)
                }
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.service_term),
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
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.version_info),
                style = T600_16_22_4,
                color = Gray900
            )
            Text(
                text = "현재 버전 1.0.0",
                style = T500_14_19_6,
                color = Gray600
            )
        }
        HorizontalDivider(color = Gray100)
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(start = 20.dp, bottom = 40.dp)
                .height(42.dp)
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate(WITHDRAW_SCREEN)
                }
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.account_withdraw),
                style = T600_14_19_6,
                color = Gray700
            )
        }
    }
}

@Preview
@Composable
private fun MyScreenPreview() {
    ImdangTheme {
        MyScreen(
            navController = rememberNavController(),
            viewModel = FakeMyVieModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LogoutDialogPreview() {
    ImdangTheme {
        CommonDialog(
            iconResource = R.drawable.ic_sign_for_dialog,
            message = stringResource(R.string.logout_info_message),
            positiveButtonText = stringResource(R.string.logout),
            negativeButtonText = stringResource(R.string.cancel)
        )
    }
}
