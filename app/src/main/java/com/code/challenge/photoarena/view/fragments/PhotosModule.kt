package com.code.challenge.photoarena.view.fragments

import com.code.challenge.photoarena.di.DISPATCHER_IO
import com.code.challenge.photoarena.view.fragments.detail.DetailViewModel
import com.code.challenge.photoarena.view.fragments.home.HomeViewModel
import com.code.challenge.photoarena.view.fragments.home.data.HomeDataSource
import com.code.challenge.photoarena.view.fragments.home.data.HomeRepository
import com.code.challenge.photoarena.view.fragments.home.data.HomeResourceProvider
import com.code.challenge.photoarena.view.fragments.home.data.PhotosMapper
import com.code.challenge.photoarena.view.fragments.home.data.RemoteHomeDateSource
import com.code.challenge.photoarena.view.fragments.home.domain.GetPhotoListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val MAPPER_PHOTOS_REMOTE = "MAPPER_PHOTOS_REMOTE"

private const val DATA_SOURCE_HOME_REMOTE = "DATA_SOURCE_HOME_REMOTE"
private const val DATA_SOURCE_HOME_LOCAL = "DATA_SOURCE_HOME_LOCAL"

private const val REPOSITORY_HOME = "REPOSITORY_HOME"

private const val USECASE_PHOTO_LIST_GET = "USECASE_PHOTO_LIST_GET"


val homeModule = module {

    factory {
        HomeResourceProvider(androidContext())
    }

    factory<HomeDataSource>(named(DATA_SOURCE_HOME_REMOTE)) {
        RemoteHomeDateSource(get(), get(named(MAPPER_PHOTOS_REMOTE)))
    }

    factory(named(MAPPER_PHOTOS_REMOTE)) {
        PhotosMapper(get())
    }
    /*factory<BranchesDataSource>(named(DATA_SOURCE_BRANCHES_LOCAL)) {
        LocalBranchesDataSource(get())
    }*/

    factory(named(REPOSITORY_HOME)) {
        HomeRepository(
            get(named(DATA_SOURCE_HOME_REMOTE))
        )
    }


    factory(named(USECASE_PHOTO_LIST_GET)) {
        GetPhotoListUseCase(
            get(named(REPOSITORY_HOME)),
            get(named(DISPATCHER_IO))
        )
    }

    viewModel {
        HomeViewModel(get(named(USECASE_PHOTO_LIST_GET)), get())
    }

    viewModel {
        DetailViewModel()
    }
}
