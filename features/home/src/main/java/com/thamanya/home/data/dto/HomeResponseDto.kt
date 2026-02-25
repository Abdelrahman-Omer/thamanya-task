package com.thamanya.home.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class HomeResponseDto(
    @SerialName("pagination")
    val pagination: Pagination? = Pagination(),
    @SerialName("sections")
    val sections: List<Section>? = listOf()
) {
    @Serializable
    data class Pagination(
        @SerialName("next_page")
        val nextPage: String? = "",
        @SerialName("total_pages")
        val totalPages: JsonElement? = null
    )

    @Serializable
    data class Section(
        @SerialName("content")
        val content: List<Content>? = listOf(),
        @SerialName("content_type")
        val contentType: String? = "",
        @SerialName("name")
        val name: String? = "",
        @SerialName("order")
        val order: JsonElement? = null,
        @SerialName("type")
        val type: SectionTypeDto? = null
    ) {
        @Serializable
        data class Content(
            @SerialName("article_id")
            val articleId: String? = "",
            @SerialName("audio_url")
            val audioUrl: String? = "",
            @SerialName("audiobook_id")
            val audiobookId: String? = "",
            @SerialName("author_name")
            val authorName: String? = "",
            @SerialName("avatar_url")
            val avatarUrl: String? = "",
            @SerialName("description")
            val description: String? = "",
            @SerialName("duration")
            val duration: JsonElement? = null,
            @SerialName("episode_count")
            val episodeCount: JsonElement? = null,
            @SerialName("episode_id")
            val episodeId: String? = "",
            @SerialName("episode_type")
            val episodeType: String? = "",
            @SerialName("language")
            val language: String? = "",
            @SerialName("name")
            val name: String? = "",
            @SerialName("paid_exclusive_start_time")
            val paidExclusiveStartTime: JsonElement? = null,
            @SerialName("paid_is_early_access")
            val paidIsEarlyAccess: Boolean? = false,
            @SerialName("paid_is_exclusive")
            val paidIsExclusive: Boolean? = false,
            @SerialName("paid_is_exclusive_partially")
            val paidIsExclusivePartially: Boolean? = false,
            @SerialName("paid_is_now_early_access")
            val paidIsNowEarlyAccess: Boolean? = false,
            @SerialName("podcast_id")
            val podcastId: String? = "",
            @SerialName("podcast_name")
            val podcastName: String? = "",
            @SerialName("podcastPopularityScore")
            val podcastPopularityScore: JsonElement? = null,
            @SerialName("podcastPriority")
            val podcastPriority: JsonElement? = null,
            @SerialName("popularityScore")
            val popularityScore: JsonElement? = null,
            @SerialName("priority")
            val priority: JsonElement? = null,
            @SerialName("season_number")
            val seasonNumber: JsonElement? = null,
            @SerialName("number")
            val number: JsonElement? = null,
            @SerialName("score")
            val score: JsonElement? = null,
            @SerialName("release_date")
            val releaseDate: String? = "",
            @SerialName("separated_audio_url")
            val separatedAudioUrl: String? = ""
        )
    }
}