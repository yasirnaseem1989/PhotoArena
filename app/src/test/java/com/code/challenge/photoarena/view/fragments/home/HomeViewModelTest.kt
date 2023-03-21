package com.code.challenge.photoarena.view.fragments.home

import com.code.challenge.photoarena.domain.PhotosResult.Success
import com.code.challenge.photoarena.util.FakeResponse
import com.code.challenge.photoarena.util.MainDispatcherRule
import com.code.challenge.photoarena.view.fragments.home.HomeViewEvent.ClearSearchQuery
import com.code.challenge.photoarena.view.fragments.home.data.HomeRepository
import com.code.challenge.photoarena.view.fragments.home.data.HomeResourceProvider
import com.code.challenge.photoarena.view.fragments.home.domain.GetPhotoListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repo: HomeRepository = mockk()
    private val getPhotoListUseCase: GetPhotoListUseCase =
        spyk(GetPhotoListUseCase(repo, mainDispatcherRule.testDispatcher))

    private val homeResourceProvider: HomeResourceProvider = mockk()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(getPhotoListUseCase, homeResourceProvider)
    }

    @Test
    fun `Given default state, viewmodel should return photos successfully`() = runTest {

        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.homeState.collect() }

        val data = FakeResponse.getPhotosSuccess()
        coEvery {
            getPhotoListUseCase.execute(any())
        } returns Success(data)

        homeViewModel.getPhotosInitial()

        assertEquals(
            HomeUiState(
                isLoading = false,
                photos = data
            ),
            homeViewModel.homeState.value
        )
        collectJob.cancel()
    }

    @Test
    fun `Given query, viewmodel should return photos successfully`() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.homeState.collect() }

        val data = FakeResponse.getPhotosSuccess()
        coEvery {
            getPhotoListUseCase.execute(any())
        } returns Success(data)

        homeViewModel.setQuery("apple")

        assertEquals(
            HomeUiState(
                isLoading = false,
                photos = data
            ),
            homeViewModel.homeState.value
        )
        collectJob.cancel()
    }

    @Test
    fun `When clear text is clicked, then verify events`() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.homeState.collect() }
        val collectJobEvent =
            launch(UnconfinedTestDispatcher()) { homeViewModel.observeViewEvents {
                assertTrue(it is ClearSearchQuery)
            } }

        val data = FakeResponse.getPhotosSuccess()
        coEvery {
            getPhotoListUseCase.execute(any())
        } returns Success(data)

        homeViewModel.clearTextClicked()

        coVerify { getPhotoListUseCase.execute(any()) }

        collectJob.cancel()
        collectJobEvent.cancel()
    }
}