package com.thamanya.home.presentation

import com.thamanya.home.domain.HomeResponse
import com.thamanya.home.domain.model.Chip
import com.thamanya.presentation.UiText
import com.thamanya.presentation.domain.AudioTrack

data class HomeState(
    var isLoading:Boolean = false,
    var isSearchActive:Boolean = false,
    var searchQuery:String = "",
    var isPlayerExpanded:Boolean = false,
    var isPlaying:Boolean = false,
    var progress:Float = 0f,
    var selectedTrack:AudioTrack? = null,
    var sections: List<HomeResponse.Section> = emptyList(),
    var categories: List<Chip> = listOf(
        Chip(
            text = "الجميع",
            isSelected = true
        ),
        Chip(
            text = "لك"
        ),
        Chip(
            text = "البودكاست"
        ),
        Chip(
            text = "الصوتية"
        ),
        Chip(
            text = "المقالات"
        ),
        Chip(
            text = "الكتب"
        ),
    ),
    val errorMessage: UiText? = null
){
    val selectedOption = categories.find { it.isSelected }
}
