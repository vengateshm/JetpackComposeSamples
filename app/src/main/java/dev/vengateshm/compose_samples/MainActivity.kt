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
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.vengateshm.compose_samples.calculator.CalculatorScreen
import dev.vengateshm.compose_samples.calculator.CalculatorViewModel
import dev.vengateshm.compose_samples.maps_spot_finder.presentation.MapScreen
import dev.vengateshm.compose_samples.material_3_ui.MyM3Theme
import dev.vengateshm.compose_samples.material_3_ui.components.ChatMessage
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
            CalculatorScreen(viewModel = viewModel())
        }
    }
}

@Composable
fun MyMaterial3Composable() {
    MyM3Theme(isDynamicColor = true) {
        androidx.compose.material3.Surface(modifier = Modifier.fillMaxSize()) {
            ChatMessage()
        }
    }
}