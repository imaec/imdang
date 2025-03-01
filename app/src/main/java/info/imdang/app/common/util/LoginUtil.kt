package info.imdang.app.common.util

import android.content.Context
import com.kakao.sdk.user.UserApiClient

fun kakaoLogin(
    context: Context,
    onSuccess: (token: String) -> Unit,
    onFailure: (error: Throwable) -> Unit
) {
    val userApiClient = UserApiClient.instance
    if (userApiClient.isKakaoTalkLoginAvailable(context)) {
        userApiClient.loginWithKakaoTalk(context) { oAuthToken, error ->
            val kakaoToken = oAuthToken?.accessToken
            if (kakaoToken != null) {
                onSuccess(kakaoToken)
            } else if (error != null) {
                onFailure(error)
            }
        }
    } else {
        userApiClient.loginWithKakaoAccount(context) { oAuthToken, error ->
            val kakaoToken = oAuthToken?.accessToken
            if (kakaoToken != null) {
                onSuccess(kakaoToken)
            } else if (error != null) {
                onFailure(error)
            }
        }
    }
}
