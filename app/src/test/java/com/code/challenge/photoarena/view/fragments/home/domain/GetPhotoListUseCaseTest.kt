package com.code.challenge.photoarena.view.fragments.home.domain

import com.code.challenge.photoarena.domain.PhotosResult.Error
import com.code.challenge.photoarena.domain.PhotosResult.Success
import com.code.challenge.photoarena.util.FakeResponse
import com.code.challenge.photoarena.util.MainDispatcherRule
import com.code.challenge.photoarena.view.fragments.home.data.HomeRepository
import com.code.challenge.photoarena.view.fragments.home.domain.PhotosFailure.NoPhotosFailure
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetPhotoListUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val homeRepository: HomeRepository = mockk()

    private lateinit var getPhotoListUseCase: GetPhotoListUseCase

    @Before
    fun setUp() {
        getPhotoListUseCase = GetPhotoListUseCase(homeRepository, mainDispatcherRule.testDispatcher)
    }

    @Test
    fun `given valid query it should return list`() = runTest {
        val data = FakeResponse.getPhotosSuccess()
        coEvery {
            homeRepository.getPhotos(any())
        } returns Success(data)
        val request = PhotoRequest(query = "fruits")
        val response = getPhotoListUseCase.invoke(request)
        assertTrue(response.isSuccess)
        assertEquals(data, (response as Success).data)
    }

    @Test
    fun `given empty query it should return empty list`() = runTest {
        coEvery {
            homeRepository.getPhotos(any())
        } returns Success(emptyList())
        val request = PhotoRequest(query = "")
        val response = getPhotoListUseCase.invoke(request)
        assertTrue(response.isSuccess)
        assertEquals(emptyList(), (response as Success).data)
    }

    @Test
    fun `given invalid query it should return empty list`() = runTest {
        coEvery {
            homeRepository.getPhotos(any())
        } returns Error(NoPhotosFailure)
        val request = PhotoRequest(query = "@0994300")
        val response = getPhotoListUseCase.invoke(request)
        assertTrue(response.isError)
    }
}