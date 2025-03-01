@file:Suppress("DEPRECATION")

package info.imdang.app.common.util

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import info.imdang.app.BuildConfig

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

fun googleLogin(
    context: Context,
    googleLoginLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    onFailure: (error: Throwable) -> Unit
) {
    val googleSignInClient = context.googleSingInClient()
    val signInIntent = googleSignInClient.signInIntent
    try {
        googleLoginLauncher.launch(signInIntent)
    } catch (e: IntentSender.SendIntentException) {
        onFailure(e)
    }
}

fun logout(context: Context) {
    UserApiClient.instance.logout {}
    context.googleSingInClient().signOut()
}

private fun Context.googleSingInClient(): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestServerAuthCode(BuildConfig.GOOGLE_WEB_CLIENT_ID)
        .build()
    return GoogleSignIn.getClient(this, gso)
}
