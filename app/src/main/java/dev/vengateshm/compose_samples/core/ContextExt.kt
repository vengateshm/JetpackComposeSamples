package dev.vengateshm.compose_samples.core

import android.content.Context
import android.content.Intent
import android.os.Build

fun Context.startServiceInForeground(intent: Intent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.startForegroundService(intent)
    } else {
        this.startService(intent)
    }
}