package com.bilalberekgm.bluearchive.di

import com.bilalberekgm.bluearchive.adapter.BlueArchiveAdaptor
import com.bilalberekgm.bluearchive.adapter.ViewPagerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {

    @Provides
    fun ProvideBlueArchiveAdapter() = BlueArchiveAdaptor()

    @Provides
    fun provideViewPagerAdapter() = ViewPagerAdapter()
}