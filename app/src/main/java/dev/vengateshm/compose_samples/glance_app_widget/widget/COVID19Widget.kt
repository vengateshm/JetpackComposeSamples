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
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.google.gson.Gson
import dev.vengateshm.compose_samples.R
import dev.vengateshm.compose_samples.core.startServiceInForeground
import dev.vengateshm.compose_samples.glance_app_widget.models.SummaryResponse
import dev.vengateshm.compose_samples.glance_app_widget.service.COVID19DataService
import dev.vengateshm.compose_samples.glance_app_widget.utils.SUMMARY_RESPONSE_KEY

class COVID19Widget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        val context = LocalContext.current
        //val prefs = currentState<Preferences>()

        var response: SummaryResponse? = null
        try {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val respStr = prefs.getString(SUMMARY_RESPONSE_KEY, "")
            if (respStr.isNullOrEmpty()) {
                context.startServiceInForeground(Intent(context, COVID19DataService::class.java))
            }
            response = Gson().fromJson(respStr, SummaryResponse::class.java)
        } catch (e: Exception) {
            Log.d("COVID19Widget", e.toString())
        }

        val confirmedDef = "Confirmed Cases - NA"
        val deathsDef = "Deaths - NA"
        val recoveredDef = "Recovered - NA"

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(imageProvider = ImageProvider(R.drawable.summary_widget_bg))
                .appWidgetBackground()
                .padding(16.dp)
        ) {
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = GlanceModifier.size(24.dp),
                    provider = ImageProvider(R.drawable.ic_covid_19),
                    contentDescription = "Refresh Icon",
                )
                Spacer(
                    modifier = GlanceModifier
                        .height(8.dp)
                )
                Text(
                    text = "COVID19 Global Summary",
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .defaultWeight(),
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontSize = 18.sp,
                    ),
                )
                Image(
                    modifier = GlanceModifier.size(24.dp)
                        .clickable(actionRunCallback<RefreshButtonClickAction>()),
                    provider = ImageProvider(R.drawable.ic_outline_refresh_24),
                    contentDescription = "Refresh Icon",
                )
            }
            Spacer(
                modifier = GlanceModifier
                    .height(8.dp)
            )
            Summary(
                title = "New",
                confirmed = response?.let { "Confirmed Cases - ${response.global.newConfirmed}" }
                    ?: confirmedDef,
                deaths = response?.let { "Deaths - ${response.global.newDeaths}" }
                    ?: deathsDef,
                recovered = response?.let { "Recovered - ${response.global.newRecovered}" }
                    ?: recoveredDef
            )
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0XFFFFFFFF))
            ) {
            }
            Summary(
                title = "Total",
                confirmed = response?.let { "Confirmed Cases - ${response.global.totalConfirmed}" }
                    ?: confirmedDef,
                deaths = response?.let { "Deaths - ${response.global.totalDeaths}" }
                    ?: deathsDef,
                recovered = response?.let { "Recovered - ${response.global.totalRecovered}" }
                    ?: recoveredDef
            )
        }
    }
}

@Composable
fun Summary(title: String, confirmed: String, deaths: String, recovered: String) {
    Column {
        Text(
            text = title,
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(8.dp),
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 18.sp,
            ),
        )
        Spacer(
            modifier = GlanceModifier
                .height(8.dp)
        )
        Text(
            text = confirmed,
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(8.dp),
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 18.sp,
            ),
        )
        Spacer(
            modifier = GlanceModifier
                .height(8.dp)
        )
        Text(
            text = deaths,
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(8.dp),
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 18.sp,
            ),
        )
        Text(
            text = recovered,
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(8.dp),
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 18.sp,
            ),
        )
    }
}

class RefreshButtonClickAction : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        context.startServiceInForeground(Intent(context, COVID19DataService::class.java))
    }
}