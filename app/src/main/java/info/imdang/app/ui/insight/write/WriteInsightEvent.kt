package info.imdang.app.ui.insight.write

sealed class WriteInsightEvent {

    data class WriteInsightComplete(val insightId: String) : WriteInsightEvent()
}
