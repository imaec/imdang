package info.imdang.app.ui.insight.write.basic

import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.google.gson.Gson
import info.imdang.app.common.ext.sharedViewModel
import info.imdang.app.const.KAKAO_ADDRESS_URL
import info.imdang.app.ui.insight.write.WRITE_INSIGHT_SCREEN
import info.imdang.app.ui.insight.write.WriteInsightViewModel
import info.imdang.app.ui.insight.write.basic.KakaoAddressJavascriptInterface.Companion.KAKAO_ADDRESS_INTERFACE_NAME
import info.imdang.component.common.topbar.TopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import timber.log.Timber

const val KAKAO_ADDRESS_WEB_SCREEN = "kakaoAddressWeb"

fun NavGraphBuilder.kakaoAddressWebScreen(navController: NavController) {
    composable(route = KAKAO_ADDRESS_WEB_SCREEN) {
        CommonWebScreen(
            navController = navController,
            viewModel = it.sharedViewModel(navController, WRITE_INSIGHT_SCREEN)
        )
    }
}

@Suppress("DEPRECATION")
@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
private fun CommonWebScreen(
    navController: NavController,
    viewModel: WriteInsightViewModel
) {
    val state = rememberWebViewState(url = KAKAO_ADDRESS_URL)
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
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()

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
                    webView.addJavascriptInterface(
                        object : KakaoAddressJavascriptInterface {
                            @JavascriptInterface
                            override fun processDATA(address: String) {
                                try {
                                    val kakaoAddressVo = Gson().fromJson(
                                        address,
                                        KakaoAddressVo::class.java
                                    )
                                    Geocoder(context).getFromLocationName(
                                        address,
                                        1
                                    )?.let {
                                        with(viewModel) {
                                            updateAddress(kakaoAddressVo.jibunAddress)
                                            updateComplexName(kakaoAddressVo.buildingName)
                                            updateLatitude(it[0].latitude)
                                            updateLongitude(it[0].longitude)
                                        }
                                        coroutineScope.launch(Dispatchers.Main) {
                                            navController.popBackStack()
                                        }
                                    }
                                } catch (e: JSONException) {
                                    Timber.e(
                                        message = "  ## JSON 파싱 오류: ${Log.getStackTraceString(e)}"
                                    )
                                }
                            }
                        },
                        KAKAO_ADDRESS_INTERFACE_NAME
                    )
                }
            )
        }
    )
}

interface KakaoAddressJavascriptInterface {

    @JavascriptInterface
    fun processDATA(address: String)

    companion object {
        const val KAKAO_ADDRESS_INTERFACE_NAME = "Android"
    }
}

data class KakaoAddressVo(
    val roadAddress: String,
    val jibunAddress: String,
    val zonecode: String,
    val buildingName: String,
    val apartment: String
)
