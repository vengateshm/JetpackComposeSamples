package dev.vengateshm.compose_samples

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.vengateshm.compose_samples.device_sensors.SensorData
import dev.vengateshm.compose_samples.material_3_ui.components.Material3Feed
import dev.vengateshm.compose_samples.material_3_ui.ui.theme.Material3AppTheme
import dev.vengateshm.compose_samples.parallax_scroll_effect.ParallaxComponent
import dev.vengateshm.compose_samples.ui.theme.ProductSansFontTheme

@AndroidEntryPoint
//@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMaterial2Composable()
//            MyMaterial3Composable()
        }
        //launchActivity<MeditationHomeActivity>()
//        launchActivity<CoroutinesCancellationActivity>()
    }
}

inline fun <reified T> Context.launchActivity() = this.startActivity(Intent(this, T::class.java))

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyMaterial2Composable() {
    ProductSansFontTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
//            MultiSelectVerticalGridListScreen()
//            ShimmerVerticalGridListScreen()
//            ReelsView()
//            NestedLazyColumn()
//            MultipleScreenSizeComposable()
//            MotionLayoutSample()
//            MapScreen()
//            CalculatorScreen(viewModel = viewModel())
//            ParallaxComponent()
            SensorData()
        }
    }
}

@Composable
fun MyMaterial3Composable() {
//    MyM3Theme(isDynamicColor = true) {
//        androidx.compose.material3.Surface(modifier = Modifier.fillMaxSize()) {
//            ChatMessage()
//        }
//    }
    Material3AppTheme {
        androidx.compose.material3.Surface(
            modifier = Modifier.fillMaxSize(),
            color = androidx.compose.material3.MaterialTheme.colorScheme.background
        ) {
            Material3Feed()
        }
    }
}