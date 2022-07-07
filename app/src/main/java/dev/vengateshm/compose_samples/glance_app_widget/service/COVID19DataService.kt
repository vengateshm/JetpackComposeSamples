package dev.vengateshm.compose_samples.glance_app_widget.service

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.preference.PreferenceManager
import com.google.gson.Gson
import dev.vengateshm.compose_samples.glance_app_widget.network.COVIDApiService
import dev.vengateshm.compose_samples.glance_app_widget.receiver.COVID19WidgetReceiver
import dev.vengateshm.compose_samples.glance_app_widget.utils.SUMMARY_RESPONSE_KEY
import dev.vengateshm.compose_samples.glance_app_widget.utils.startForegroundWithNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val COVID_DATA_FOREGROUND_SERVICE_ID = 111

class COVID19DataService : Service() {

    private val coroutineJob: Job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundWithNotification(
            foregroundServiceId = COVID_DATA_FOREGROUND_SERVICE_ID,
            channelId = "COVID19Service",
            channelName = "COVID19 Summary Channel",
            contentTitle = null,
            contentText = "Updating COVID19 global summary...",
            null
        )

        coroutineScope.launch {
            try {
                val summaryResponse = COVIDApiService.getCOVIDApi().getSummary()
                val context = this@COVID19DataService.applicationContext
                val prefs = PreferenceManager.getDefaultSharedPreferences(context)
                val respStr = Gson().toJson(summaryResponse)
                prefs.edit().putString(SUMMARY_RESPONSE_KEY, respStr).commit()

                val componentName = ComponentName(context, COVID19WidgetReceiver::class.java)
                val appWidgetIds =
                    AppWidgetManager.getInstance(context).getAppWidgetIds(componentName)

                val widgetUpdateIntent = Intent(context, COVID19WidgetReceiver::class.java)
                    .apply {
                        this.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                        this.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
                    }
                sendBroadcast(widgetUpdateIntent)

                stopSelf()
            } catch (e: Exception) {
                stopSelf()
                e.printStackTrace()
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        coroutineJob.cancel()
    }
}