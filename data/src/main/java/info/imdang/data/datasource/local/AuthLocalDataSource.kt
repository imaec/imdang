package info.imdang.data.datasource.local

interface AuthLocalDataSource {

    fun saveAccessToken(accessToken: String)

    fun saveRefreshToken(refreshToken: String)

    fun getAccessToken(): String

    fun getRefreshToken(): String

    fun removeToken()

    fun saveMemberId(memberId: String)

    fun getMemberId(): String

    fun saveLoginType(loginType: String)

    fun getLoginType(): String

    fun saveSocialAccessToken(accessToken: String)

    fun getSocialAccessToken(): String
}
