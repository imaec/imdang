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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.model.notification.NotificationCategory
import info.imdang.app.model.notification.NotificationItem
import info.imdang.app.model.notification.NotificationListType
import info.imdang.app.model.notification.NotificationVo
import info.imdang.app.ui.notification.preview.FakeNotificationVIewModel
import info.imdang.component.common.text.EmptyView
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
        NotificationScreen(
            navController = navController,
            viewModel = hiltViewModel()
        )
    }
}

@Composable
private fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.notification),
                onClickBack = { navController.popBackStack() }
            )
        },
        content = { contentPadding ->
            NotificationContent(
                navController = navController,
                viewModel = viewModel,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun NotificationContent(
    navController: NavController,
    viewModel: NotificationViewModel,
    contentPadding: PaddingValues
) {
    val selectedNotificationType by viewModel.selectedNotificationListType
        .collectAsStateWithLifecycle()
    val notifications by viewModel.notificationItems.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp)
    ) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(NotificationListType.entries) {
                CommonChip(
                    text = when (it) {
                        NotificationListType.ALL -> stringResource(R.string.all)
                        NotificationListType.REQUEST_HISTORY -> {
                            stringResource(R.string.request_exchange_history)
                        }
                        NotificationListType.REQUESTED_HISTORY -> {
                            stringResource(R.string.requested_exchange_history)
                        }
                    },
                    isSelected = it == selectedNotificationType,
                    onClick = {
                        viewModel.selectNotificationType(it)
                    }
                )
            }
        }
        LazyColumn(
            modifier = Modifier.padding(top = 12.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(notifications) { index, notificationItem ->
                when (notificationItem) {
                    is NotificationItem.Title -> {
                        if (index != 0) Spacer(Modifier.height(12.dp))
                        Text(text = notificationItem.title, style = T700_22_30_8)
                    }
                    is NotificationItem.Notification -> {
                        if (index > 0) {
                            if (notifications[index - 1] is NotificationItem.Title) {
                                Spacer(Modifier.height(4.dp))
                            }
                        }
                        NotificationItem(notificationItem.notification)
                    }
                    is NotificationItem.Empty -> {
                        if (index != 0) Spacer(Modifier.height(4.dp))
                        EmptyView(emptyMessage = notificationItem.text)
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationItem(notificationVo: NotificationVo) {
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
                        color = if (notificationVo.category == NotificationCategory.REQUESTED) {
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
                    text = if (notificationVo.category == NotificationCategory.REQUESTED) {
                        stringResource(R.string.requested_exchange_history)
                    } else {
                        stringResource(R.string.request_exchange_history)
                    },
                    style = T600_12_16_8,
                    color = if (notificationVo.category == NotificationCategory.REQUESTED) {
                        Orange500
                    } else {
                        Blue11388B
                    }
                )
            }
            Text(
                text = notificationVo.createdAt,
                style = T500_12_16_8,
                color = Gray500
            )
        }
        Text(
            text = notificationVo.message,
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
                    when (notificationVo.category) {
                        NotificationCategory.REQUESTED -> {
                            // todo : 인사이트 상세 이동
                        }
                        NotificationCategory.ACCEPTED -> {
                            // todo : 보관함 이동
                        }
                        NotificationCategory.REJECTED -> {
                            // todo : 인사이트 상세 이동
                        }
                    }
                }
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(
                    when (notificationVo.category) {
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
        NotificationScreen(
            navController = rememberNavController(),
            viewModel = FakeNotificationVIewModel()
        )
    }
}
