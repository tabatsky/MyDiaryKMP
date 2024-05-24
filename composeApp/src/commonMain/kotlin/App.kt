import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import jatx.mydiarykmp.presentation.view.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalGraphicsApi::class, ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    MainScreen()
}