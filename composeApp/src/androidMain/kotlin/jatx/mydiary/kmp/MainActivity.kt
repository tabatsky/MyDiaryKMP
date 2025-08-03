@file:OptIn(ExperimentalFoundationApi::class, ExperimentalGraphicsApi::class)

package jatx.mydiary.kmp

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import jatx.mydiary.kmp.di.Injector
import jatx.mydiary.kmp.presentation.main.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val injector = Injector()

        setContent {
            val mainViewModel = injector.mainViewModel()
            MainScreen(mainViewModel)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}