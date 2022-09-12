package com.catalin.mvvmanimalslist

import com.catalin.mvvmanimalslist.api.AnimalRepo
import com.catalin.mvvmanimalslist.api.AnimalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideRepo() = AnimalRepo(AnimalService.api)
}