package dev.vengateshm.compose_samples.core

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Float.toDp(): Dp {
    return (this / Resources.getSystem().displayMetrics.density).dp
}