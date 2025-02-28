package info.imdang.app.ui.my.withdraw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.login.LOGIN_SCREEN
import info.imdang.component.common.image.Icon
import info.imdang.component.common.modifier.clickableWithoutRipple
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.button.CommonButtonType
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T600_18_25_2
import info.imdang.resource.R

const val WITHDRAW_SCREEN = "withdraw"

fun NavGraphBuilder.withdrawScreen(navController: NavController) {
    composable(route = WITHDRAW_SCREEN) {
        WithdrawScreen(navController = navController)
    }
}

@Composable
private fun WithdrawScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.account_withdraw),
                onClickBack = { navController.popBackStack() }
            )
        },
        content = { contentPadding ->
            WithdrawContent(
                navController = navController,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun WithdrawContent(
    navController: NavController,
    contentPadding: PaddingValues
) {
    var isAgreeWithdraw by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.withdraw_caution_title),
            style = T600_18_25_2,
            color = Gray900
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = stringResource(R.string.withdraw_caution_content_1),
            style = T500_14_19_6,
            color = Gray700
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = stringResource(R.string.withdraw_caution_content_2),
            style = T500_14_19_6,
            color = Gray700
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = stringResource(R.string.withdraw_caution_content_3),
            style = T500_14_19_6,
            color = Gray700
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = if (isAgreeWithdraw) Orange50 else Gray50,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .clickableWithoutRipple {
                    isAgreeWithdraw = !isAgreeWithdraw
                }
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                iconResource = if (isAgreeWithdraw) {
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_check_gray
                }
            )
            Text(
                text = stringResource(R.string.withdraw_agree),
                style = T500_14_19_6,
                color = Gray900
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        CommonButton(
            buttonType = CommonButtonType.GHOST,
            buttonText = stringResource(R.string.withdraw),
            horizontalPadding = 0.dp,
            isEnabled = isAgreeWithdraw,
            onClick = {
                navController.navigate("$LOGIN_SCREEN?isWithdraw=true") {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }
}

@Preview
@Composable
private fun WithdrawScreenPreview() {
    ImdangTheme {
        WithdrawScreen(navController = rememberNavController())
    }
}
