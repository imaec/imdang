package info.imdang.app.ui.insight.detail

sealed class InsightDetailEvent {

    data object MoveHomeExchange : InsightDetailEvent()

    data object MoveStorage : InsightDetailEvent()
}
