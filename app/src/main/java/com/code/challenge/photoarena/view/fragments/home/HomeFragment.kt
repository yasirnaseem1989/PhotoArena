package com.code.challenge.photoarena.view.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.code.challenge.photoarena.adapter.PhotosAdapter
import com.code.challenge.photoarena.base.BaseFragment
import com.code.challenge.photoarena.base.observeInLifecycle
import com.code.challenge.photoarena.databinding.FragmentHomeBinding
import com.code.challenge.photoarena.ext.launchAndRepeatWithViewLifecycle
import com.code.challenge.photoarena.ext.textChanges
import com.code.challenge.photoarena.view.fragments.home.HomeViewEvent.NavigateToDetail
import com.code.challenge.photoarena.view.fragments.home.HomeViewEvent.ShowConfirmationDialog
import com.code.challenge.ui.loading.dialog.onNegative
import com.code.challenge.ui.loading.dialog.onPositive
import com.code.challenge.ui.loading.dialog.photoArenaDialog
import com.code.challenge.ui.loading.dialog.title
import com.code.challenge.ui.loading.ext.LoadingProperty
import com.code.challenge.ui.loading.loading.LoadingHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalCoroutinesApi::class)
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var photoAdapter: PhotosAdapter

    private val loadingHandler: LoadingHandler by LoadingProperty()

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.getPhotosInitial()
    }

    @OptIn(FlowPreview::class)
    override fun initializeView() {

        photoAdapter = PhotosAdapter(emptyList()) {
            homeViewModel.onItemClicked(it)
        }

        linearLayoutManager = LinearLayoutManager(context)
        _binding?.photosRecyclerView?.apply {
            adapter = photoAdapter
            layoutManager = linearLayoutManager
        }

        _binding?.searchEditText?.textChanges()?.debounce(DELAY_SEARCH_DEBOUNCE)?.map {
            it?.trim()
        }?.onEach {
            val query = it?.toString()
            _binding?.searchClearImageView?.isVisible =
                query != null && query.length >= LENGTH_SEARCH_DEFAULT
            homeViewModel.setQuery(query.orEmpty())
        }?.observeInLifecycle(viewLifecycleOwner)

        _binding?.swipeLayout?.onRefreshed {
            homeViewModel.getPhotosInitial()
        }
    }

    override fun observeViewModel() {

        launchAndRepeatWithViewLifecycle {
            homeViewModel.observeViewEvents {

                when (it) {
                    is ShowConfirmationDialog -> {
                        val dialog = photoArenaDialog(requireContext()) {
                            title(it.title)
                            onPositive(it.positiveButtonText) {
                                homeViewModel.onPositiveButtonClicked(it.selectedPhoto)
                            }
                            onNegative(it.negativeButtonText) {}
                        }
                        dialog.show()
                    }
                    is NavigateToDetail -> {
                        val directions = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                            it.name,
                            it.imageUrl,
                            it.tags,
                            it.totalLikes,
                            it.totalDownloads,
                            it.totalComments
                        )
                        findNavController().navigate(directions)
                    }
                }
            }
        }

        launchAndRepeatWithViewLifecycle {
            homeViewModel.homeState.collect { uiState ->

                loadingHandler.set(uiState.isLoading)

                _binding?.contentGroup?.isVisible = uiState.hasData
                _binding?.emptyGroup?.isVisible = uiState.hasError

                photoAdapter.update(uiState.photos)
            }
        }
    }

    private companion object {
        private const val DELAY_SEARCH_DEBOUNCE = 300L
        private const val LENGTH_SEARCH_DEFAULT = 1
    }
}