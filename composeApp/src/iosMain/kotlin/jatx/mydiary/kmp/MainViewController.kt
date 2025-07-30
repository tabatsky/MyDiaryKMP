@file:OptIn(ExperimentalFoundationApi::class, ExperimentalGraphicsApi::class)

package jatx.mydiary.kmp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.window.ComposeUIViewController
import jatx.mydiary.kmp.di.IOSInjector
import jatx.mydiary.kmp.presentation.main.MainScreen
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val injector = IOSInjector()
    return ComposeUIViewController { MainScreen(injector.mainViewModel()) }
}