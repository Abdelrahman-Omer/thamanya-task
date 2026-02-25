package com.thamanya.home.domain

data class HomeResponse(
    val pagination: Pagination,
    val sections: List<Section>
) {
    data class Pagination(
        val nextPage: String,
        val totalPages: Int
    ) {
        companion object {
            fun empty() = Pagination(
                nextPage = "",
                totalPages = 0
            )
        }
    }

    data class Section(
        val content: List<Content>,
        val contentType: String,
        val name: String,
        val order: Int,
        val type: SectionType? = null
    ) {
        data class Content(
            val articleId: String,
            val audioUrl: String,
            val audiobookId: String,
            val authorName: String,
            val avatarUrl: String,
            val description: String,
            val duration: Int,
            val episodeCount: Int,
            val episodeId: String,
            val episodeType: String,
            val language: String,
            val name: String,
            val paidExclusiveStartTime: Int,
            val paidIsEarlyAccess: Boolean,
            val paidIsExclusive: Boolean,
            val paidIsExclusivePartially: Boolean,
            val paidIsNowEarlyAccess: Boolean,
            val podcastId: String,
            val podcastName: String,
            val podcastPopularityScore: Int,
            val podcastPriority: Int,
            val popularityScore: Int,
            val priority: Int,
            val seasonNumber: Int?,
            val number: Int?,
            val score: Double,
            val releaseDate: String,
            val separatedAudioUrl: String
        ) {
            companion object {
                fun empty() = Content(
                    articleId = "",
                    audioUrl = "",
                    audiobookId = "",
                    authorName = "",
                    avatarUrl = "",
                    description = "",
                    duration = 0,
                    episodeCount = 0,
                    episodeId = "",
                    episodeType = "",
                    language = "",
                    name = "",
                    paidExclusiveStartTime = 0,
                    paidIsEarlyAccess = false,
                    paidIsExclusive = false,
                    paidIsExclusivePartially = false,
                    paidIsNowEarlyAccess = false,
                    podcastId = "",
                    podcastName = "",
                    podcastPopularityScore = 0,
                    podcastPriority = 0,
                    popularityScore = 0,
                    priority = 0,
                    seasonNumber = null,
                    number = null,
                    score = 0.0,
                    releaseDate = "",
                    separatedAudioUrl = ""
                )
            }
        }

        companion object {
            fun empty() = Section(
                content = emptyList(),
                contentType = "",
                name = "",
                order = 0,
                type = null
            )
        }
    }

    companion object {
        fun empty() = HomeResponse(
            pagination = Pagination.empty(),
            sections = emptyList()
        )
    }
}