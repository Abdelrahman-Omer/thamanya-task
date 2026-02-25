package com.thamanya.home.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Keep
@Serializable(with = SectionTypeDtoSerializer::class)
enum class SectionTypeDto {
    @SerialName("square") SQUARE,
    @SerialName("2_lines_grid") TWO_LINES_GRID,
    @SerialName("big_square") BIG_SQUARE_AUDIO_BOOK,
    @SerialName("big square") BIG_SQUARE,
    @SerialName("queue") QUEUE,
    UNKNOWN
}

object SectionTypeDtoSerializer : KSerializer<SectionTypeDto> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("SectionTypeDto", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SectionTypeDto) {
        val string = when (value) {
            SectionTypeDto.SQUARE -> "square"
            SectionTypeDto.TWO_LINES_GRID -> "2_lines_grid"
            SectionTypeDto.BIG_SQUARE_AUDIO_BOOK -> "big_square"
            SectionTypeDto.BIG_SQUARE -> "big square"
            SectionTypeDto.QUEUE -> "queue"
            SectionTypeDto.UNKNOWN -> "unknown"
        }
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): SectionTypeDto {
        return when (val string = decoder.decodeString()) {
            "square" -> SectionTypeDto.SQUARE
            "2_lines_grid" -> SectionTypeDto.TWO_LINES_GRID
            "big_square" -> SectionTypeDto.BIG_SQUARE_AUDIO_BOOK
            "big square" -> SectionTypeDto.BIG_SQUARE
            "queue" -> SectionTypeDto.QUEUE
            else -> {
                SectionTypeDto.UNKNOWN
            }
        }
    }
}