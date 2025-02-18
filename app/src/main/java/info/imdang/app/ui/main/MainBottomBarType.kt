package info.imdang.app.ui.main

import info.imdang.app.ui.main.home.HOME_SCREEN
import info.imdang.app.ui.main.storage.STORAGE_SCREEN

enum class MainBottomBarType(val route: String, val title: String) {
    HOME(route = HOME_SCREEN, title = "홈"),
    STORAGE(route = STORAGE_SCREEN, title = "보관함")
}
