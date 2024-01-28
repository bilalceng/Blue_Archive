package com.bilalberekgm.bluearchive.di


import com.bilalberekgm.bluearchive.domain.repository.BlueArchiveRepositoryRepository
import com.bilalberekgm.bluearchive.viewmodel.BlueArchiveViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideViewModel(repository: BlueArchiveRepositoryRepository) =
        BlueArchiveViewModel(repository)
}


