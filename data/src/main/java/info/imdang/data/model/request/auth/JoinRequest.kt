package info.imdang.data.model.request.auth

data class JoinRequest(
    val nickname: String,
    val birthDate: String,
    val gender: String?,
    val deviceToken: String
)
