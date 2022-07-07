package dev.vengateshm.compose_samples.audio_player_view

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun getRandomColor(): Color {
    return Color(
        android.graphics.Color.argb(
            255,
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
    )
}