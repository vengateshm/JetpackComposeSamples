package dev.vengateshm.compose_samples.glance_app_widget.network

import dev.vengateshm.compose_samples.glance_app_widget.models.SummaryResponse
import retrofit2.http.GET

interface COVIDApi {
    @GET("summary")
    suspend fun getSummary(): SummaryResponse

    companion object {
        const val BASE_URL = "https://api.covid19api.com/"
    }
}