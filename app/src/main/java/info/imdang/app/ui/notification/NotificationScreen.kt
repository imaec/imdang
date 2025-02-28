package info.imdang.app.ui.notification

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import info.imdang.app.model.notification.NotificationCategory
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.system.chip.CommonChip
import info.imdang.component.theme.Blue11388B
import info.imdang.component.theme.BlueEDF3FF
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_12_16_8
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T600_12_16_8
import info.imdang.component.theme.T600_14_19_6
import info.imdang.component.theme.T700_22_30_8
import info.imdang.component.theme.White
import info.imdang.resource.R

const val NOTIFICATION_SCREEN = "notification"

fun NavGraphBuilder.notificationScreen(navController: NavController) {
    composable(route = NOTIFICATION_SCREEN) {
        NotificationScreen(navController = navController)
    }
}

@Composable
private fun NotificationScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.notification),
                onClickBack = { navController.popBackStack() }
            )
        },
        content = { contentPadding ->
            NotificationContent(contentPadding)
        }
    )
}

@Composable
private fun NotificationContent(contentPadding: PaddingValues) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val notificationStatus = listOf(
        stringResource(R.string.all),
        stringResource(R.string.request_exchange_history),
        stringResource(R.string.requested_exchange_history)
    )
    val newNotifications = listOf(
        NotificationCategory.REQUESTED,
        NotificationCategory.ACCEPTED,
        NotificationCategory.REJECTED
    ).filter {
        when (selectedIndex) {
            1 -> it != NotificationCategory.REQUESTED
            2 -> it == NotificationCategory.REQUESTED
            else -> true
        }
    }
    val lastNotifications = listOf(
        NotificationCategory.REQUESTED,
        NotificationCategory.ACCEPTED,
        NotificationCategory.REJECTED
    ).filter {
        when (selectedIndex) {
            1 -> it != NotificationCategory.REQUESTED
            2 -> it == NotificationCategory.REQUESTED
            else -> true
        }
    }
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp)
    ) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(notificationStatus) { index, status ->
                CommonChip(
                    text = status,
                    isSelected = index == selectedIndex,
                    onClick = {
                        selectedIndex = index
                    }
                )
            }
        }
        LazyColumn(
            modifier = Modifier.padding(top = 12.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.new_notification),
                    style = T700_22_30_8
                )
            }
            itemsIndexed(newNotifications) { index, notification ->
                if (index == 0) Spacer(Modifier.height(4.dp))
                NotificationItem(notification)
            }
            item {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.last_notification),
                    style = T700_22_30_8
                )
            }
            itemsIndexed(lastNotifications) { index, notification ->
                if (index == 0) Spacer(Modifier.height(4.dp))
                NotificationItem(notification)
            }
        }
    }
}

@Composable
private fun NotificationItem(notificationCategory: NotificationCategory) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(12.dp))
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(25.dp)
                    .background(
                        color = if (notificationCategory == NotificationCategory.REQUESTED) {
                            Orange50
                        } else {
                            BlueEDF3FF
                        },
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (notificationCategory == NotificationCategory.REQUESTED) {
                        stringResource(R.string.requested_exchange_history)
                    } else {
                        stringResource(R.string.request_exchange_history)
                    },
                    style = T600_12_16_8,
                    color = if (notificationCategory == NotificationCategory.REQUESTED) {
                        Orange500
                    } else {
                        Blue11388B
                    }
                )
            }
            Text(
                text = "방금 전",
                style = T500_12_16_8,
                color = Gray500
            )
        }
        Text(
            text = "길동님이 인사이트 교환을 수락했어요.\n교환한 인사이트를 보관함에서 확인해보세요.",
            style = T500_16_22_4,
            color = Gray900
        )
        Box(
            modifier = Modifier
                .height(42.dp)
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    // todo : 알림 클릭 동작
                }
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(
                    when (notificationCategory) {
                        NotificationCategory.REQUESTED -> R.string.check_insight
                        NotificationCategory.ACCEPTED -> R.string.check_storage
                        NotificationCategory.REJECTED -> R.string.retry_request
                    }
                ),
                style = T600_14_19_6,
                color = Gray700
            )
        }
    }
}

@Preview
@Composable
private fun NotificationScreenPreview() {
    ImdangTheme {
        NotificationScreen(navController = rememberNavController())
    }
}
