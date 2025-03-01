package info.imdang.app.ui.splash

sealed class SplashEvent {

    data object MoveLoginScreen : SplashEvent()

    data object MoveMainScreen : SplashEvent()
}
