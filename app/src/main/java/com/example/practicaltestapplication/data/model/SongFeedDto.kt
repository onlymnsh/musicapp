package com.example.practicaltestapplication.data.model

import com.google.gson.annotations.SerializedName

data class SongFeedDto(
    @SerializedName("entry")
    val songs: List<SongDataDto>
)

data class SongDataDto(
    @SerializedName("id")
    val songIdDto: SongIdDto,
    @SerializedName("im:image")
    val images: List<ImageDto>,
    @SerializedName("title")
    val title: TitleDto,
) {
    data class SongIdDto(
        @SerializedName("attributes")
        val attributes: AttributeDto,
    ) {
        data class AttributeDto(
            @SerializedName("im:id")
            val id: String,
        )
    }

    data class ImageDto(
        @SerializedName("label")
        val imageUrl: String,
    )

    data class TitleDto(
        @SerializedName("label")
        val name: String,
    )
}

