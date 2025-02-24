package info.imdang.app.common.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
    route: String
): VM {
    val parentEntry: NavBackStackEntry = remember(this) {
        navController.getBackStackEntry(route)
    }
    val viewModel = hiltViewModel<VM>(parentEntry)
    return viewModel
}
