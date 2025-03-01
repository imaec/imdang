package info.imdang.component.common.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.Gray800_95
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.White
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

lateinit var snackbarHostState: SnackbarHostState

suspend fun showSnackbar(message: String) {
    coroutineScope {
        val snackbarJob = launch {
            snackbarHostState.showSnackbar(message = message)
        }
        delay(2000)
        snackbarJob.cancel()
    }
}

@Composable
fun BoxScope.Snackbar() {
    snackbarHostState = remember { SnackbarHostState() }

    SnackbarHost(
        modifier = Modifier.align(Alignment.BottomCenter),
        hostState = snackbarHostState
    ) { data ->
        Snackbar(message = data.visuals.message)
    }
}

@Composable
private fun Snackbar(message: String) {
    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 108.dp)
            .background(color = Gray800_95, shape = RoundedCornerShape(8.dp))
            .heightIn(min = 56.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clickable {},
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            color = White,
            style = T600_16_22_4,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SnackbarPreview() {
    ImdangTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Snackbar(message = "스낵바 메세지1\n스낵바 메세지2")
        }
    }
}
