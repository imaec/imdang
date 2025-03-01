package info.imdang.app.ui.login

sealed class LoginEvent {

    data object MoveMainScreen : LoginEvent()

    data object MoveOnboardingScreen : LoginEvent()
}
