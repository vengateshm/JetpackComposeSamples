package dev.vengateshm.compose_samples.glance_app_widget.service

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.preference.PreferenceManager
import com.google.gson.Gson
import dev.vengateshm.compose_samples.glance_app_widget.network.LiveScoreApiService
import dev.vengateshm.compose_samples.glance_app_widget.receiver.LiveCricketScoreWidgetReceiver
import dev.vengateshm.compose_samples.glance_app_widget.utils.LIVE_MATCHES_RESPONSE_KEY
import dev.vengateshm.compose_samples.glance_app_widget.utils.startForegroundWithNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val LIVE_SCORE_FOREGROUND_SERVICE_ID = 112

class LiveScoreService : Service() {

    private val coroutineJob: Job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundWithNotification(
            foregroundServiceId = LIVE_SCORE_FOREGROUND_SERVICE_ID,
            channelId = "LiveScoreService",
            channelName = "Live Score Channel",
            contentTitle = null,
            contentText = "Updating live cricket score...",
            null
        )

        coroutineScope.launch {
            try {
                val liveMatchesResponse = LiveScoreApiService.getLiveScoreApi().getLiveMatches()
                val context = this@LiveScoreService.applicationContext
                val prefs = PreferenceManager.getDefaultSharedPreferences(context)
                val respStr = Gson().toJson(liveMatchesResponse)
                prefs.edit().putString(LIVE_MATCHES_RESPONSE_KEY, respStr).commit()

                val componentName =
                    ComponentName(context, LiveCricketScoreWidgetReceiver::class.java)
                val appWidgetIds =
                    AppWidgetManager.getInstance(context).getAppWidgetIds(componentName)

                val widgetUpdateIntent = Intent(context, LiveCricketScoreWidgetReceiver::class.java)
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