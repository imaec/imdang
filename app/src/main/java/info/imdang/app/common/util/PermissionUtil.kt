package info.imdang.app.common.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat

@Composable
fun rememberPermissionGranted(context: Context, permission: String) = remember {
    mutableStateOf(
        value = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    )
}

@Composable
fun rememberPermissionLauncher(
    onGranted: (isGranted: Boolean) -> Unit
) = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission(),
    onGranted
)
