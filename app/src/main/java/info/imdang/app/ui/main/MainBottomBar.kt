package info.imdang.app.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.insight.write.WRITE_INSIGHT_SCREEN
import info.imdang.component.common.image.Icon
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray800
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_12_16_8
import info.imdang.component.theme.White
import info.imdang.resource.R

@Composable
fun MainBottomBar(
    navController: NavController,
    mainNavController: NavController
) {
    NavigationBar(containerColor = White) {
        Column {
            HorizontalDivider(color = Gray100)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 55.dp)
                    .padding(top = 16.dp, bottom = 24.dp)
                    .background(White),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavigationItem(
                    mainNavController = mainNavController,
                    mainBottomBarType = MainBottomBarType.HOME
                )
                Icon(
                    modifier = Modifier
                        .size(52.dp)
                        .shadow(elevation = 4.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .background(color = Gray800)
                        .clickable {
                            navController.navigate(WRITE_INSIGHT_SCREEN)
                        }
                        .padding(all = 14.dp),
                    iconResource = R.drawable.ic_pen,
                    tint = White
                )
                BottomNavigationItem(
                    mainNavController = mainNavController,
                    mainBottomBarType = MainBottomBarType.STORAGE
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationItem(
    mainNavController: NavController,
    mainBottomBarType: MainBottomBarType
) {
    val currentRoute =
        mainNavController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .clickable {
                if (currentRoute == mainBottomBarType.route) return@clickable

                mainNavController.navigate(mainBottomBarType.route) {
                    launchSingleTop = true
                    popUpTo(mainBottomBarType.route) { inclusive = false }
                }
            }
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                iconResource = when (mainBottomBarType) {
                    MainBottomBarType.HOME -> R.drawable.ic_home
                    MainBottomBarType.STORAGE -> R.drawable.ic_storage
                },
                tint = if (mainBottomBarType.route == currentRoute) Gray900 else Gray500
            )
            Text(
                text = mainBottomBarType.title,
                style = T500_12_16_8,
                color = if (mainBottomBarType.route == currentRoute) Gray900 else Gray500
            )
        }
    }
}

@Preview
@Composable
private fun MainBottomBarPreview() {
    ImdangTheme {
        MainBottomBar(
            navController = rememberNavController(),
            mainNavController = rememberNavController()
        )
    }
}
