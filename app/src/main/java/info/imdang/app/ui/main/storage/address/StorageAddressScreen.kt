package info.imdang.app.ui.main.storage.address

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.main.storage.address.preview.FakeStorageAddressViewModel
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.button.RadioButton
import info.imdang.component.system.gradient.ButtonGradient
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_14_19_6
import info.imdang.resource.R

const val STORAGE_ADDRESS_SCREEN = "storageAddress"

fun NavGraphBuilder.storageAddressScreen(navController: NavController) {
    composable(route = "$STORAGE_ADDRESS_SCREEN?selectedPage={selectedPage}") {
        StorageAddressScreen(
            navController = navController,
            viewModel = hiltViewModel()
        )
    }
}

@Composable
private fun StorageAddressScreen(
    navController: NavController,
    viewModel: StorageAddressViewModel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.region_all),
                onClickBack = {
                    navController.popBackStack()
                }
            )
        },
        content = { contentPadding ->
            StorageAddressContent(
                viewModel = viewModel,
                contentPadding = contentPadding
            )
        },
        bottomBar = {
            CommonButton(
                buttonText = stringResource(R.string.select_complete),
                onClick = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedPage", viewModel.selectedPage.value)
                    navController.popBackStack()
                }
            )
        }
    )
}

@Composable
private fun StorageAddressContent(
    viewModel: StorageAddressViewModel,
    contentPadding: PaddingValues
) {
    val addresses by viewModel.addresses.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        LazyColumn {
            items(addresses) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(86.dp)
                            .clickable {
                                viewModel.onClickAddress(it)
                            }
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = it.toSiGuDong(),
                                style = T500_14_19_6,
                                color = Gray900
                            )
                            Row {
                                Text(
                                    text = stringResource(R.string.complex),
                                    style = T500_14_19_6,
                                    color = Gray700
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${it.complexCount}개",
                                    style = T500_14_19_6,
                                    color = Orange500
                                )
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .width(1.dp)
                                        .height(14.dp)
                                        .background(Gray100)
                                )
                                Text(
                                    text = stringResource(R.string.insight),
                                    style = T500_14_19_6,
                                    color = Gray700
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${it.insightCount}개",
                                    style = T500_14_19_6,
                                    color = Orange500
                                )
                            }
                        }
                        RadioButton(
                            isSelected = it.isSelected,
                            isEnabled = false
                        )
                    }
                    HorizontalDivider(color = Gray100)
                }
            }
        }
        ButtonGradient(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Preview
@Composable
private fun StorageAddressScreenPreview() {
    ImdangTheme {
        StorageAddressScreen(
            navController = rememberNavController(),
            viewModel = FakeStorageAddressViewModel()
        )
    }
}
