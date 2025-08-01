@file:OptIn(ExperimentalFoundationApi::class, ExperimentalGraphicsApi::class)

package jatx.mydiary.kmp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import jatx.mydiary.kmp.consumer.EventConsumer
import jatx.mydiary.kmp.di.Injector
import jatx.mydiary.kmp.presentation.main.MainScreen
import java.util.Calendar

class MainActivity : ComponentActivity() {
    private var calendar: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val injector = Injector()

        setContent {
            val mainViewModel = injector.mainViewModel()
            EventConsumer(mainViewModel.showDateTimePickerChannel) {
                selectDateAndTime {
                    val time = calendar?.timeInMillis ?: System.currentTimeMillis()
                    mainViewModel.createEntry(time)
                }
            }
            MainScreen(mainViewModel)
        }
    }

    private fun selectDateAndTime(onSuccess: () -> Unit) {
        calendar = Calendar.getInstance()
        calendar?.timeInMillis = System.currentTimeMillis()

        val year = calendar?.get(Calendar.YEAR) ?: 0
        val month = calendar?.get(Calendar.MONTH) ?: 0
        val day = calendar?.get(Calendar.DAY_OF_MONTH) ?: 0

        val dpd = DatePickerDialog(this, { _, year, month, day ->
            calendar?.set(Calendar.YEAR, year)
            calendar?.set(Calendar.MONTH, month)
            calendar?.set(Calendar.DAY_OF_MONTH, day)

            selectTime(onSuccess)
        }, year, month, day)

        dpd.show()
    }

    private fun selectTime(onSuccess: () -> Unit) {
        val hour = calendar?.get(Calendar.HOUR_OF_DAY) ?: 0
        val minute = calendar?.get(Calendar.MINUTE) ?: 0

        val tpd = TimePickerDialog(this, { _: TimePicker, hour: Int, minute: Int ->
            calendar?.set(Calendar.HOUR_OF_DAY, hour)
            calendar?.set(Calendar.MINUTE, minute)

            onSuccess()
        }, hour, minute, true)

        tpd.show()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}