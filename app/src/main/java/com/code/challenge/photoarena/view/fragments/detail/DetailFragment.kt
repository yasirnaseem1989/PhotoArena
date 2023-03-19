package com.code.challenge.photoarena.view.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.code.challenge.photoarena.base.BaseFragment
import com.code.challenge.photoarena.databinding.FragmentDetailBinding
import com.code.challenge.photoarena.ext.launchAndRepeatWithViewLifecycle
import com.code.challenge.ui.loading.ext.LoadingProperty
import com.code.challenge.ui.loading.loading.LoadingHandler
import com.code.challenge.ui.model.PhoneArenaCard
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    private val detailViewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    private val loadingHandler: LoadingHandler by LoadingProperty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailViewModel.setDetailData(
            name = args.name,
            imageUrl = args.imageUrl,
            tags = args.tags,
            totalLikes = args.totalLikes,
            totalDownloads = args.totalDownloads,
            totalComments = args.totalComments
        )
    }

    override fun initializeView() {}

    override fun observeViewModel() {

        launchAndRepeatWithViewLifecycle {
            detailViewModel.detailState.collect { uiState ->

                loadingHandler.set(uiState.isLoading)

                _binding?.detailCard?.setCard(
                    PhoneArenaCard(
                        imageUrl = uiState.photo.imageURL,
                        tags = uiState.photo.tags,
                        title = uiState.photo.name,
                        subtitle = uiState.photo.totalLikes,
                        subtitleBody = uiState.photo.totalDownloads,
                        description = uiState.photo.totalLikes,
                    )
                )
            }
        }
    }
}