package info.imdang.component.common.webview

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import info.imdang.component.common.topbar.TopBar
import java.net.URLDecoder

const val COMMON_WEB_SCREEN = "commonWeb"

fun NavGraphBuilder.commonWebScreen(navController: NavController) {
    composable(route = "$COMMON_WEB_SCREEN?url={url}") {
        val url = URLDecoder.decode(it.arguments?.getString("url"), Charsets.UTF_8.name())
        CommonWebScreen(
            navController = navController,
            url = url
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun CommonWebScreen(
    navController: NavController,
    url: String
) {
    val state = rememberWebViewState(url = url)
    val navigator = rememberWebViewNavigator()

    BackHandler {
        if (navigator.canGoBack) {
            navigator.navigateBack()
        } else {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                onClickBack = {
                    navController.popBackStack()
                }
            )
        },
        content = { contentPadding ->
            WebView(
                state = state,
                navigator = navigator,
                client = object : AccompanistWebViewClient() {
                },
                chromeClient = object : AccompanistWebChromeClient() {
                },
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                onCreated = { webView ->
                    webView.settings.javaScriptEnabled = true
                    webView.settings.domStorageEnabled = true
                }
            )
        }
    )
}
