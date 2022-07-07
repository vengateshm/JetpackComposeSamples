package dev.vengateshm.compose_samples.lottie_animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import dev.vengateshm.compose_samples.R

class LottieAnimation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Loader()
                }
            }
        }
    }
}

@Composable
fun Loader() {
    val compositionResult: LottieCompositionResult =
        rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.compass)
        ) // Animation file can be added in assets folder too

    val progress by animateLottieCompositionAsState(
        composition = compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 2.0f
    )

    val color by derivedStateOf { Color.Green }

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = color.toArgb(),
            keyPath = arrayOf(
                "donut",
                "Group 1",
                "Fill 1"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = color.toArgb(),
            keyPath = arrayOf(
                "compass needle",
                "Shape 1",
                "Fill 1"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.OPACITY,
            value = 50,
            keyPath = arrayOf(
                "compass needle",
                "Shape 1",
                "Fill 1"
            )
        )
    )

    LottieAnimation(
        composition = compositionResult.value,
        progress = progress,
        dynamicProperties = dynamicProperties
    )
}