package info.imdang.app.ui.my.withdraw

sealed class WithdrawEvent {

    data object MoveLoginScreen : WithdrawEvent()
}
