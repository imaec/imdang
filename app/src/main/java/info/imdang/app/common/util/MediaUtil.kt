package info.imdang.app.common.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

@Composable
fun rememberPickMediaLauncher(
    onResult: (uri: Uri) -> Unit
) = rememberLauncherForActivityResult(
    ActivityResultContracts.PickVisualMedia()
) { uri ->
    if (uri != null) onResult(uri)
}

fun ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>.launch() {
    launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
}

@Composable
fun rememberTakePictureLauncher(
    onResult: (isSuccess: Boolean) -> Unit
) = rememberLauncherForActivityResult(
    ActivityResultContracts.TakePicture(),
    onResult
)

fun ManagedActivityResultLauncher<Uri, Boolean>.launch(
    context: Context,
    onCreatedFile: (file: File) -> Unit
) {
    createImageFile(context)?.let {
        onCreatedFile(it)
        val uri = it.getUriFromFilePath(context)
        launch(uri)
    }
}

fun getFileFromContentUri(context: Context, contentUri: Uri): File? {
    val fileName = "imdang_${nowDateTimeToString("yyyy_MM_dd_HH_mm_ss")}.jpg"
    val tempFile = File(context.cacheDir, fileName)

    try {
        val inputStream = context.contentResolver.openInputStream(contentUri)
        val outputStream = FileOutputStream(tempFile)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun createImageFile(context: Context): File? {
    val imageFileName = "imdang_${nowDateTimeToString("yyyy_MM_dd_HH_mm_ss")}.jpg"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    )
}

fun File.getUriFromFilePath(context: Context): Uri {
    return FileProvider.getUriForFile(
        context,
        context.packageName,
        this
    )
}
