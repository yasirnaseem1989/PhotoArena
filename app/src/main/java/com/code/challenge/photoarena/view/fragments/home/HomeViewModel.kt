package com.code.challenge.photoarena.view.fragments.home

import androidx.lifecycle.viewModelScope
import com.code.challenge.photoarena.base.BaseViewModel
import com.code.challenge.photoarena.base.BaseViewModel.ViewEvent
import com.code.challenge.photoarena.domain.PhotosResult
import com.code.challenge.photoarena.view.fragments.home.HomeViewEvent.ClearSearchQuery
import com.code.challenge.photoarena.view.fragments.home.HomeViewEvent.NavigateToDetail
import com.code.challenge.photoarena.view.fragments.home.HomeViewEvent.ShowConfirmationDialog
import com.code.challenge.photoarena.view.fragments.home.data.HomeResourceProvider
import com.code.challenge.photoarena.view.fragments.home.domain.GetPhotoListUseCase
import com.code.challenge.photoarena.view.fragments.home.domain.GetPhotoListUseCase.Companion.QUERY_DEFAULT
import com.code.challenge.photoarena.view.fragments.home.domain.PhotoRequest
import com.code.challenge.photoarena.view.fragments.home.model.Photos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPhotoListUseCase: GetPhotoListUseCase,
    private val homeResourceProvider: HomeResourceProvider
) : BaseViewModel<HomeViewEvent>() {

    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState.asStateFlow()

    private var photosRequest = PhotoRequest()

    fun getPhotosInitial() {
        viewModelScope.launch {
            photosRequest = photosRequest.copy(query = QUERY_DEFAULT)
            _homeState.update { homeUiState ->
                homeUiState.copy(isLoading = true)
            }
            getPhotos()
        }
    }

    private fun getPhotos() {
        viewModelScope.launch {
            val response =  getPhotoListUseCase(photosRequest)
            if (response is PhotosResult.Success) {
                _homeState.update { homeUiState ->
                    homeUiState.copy(
                        isLoading = false, photos = response.data
                    )
                }
            }
            else {
                _homeState.update { homeUiState ->
                    homeUiState.copy(isLoading = false)
                }
            }
        }
    }

    fun clearTextClicked() {
        viewModelScope.launch {
            tryEmitViewEvent(ClearSearchQuery)
            getPhotosInitial()
        }
    }

    fun setQuery(query: String) {
        photosRequest = photosRequest.copy(query = query)
        getPhotos()
    }

    fun onItemClicked(photo: Photos) {
        viewModelScope.launch {
            val title = homeResourceProvider.getDialogTitle()
            val positiveButtonText = homeResourceProvider.getPositiveButtonText()
            val negativeButtonText = homeResourceProvider.getNegativeButtonText()
            tryEmitViewEvent(
                ShowConfirmationDialog(
                    title = title,
                    positiveButtonText = positiveButtonText,
                    negativeButtonText = negativeButtonText,
                    selectedPhoto = photo
                )
            )
        }
    }

    fun onPositiveButtonClicked(photo: Photos) {
        viewModelScope.launch {
            tryEmitViewEvent(
                NavigateToDetail(
                    name = photo.name,
                    imageUrl = photo.imageURL,
                    tags = photo.tags,
                    totalLikes = photo.totalLikes,
                    totalDownloads = photo.totalDownloads,
                    totalComments = photo.totalComments
                )
            )
        }
    }
}

data class HomeUiState(
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val photos: List<Photos> = emptyList()
) {
    val hasData: Boolean = photos.isNotEmpty()
}

sealed class HomeViewEvent : ViewEvent() {
    object ClearSearchQuery : HomeViewEvent()
    data class ShowConfirmationDialog(
        val title: String,
        val positiveButtonText: String,
        val negativeButtonText: String,
        val selectedPhoto: Photos
    ) : HomeViewEvent()

    data class NavigateToDetail(
        val name: String = "",
        val imageUrl: String = "",
        val tags: String = "",
        val totalLikes: String = "",
        val totalDownloads: String = "",
        val totalComments: String = ""
    ) : HomeViewEvent()
}