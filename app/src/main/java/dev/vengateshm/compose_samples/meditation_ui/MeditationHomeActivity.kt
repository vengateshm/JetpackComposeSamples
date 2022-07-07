package dev.vengateshm.compose_samples.meditation_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.vengateshm.compose_samples.meditation_ui.ui.MeditationUITheme

class MeditationHomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeditationUITheme {
                HomeScreen()
            }
        }
    }
}