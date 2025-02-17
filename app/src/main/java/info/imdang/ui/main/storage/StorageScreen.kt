package info.imdang.ui.main.storage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.component.theme.ImdangTheme

const val STORAGE_SCREEN = "storage"

fun NavGraphBuilder.storageScreen(
    navController: NavController,
    mainNavController: NavController
) {
    composable(route = STORAGE_SCREEN) {
        StorageScreen(
            navController = navController,
            mainNavController = mainNavController
        )
    }
}

@Composable
private fun StorageScreen(
    navController: NavController,
    mainNavController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { contentPadding ->
            StorageContent(contentPadding)
        }
    )
}

@Composable
private fun StorageContent(contentPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
    }
}

@Preview
@Composable
private fun StorageScreenPreview() {
    ImdangTheme {
        StorageScreen(
            navController = rememberNavController(),
            mainNavController = rememberNavController()
        )
    }
}
