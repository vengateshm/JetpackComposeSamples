package dev.vengateshm.compose_samples.glance_app_widget.models


import com.google.gson.annotations.SerializedName

data class LiveMatchesResponse(
    @SerializedName("Stages")
    val stages: List<Stage>,
)