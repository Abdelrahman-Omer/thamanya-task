package com.thamanya.home.data.mapper

import com.thamanya.home.data.dto.HomeResponseDto
import com.thamanya.home.data.dto.SectionTypeDto
import com.thamanya.home.domain.HomeResponse
import com.thamanya.home.domain.SectionType
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

private fun JsonElement?.asIntOrZero(): Int {
    if (this == null || this !is JsonPrimitive) return 0
    return this.content.toIntOrNull() ?: 0
}

private fun JsonElement?.asIntOrNull(): Int? {
    if (this == null || this !is JsonPrimitive) return null
    return this.content.toIntOrNull()
}

private fun JsonElement?.asDoubleOrZero(): Double {
    if (this == null || this !is JsonPrimitive) return 0.0
    return this.content.toDoubleOrNull() ?: 0.0
}

fun HomeResponseDto.toHomeResponse(): HomeResponse {
    return HomeResponse(
        pagination = this.pagination?.toPagination() ?: HomeResponse.Pagination.empty(),
        sections = this.sections?.map { it.toSectionType() } ?: emptyList()
    )
}

fun HomeResponseDto.Pagination.toPagination(): HomeResponse.Pagination {
    return HomeResponse.Pagination(
        nextPage = this.nextPage ?: "",
        totalPages = this.totalPages.asIntOrZero()
    )
}

fun HomeResponseDto.Section.toSectionType(): HomeResponse.Section {
    return HomeResponse.Section(
        content = this.content?.map { it.toContent() } ?: emptyList(),
        contentType = this.contentType ?: "",
        name = this.name ?: "",
        order = this.order.asIntOrZero(),
        type = this.type?.toSectionType()
    )
}

fun SectionTypeDto.toSectionType(): SectionType {
    return when(this){
        SectionTypeDto.SQUARE -> SectionType.SQUARE
        SectionTypeDto.BIG_SQUARE -> SectionType.BIG_SQUARE
        SectionTypeDto.BIG_SQUARE_AUDIO_BOOK -> SectionType.BIG_SQUARE_AUDIO_BOOK
        SectionTypeDto.QUEUE -> SectionType.QUEUE
        SectionTypeDto.TWO_LINES_GRID -> SectionType.TWO_LINES_GRID
        SectionTypeDto.UNKNOWN -> SectionType.SQUARE
    }
}

fun HomeResponseDto.Section.Content.toContent(): HomeResponse.Section.Content {
    return HomeResponse.Section.Content(
        articleId = this.articleId ?: "",
        audioUrl = this.audioUrl ?: "",
        audiobookId = this.audiobookId ?: "",
        authorName = this.authorName ?: "",
        avatarUrl = this.avatarUrl ?: "",
        description = this.description ?: "",
        duration = this.duration.asIntOrZero(),
        episodeCount = this.episodeCount.asIntOrZero(),
        episodeId = this.episodeId ?: "",
        episodeType = this.episodeType ?: "",
        language = this.language ?: "",
        name = this.name ?: "",
        paidExclusiveStartTime = this.paidExclusiveStartTime.asIntOrZero(),
        paidIsEarlyAccess = this.paidIsEarlyAccess ?: false,
        paidIsExclusive = this.paidIsExclusive ?: false,
        paidIsExclusivePartially = this.paidIsExclusivePartially ?: false,
        paidIsNowEarlyAccess = this.paidIsNowEarlyAccess ?: false,
        podcastId = this.podcastId ?: "",
        podcastName = this.podcastName ?: "",
        podcastPopularityScore = this.podcastPopularityScore.asIntOrZero(),
        podcastPriority = this.podcastPriority.asIntOrZero(),
        popularityScore = this.popularityScore.asIntOrZero(),
        priority = this.priority.asIntOrZero(),
        seasonNumber = this.seasonNumber.asIntOrNull(),
        number = this.number.asIntOrNull(),
        score = this.score.asDoubleOrZero(),
        releaseDate = this.releaseDate ?: "",
        separatedAudioUrl = this.separatedAudioUrl ?: ""
    )
}