@file:OptIn(ExperimentalFoundationApi::class, ExperimentalGraphicsApi::class)

package jatx.mydiary.kmp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jatx.mydiary.kmp.di.JVMInjector
import jatx.mydiary.kmp.presentation.main.MainScreen

fun main() = application {
    val injector = JVMInjector()

    Window(
        onCloseRequest = ::exitApplication,
        title = "MyDiaryKMP",
    ) {
        MainScreen(injector.mainViewModel())
    }
}