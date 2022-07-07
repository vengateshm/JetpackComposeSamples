package dev.vengateshm.compose_samples.glance_app_widget.widget

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.layout.*
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.google.gson.Gson
import dev.vengateshm.compose_samples.R
import dev.vengateshm.compose_samples.core.startServiceInForeground
import dev.vengateshm.compose_samples.glance_app_widget.models.LiveMatchesResponse
import dev.vengateshm.compose_samples.glance_app_widget.service.LiveScoreService
import dev.vengateshm.compose_samples.glance_app_widget.utils.LIVE_MATCHES_RESPONSE_KEY

class LiveCricketScoreWidget : GlanceAppWidget() {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(imageProvider = ImageProvider(R.drawable.widget_initial_layout_bg))
                .appWidgetBackground()
                .padding(16.dp)
        ) {
            var response: LiveMatchesResponse? = null
            try {
                val prefs = PreferenceManager.getDefaultSharedPreferences(context)
                val respStr = prefs.getString(LIVE_MATCHES_RESPONSE_KEY, "")
                if (respStr.isNullOrEmpty()) {
                    context.startServiceInForeground(Intent(context, LiveScoreService::class.java))
                }
                response = Gson().fromJson(respStr, LiveMatchesResponse::class.java)
            } catch (e: Exception) {
                Log.d("LiveCricketScoreWidget", e.toString())
            }

            if (response != null) {
                response.stages.find { stage ->
                    stage.snm == "South Africa v India" ||
                            stage.scd == "south-africa-v-india"
                }?.let {
                    if (it.events.isNullOrEmpty().not()) {
                        it.events[0].let { event ->
                            Text(text = event.epsL)
                            Spacer(
                                modifier = GlanceModifier
                                    .height(8.dp)
                            )
                            Text(
                                text = event.eCo,
                                style = TextStyle(
                                    fontStyle = FontStyle.Normal,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            } else {
                Text(text = "Live score not available")
            }
            Spacer(
                modifier = GlanceModifier
                    .height(16.dp)
            )
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .clickable(actionRunCallback<RefreshScoreClickAction>()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Refresh Score",
                    modifier = GlanceModifier
                        .wrapContentSize(),
                    style = TextStyle(
                        color = ColorProvider(Color(0XFF777777)),
                        fontSize = 18.sp,
                    ),
                )
                Image(
                    modifier = GlanceModifier.size(24.dp),
                    provider = ImageProvider(R.drawable.ic_outline_refresh_24_black),
                    contentDescription = "Refresh Icon",
                )
            }
        }
    }
}

class RefreshScoreClickAction : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        context.startServiceInForeground(Intent(context, LiveScoreService::class.java))
    }
}