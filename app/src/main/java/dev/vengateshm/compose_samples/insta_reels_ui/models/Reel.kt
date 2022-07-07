package dev.vengateshm.compose_samples.insta_reels_ui.models

import android.net.Uri

data class Reel(
    val id: Int,
    private val video: String,
    val userImage: String,
    val userName: String,
    val isLiked: Boolean = false,
    val likesCount: Int,
    val comment: String,
    val commentsCount: Int,
) {
    fun getVideoUri() = Uri.parse("asset:///${video}")
}
