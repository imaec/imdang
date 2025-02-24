package info.imdang.app.common.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Composable
inline fun <reified VM : ViewModel> NavController.sharedViewModel(route: String): VM {
    val backStackEntry = remember(currentBackStackEntry) {
        getBackStackEntry(route)
    }
    val viewModel = hiltViewModel<VM>(backStackEntry)
    return viewModel
}
