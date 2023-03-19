package com.code.challenge.photoarena.view.fragments.detail

import androidx.lifecycle.viewModelScope
import com.code.challenge.photoarena.base.BaseViewModel
import com.code.challenge.photoarena.base.BaseViewModel.ViewEvent
import com.code.challenge.photoarena.view.fragments.home.model.Photos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel : BaseViewModel<DetailViewEvent>() {

    private val _detailState = MutableStateFlow(DetailUiState())
    val detailState = _detailState.asStateFlow()

    fun setDetailData(
        name: String,
        imageUrl: String,
        tags: String,
        totalLikes: String,
        totalDownloads: String,
        totalComments: String
    ) {
        viewModelScope.launch {
            _detailState.update { homeUiState ->
                homeUiState.copy(isLoading = true)
            }
            val photo = Photos(
                name = name,
                imageURL = imageUrl,
                tags = tags,
                totalLikes = totalLikes,
                totalDownloads = totalDownloads,
                totalComments = totalComments
            )
            _detailState.update { homeUiState ->
                homeUiState.copy(isLoading = false, photo = photo)
            }
        }
    }
}

data class DetailUiState(
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val photo: Photos = Photos()
)

sealed class DetailViewEvent : ViewEvent()