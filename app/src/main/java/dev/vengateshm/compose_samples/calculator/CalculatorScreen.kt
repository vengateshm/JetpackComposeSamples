package dev.vengateshm.compose_samples.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.vengateshm.compose_samples.calculator.ui.MediumGray

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val state = viewModel.state
    val buttonSpacing = 8.dp

    Calculator(
        modifier = Modifier
            .fillMaxSize()
            .background(MediumGray)
            .padding(16.dp),
        state = state,
        buttonSpacing = buttonSpacing,
        onAction = viewModel::onAction
    )
}